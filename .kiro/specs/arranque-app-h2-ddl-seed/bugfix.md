# Bugfix Requirements Document

## Introduction

La aplicación `backend-capacitacion` (Spring Boot 3.4.3, Java 17, H2 en memoria con `ddl-auto=create` y `defer-datasource-initialization=true`) **no arranca** (`Application run failed`, exit code 1). Durante la inicialización del esquema y la ejecución del seed se producen dos fallos independientes que impiden que el contexto de Spring se levante:

1. **Palabra reservada `user`**: la entidad `com.capacitacion.model.entity.User` está anotada con `@Entity` **sin** `@Table`, por lo que Hibernate genera `create table user (...)`. H2 2.3.232 trata `USER` como palabra reservada y lanza `JdbcSQLSyntaxErrorException: expected "identifier"` en `[*]user`, abortando la creación del `EntityManagerFactory`.
2. **Seed `data.sql` inválido**: `src/main/resources/data.sql` inserta en la tabla `ruls`, que no existe (ninguna entidad JPA la genera → `Table "RULS" not found`), y además referencia la columna `active` entre comillas simples (`'active'`), que en SQL es un literal string y no un identificador de columna válido. Esto hace fallar `dataSourceScriptDatabaseInitializer`.

Ambos fallos deben corregirse para que la aplicación arranque completa (contexto Spring + Tomcat en el puerto 8088, context-path `/capacitance`), con la tabla de `User` creada bajo un nombre válido y la tabla `ruls` existente y poblada con las 2 filas del seed.

Verificaciones previas (contra el código real):
- `User.java` es la única entidad del paquete `entity`; usa `@Entity` sin `@Table`. Columnas: `id` (Integer, `@Id`), `firstName`, `secondName`, `firstLastName`, `secondLastName` (String), `dateBirth` (LocalDateTime). No hay `JpaRepository` de `User` (el log reporta "Found 0 JPA repository interfaces").
- No existe ninguna referencia previa a `Rule` / `ruls` / `RNI` en el código Java → la nueva entidad `Rule` se puede crear sin colisiones.
- No hay SQL nativo ni queries que referencien la tabla `user`; el único acceso es vía JPA/Hibernate por la entidad `User`, por lo que renombrar su tabla a `users` no rompe consultas existentes.
- `application.yml`: H2 `jdbc:h2:mem:managemt`, `ddl-auto: create`, `defer-datasource-initialization: true`, `spring.sql.init.mode: always`, puerto 8088, context-path `/capacitance`.

## Bug Analysis

### Current Behavior (Defect)

Lo que ocurre hoy al ejecutar el arranque de Spring Boot con el esquema y el seed actuales:

1.1 WHEN Hibernate genera el DDL de la entidad `User` (anotada con `@Entity` sin `@Table`) sobre H2 THEN el sistema intenta ejecutar `create table user (...)` y lanza `JdbcSQLSyntaxErrorException: expected "identifier"` en `[*]user` porque `USER` es palabra reservada en H2.

1.2 WHEN falla la creación del esquema de `User` durante la construcción del `EntityManagerFactory` THEN el sistema aborta el arranque con `Application run failed` (exit code 1) y el contexto de Spring no se levanta.

1.3 WHEN se ejecuta `data.sql` e intenta `INSERT INTO ruls (...)` THEN el sistema falla con `Table "RULS" not found` porque no existe ninguna entidad JPA que genere la tabla `ruls`.

1.4 WHEN `data.sql` referencia la columna como `'active'` (entre comillas simples) en el `INSERT` THEN el sistema interpreta `'active'` como un literal string en lugar de un identificador de columna válido, produciendo un error de sintaxis en el seed.

1.5 WHEN falla la ejecución del seed THEN el `dataSourceScriptDatabaseInitializer` propaga el error y el arranque de la aplicación termina fallido.

### Expected Behavior (Correct)

Lo que debe ocurrir tras el fix, para las mismas condiciones que hoy disparan el bug:

2.1 WHEN Hibernate genera el DDL de la entidad `User` sobre H2 THEN el sistema SHALL crear la tabla con un nombre de identificador válido (p. ej. `@Table(name = "users")`) sin lanzar `JdbcSQLSyntaxErrorException`.

2.2 WHEN se construye el `EntityManagerFactory` durante el arranque THEN el sistema SHALL completar la creación del esquema sin errores de DDL y continuar el arranque del contexto de Spring.

2.3 WHEN se ejecuta `data.sql` e intenta `INSERT INTO ruls (...)` THEN el sistema SHALL encontrar la tabla `ruls` ya creada por Hibernate (a partir de una nueva entidad JPA `Rule` mapeada a `ruls` con `ddl-auto=create` + `defer-datasource-initialization=true`) y ejecutar los inserts correctamente.

2.4 WHEN `data.sql` referencia la columna `active` THEN el sistema SHALL usar el identificador de columna válido `active` (sin comillas simples) e insertar los valores booleanos correctamente.

2.5 WHEN finaliza la ejecución del seed THEN el sistema SHALL tener la tabla `ruls` poblada con las 2 filas (`RNI-0001` con `active=true`, `RNI-0002` con `active=false`) y SHALL completar el arranque con Tomcat escuchando en el puerto 8088 y context-path `/capacitance`, sin excepciones de DDL ni de seed.

### Unchanged Behavior (Regression Prevention)

Comportamiento existente que el fix NO debe alterar:

3.1 WHEN se accede a la entidad `User` desde el código Java (getters/setters, `@Id`, campos `firstName`, `secondName`, `firstLastName`, `secondLastName`, `dateBirth`, validación `@ValidBirthday`) THEN el sistema SHALL CONTINUE TO exponer los mismos campos, tipos y validaciones (el cambio es sólo el nombre de la tabla, no el modelo ni los contratos de la clase).

3.2 WHEN se invocan los controladores y endpoints existentes (`UserController`, `NotificationController`, `ReportsController`, etc.) THEN el sistema SHALL CONTINUE TO responder con los mismos contratos de request/response que antes del fix.

3.3 WHEN se compilan y ejecutan los ejemplos existentes (EJM001..EJM006) y el resto del código THEN el sistema SHALL CONTINUE TO compilar y comportarse igual, sin cambios de firma ni de lógica.

3.4 WHEN se lee la configuración de `application.yml` (datasource H2 `jdbc:h2:mem:managemt`, `ddl-auto: create`, `defer-datasource-initialization: true`, `spring.sql.init.mode: always`, puerto 8088, context-path `/capacitance`, consola H2 en `/h2-console`) THEN el sistema SHALL CONTINUE TO usar los mismos valores de configuración (el fix no requiere cambiarlos).

3.5 WHEN Hibernate genera el esquema del resto de entidades y componentes no relacionados con `User` ni `Rule` THEN el sistema SHALL CONTINUE TO generarlo sin cambios respecto al comportamiento previo.

## Bug Condition Derivation

Formalización de la condición del bug y las propiedades de validación, donde:
- **F**: el arranque de la aplicación con el código actual (sin fix).
- **F'**: el arranque de la aplicación con el fix aplicado (entidad `User` mapeada a tabla válida, nueva entidad `Rule` → tabla `ruls`, y `data.sql` corregido).

### Bug Condition

```pascal
FUNCTION isBugCondition(X)
  INPUT: X of type ApplicationStartup   // ejecución de arranque de Spring Boot sobre H2
  OUTPUT: boolean

  // El bug se dispara siempre que se inicializa el esquema H2 + seed con el estado actual:
  //  (a) la entidad User se mapea al identificador reservado `user`, o
  //  (b) el seed data.sql inserta en `ruls` (tabla inexistente) y/o usa 'active' entre comillas.
  RETURN startupInitializesSchemaAndSeed(X)
         AND ( userEntityMapsToReservedTableName(X)
               OR seedTargetsMissingRulsTable(X)
               OR seedUsesQuotedColumnActive(X) )
END FUNCTION
```

### Property — Fix Checking

```pascal
// Propiedad: para toda ejecución de arranque que hoy dispara el bug,
// el arranque con el fix debe completar sin errores de DDL ni de seed.
FOR ALL X WHERE isBugCondition(X) DO
  result ← F'(X)
  ASSERT contextStartsSuccessfully(result)               // sin Application run failed
     AND no_ddl_syntax_error(result)                     // User -> tabla válida (p.ej. users)
     AND rulsTableExists(result)                         // entidad Rule genera `ruls`
     AND rulsSeeded(result, rows = 2)                    // RNI-0001 active=true, RNI-0002 active=false
     AND tomcatListening(result, port = 8088, contextPath = "/capacitance")
END FOR
```

### Property — Preservation Checking

```pascal
// Propiedad: para toda ejecución/uso que NO dispara el bug (endpoints existentes,
// modelo de User a nivel Java, ejemplos EJM001..EJM006, config no relacionada),
// el comportamiento del sistema con el fix es idéntico al original.
FOR ALL X WHERE NOT isBugCondition(X) DO
  ASSERT F(X) = F'(X)
END FOR
```
