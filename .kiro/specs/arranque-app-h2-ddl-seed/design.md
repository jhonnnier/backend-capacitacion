# Arranque App (H2 DDL + Seed) Bugfix Design

## Overview

La aplicación `backend-capacitacion` (Spring Boot 3.4.3, Java 17, H2 en memoria) no arranca (`Application run failed`, exit code 1) por dos fallos independientes que ocurren durante la inicialización del esquema y la ejecución del seed:

1. **Tabla reservada `user`**: la entidad `com.capacitacion.model.entity.User` está anotada con `@Entity` **sin** `@Table`, por lo que Hibernate emite `create table user (...)`. H2 trata `USER` como palabra reservada y lanza `JdbcSQLSyntaxErrorException: expected "identifier"`, abortando la construcción del `EntityManagerFactory`.
2. **Seed `data.sql` inválido**: `src/main/resources/data.sql` inserta en la tabla `ruls` (que ninguna entidad JPA genera → `Table "RULS" not found`) y referencia la columna `active` entre comillas simples (`'active'`), que SQL interpreta como literal string y no como identificador de columna.

La estrategia de fix es **mínima y targeted**, sin tocar la configuración:

- **User.java**: agregar `@Table(name = "users")` para que Hibernate use un identificador no reservado. El modelo Java (campos, tipos, getters/setters Lombok, validación `@ValidBirthday`) no cambia.
- **Rule.java (nuevo)**: crear una entidad JPA `Rule` mapeada a `@Table(name = "ruls")` con los campos que el seed inserta, para que Hibernate cree la tabla `ruls` antes del seed (gracias a `ddl-auto=create` + `defer-datasource-initialization=true`).
- **data.sql**: corregir la columna `'active'` → `active` (sin comillas), preservando las 2 filas del seed.

`application.yml` no requiere cambios (se referencia únicamente como contexto del mecanismo de arranque).

## Glossary

- **Bug_Condition (C)**: la condición que dispara el bug — el arranque de Spring Boot inicializa el esquema H2 + seed y (a) la entidad `User` se mapea al identificador reservado `user`, y/o (b) el seed apunta a la tabla inexistente `ruls`, y/o (c) el seed usa la columna `'active'` entre comillas.
- **Property (P)**: el comportamiento deseado tras el fix — el contexto de Spring levanta sin errores de DDL ni de seed, la tabla `ruls` queda con 2 filas y Tomcat escucha en `8088` con context-path `/capacitance`.
- **Preservation**: el comportamiento existente que el fix NO debe alterar — modelo Java de `User`, contratos de los controladores, ejemplos EJM001..EJM006, generación de esquema del resto de entidades y la configuración de `application.yml`.
- **F**: el arranque de la aplicación con el código actual (sin fix).
- **F'**: el arranque de la aplicación con el fix aplicado (`User` → tabla `users`, nueva entidad `Rule` → tabla `ruls`, `data.sql` corregido).
- **`User`**: entidad en `backend-capacitacion/src/main/java/com/capacitacion/model/entity/User.java` que hoy usa `@Entity` sin `@Table`.
- **`Rule`**: nueva entidad en `.../model/entity/Rule.java` a crear, mapeada a `ruls`.
- **`data.sql`**: seed en `backend-capacitacion/src/main/resources/data.sql` ejecutado por `dataSourceScriptDatabaseInitializer` tras la creación del esquema (defer).

## Bug Details

### Bug Condition

El bug se manifiesta en cada arranque de Spring Boot sobre H2 con el estado actual del código: Hibernate genera el DDL a partir de las entidades y luego (por `defer-datasource-initialization=true`) se ejecuta `data.sql`. El fallo ocurre porque `User` se mapea al identificador reservado `user`, y/o porque el seed apunta a `ruls` (inexistente) usando la columna `'active'` entre comillas.

**Formal Specification:**
```
FUNCTION isBugCondition(X)
  INPUT: X of type ApplicationStartup   // ejecución de arranque de Spring Boot sobre H2
  OUTPUT: boolean

  RETURN startupInitializesSchemaAndSeed(X)
         AND ( userEntityMapsToReservedTableName(X)   // @Entity sin @Table -> create table user
               OR seedTargetsMissingRulsTable(X)      // INSERT INTO ruls sin entidad que la genere
               OR seedUsesQuotedColumnActive(X) )      // columna 'active' entre comillas
END FUNCTION
```

### Examples

- **Palabra reservada `user`** — Esperado: Hibernate crea la tabla de `User` bajo un identificador válido y el `EntityManagerFactory` se construye. Actual: `create table user (...)` lanza `JdbcSQLSyntaxErrorException: expected "identifier"` en `[*]user` y el arranque aborta con `Application run failed`.
- **Tabla `ruls` inexistente** — Esperado: `INSERT INTO ruls (...)` encuentra la tabla creada por Hibernate. Actual: `Table "RULS" not found`, el `dataSourceScriptDatabaseInitializer` propaga el error y el arranque falla.
- **Columna `'active'` entre comillas** — Esperado: `active` se interpreta como identificador de columna y el booleano se inserta. Actual: `'active'` se interpreta como literal string y el `INSERT` es sintácticamente inválido.
- **Edge case (arranque exitoso completo)** — Esperado: con los 3 sub-fallos resueltos, la tabla `ruls` queda con 2 filas (`RNI-0001` con `active=true`, `RNI-0002` con `active=false`) y Tomcat escucha en `8088` `/capacitance`.

## Expected Behavior

### Preservation Requirements

**Unchanged Behaviors:**
- El modelo Java de `User` (campos `id`, `firstName`, `secondName`, `firstLastName`, `secondLastName`, `dateBirth`; anotaciones Lombok `@Getter/@Setter/@NoArgsConstructor/@AllArgsConstructor/@ToString/@Builder`; `@Id`; validación `@ValidBirthday(minAge = 15)` sobre `dateBirth`) permanece idéntico. El único cambio es el nombre de la tabla física.
- Los contratos request/response de los controladores existentes (`UserController`, `NotificationController`, `ReportsController`) permanecen sin cambios.
- Los ejemplos EJM001..EJM006 y el resto del código siguen compilando y comportándose igual (sin cambios de firma ni de lógica).
- La configuración de `application.yml` (datasource H2 `jdbc:h2:mem:managemt`, `ddl-auto: create`, `defer-datasource-initialization: true`, `spring.sql.init.mode: always`, consola H2 en `/h2-console`, puerto `8088`, context-path `/capacitance`) se mantiene con los mismos valores.
- La generación de esquema del resto de entidades/componentes no relacionados con `User` ni `Rule` no cambia.

**Scope:**
Todos los usos que NO forman parte de la condición del bug deben quedar completamente inalterados por el fix. Esto incluye:
- El acceso a `User` a nivel Java (p. ej. `UserRepository.findById(...)`, que hoy usa `User.builder()` y no SQL nativo ni JPQL que referencie la tabla `user`).
- Las respuestas de los endpoints existentes.
- La compilación y ejecución de EJM001..EJM006 y demás clases.
- Los valores de configuración de `application.yml`.

**Note:** El comportamiento correcto esperado (arranque exitoso, `ruls` con 2 filas, Tomcat en 8088) está definido en la sección Correctness Properties (Property 1). Esta sección se enfoca en lo que NO debe cambiar.

## Hypothesized Root Cause

Con base en el análisis del código real, las causas son concretas y confirmadas (no hipótesis abiertas):

1. **Entidad `User` sin `@Table` (identificador reservado)**: `User.java` usa `@Entity` sin `@Table`, por lo que Hibernate deriva el nombre de tabla `user` del nombre de la clase. `USER` es palabra reservada en H2 → `JdbcSQLSyntaxErrorException: expected "identifier"`. Verificado: `User` es la única entidad del paquete `entity` y no hay SQL nativo/JPQL que referencie la tabla `user` (grep = 0 coincidencias; el acceso es sólo vía `User.builder()` en `UserRepository`).

2. **Falta la entidad que genera `ruls`**: `data.sql` inserta en `ruls`, pero ninguna entidad JPA la genera → `Table "RULS" not found`. Verificado: cero referencias previas a `Rule`/`ruls`/`RNI` en el código (grep = 0), por lo que crear la entidad `Rule` no colisiona con nada existente.

3. **Columna `'active'` entre comillas en el seed**: en `data.sql`, `'active'` (comillas simples) es un literal string, no un identificador de columna. Debe ser `active` sin comillas.

4. **Orden de inicialización (por qué la nueva entidad resuelve el seed)**: con `ddl-auto=create` + `defer-datasource-initialization=true`, Hibernate crea el esquema (incluida `ruls` una vez exista la entidad `Rule`) **antes** de que `spring.sql.init.mode=always` ejecute `data.sql`. Este orden es el que permite que el `INSERT INTO ruls` encuentre la tabla.

## Correctness Properties

Property 1: Bug Condition - Arranque exitoso del contexto con esquema y seed válidos

_For any_ ejecución de arranque donde la condición del bug se cumple (`isBugCondition` retorna true), la aplicación con el fix (F') SHALL completar la creación del esquema y del seed sin lanzar `JdbcSQLSyntaxErrorException` ni `Table "RULS" not found`, dejando la tabla de `User` bajo el identificador válido `users`, la tabla `ruls` existente y poblada con las 2 filas del seed (`RNI-0001` con `active=true`, `RNI-0002` con `active=false`), y Tomcat escuchando en el puerto `8088` con context-path `/capacitance`.

**Validates: Requirements 2.1, 2.2, 2.3, 2.4, 2.5**

Property 2: Preservation - Modelo, contratos, ejemplos y configuración sin cambios

_For any_ uso/ejecución donde la condición del bug NO se cumple (`isBugCondition` retorna false) — acceso a `User` a nivel Java, endpoints de los controladores existentes, compilación y comportamiento de EJM001..EJM006, y lectura de `application.yml` — la aplicación con el fix (F') SHALL producir exactamente el mismo resultado que el código original (F), preservando campos/tipos/validaciones de `User`, los contratos de los controladores, el comportamiento de los ejemplos y los valores de configuración.

**Validates: Requirements 3.1, 3.2, 3.3, 3.4, 3.5**

## Fix Implementation

### Changes Required

El fix consiste en 3 cambios acotados en 2 archivos existentes + 1 archivo nuevo. Confirmado contra el código real.

#### Cambio 1 — Mapear `User` a la tabla `users`

**File**: `backend-capacitacion/src/main/java/com/capacitacion/model/entity/User.java`

**Función/Elemento**: anotación de clase de la entidad `User`.

**Cambio específico**:
1. Agregar `import jakarta.persistence.Table;`.
2. Añadir `@Table(name = "users")` junto a `@Entity`.
3. NO tocar nada más: campos, tipos, anotaciones Lombok (`@Getter/@Setter/@NoArgsConstructor/@AllArgsConstructor/@ToString/@Builder`), `@Id`, `@Schema`, ni la validación `@ValidBirthday(minAge = 15)`.

```java
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User {
    // ... campos sin cambios ...
}
```

Con esto Hibernate emite `create table users (...)`, un identificador no reservado en H2, resolviendo 2.1 y desbloqueando 2.2.

#### Cambio 2 — Crear la entidad JPA `Rule` (tabla `ruls`)

**File** (nuevo): `backend-capacitacion/src/main/java/com/capacitacion/model/entity/Rule.java`

**Objetivo**: que Hibernate cree la tabla `ruls` durante la generación de esquema, con columnas que coinciden con las que inserta `data.sql` (`id`, `code`, `description`, `active`).

**Estructura**:
- Paquete: `com.capacitacion.model.entity`.
- `@Entity` + `@Table(name = "ruls")`.
- Mismas anotaciones Lombok que `User` para consistencia: `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@ToString`, `@Builder`. `@Schema` es opcional (documentación).
- Campos (coinciden con el seed):
  - `Integer id` con `@Id` (el seed provee el id explícito `1` y `2`, por eso no se usa generación automática).
  - `String code` (p. ej. `'RNI-0001'`).
  - `String description` (p. ej. `'des RNI-0001'`).
  - `boolean active` (primitivo; el seed inserta `true`/`false`).

```java
package com.capacitacion.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "ruls")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Rule {
    @Id
    @Schema(description = "ID de la regla")
    private Integer id;

    @Schema(description = "Código de la regla")
    private String code;

    @Schema(description = "Descripción de la regla")
    private String description;

    @Schema(description = "Indica si la regla está activa")
    private boolean active;
}
```

Nota de mapeo: Hibernate deriva los nombres de columna `id`, `code`, `description`, `active` de los campos, coincidiendo con los identificadores del `INSERT`. La tabla se crea antes del seed por `defer-datasource-initialization=true`, resolviendo 2.3.

#### Cambio 3 — Corregir la columna `active` en el seed

**File**: `backend-capacitacion/src/main/resources/data.sql`

**Cambio específico**: reemplazar `'active'` (comillas simples) por el identificador `active`, manteniendo las 2 filas y sus valores exactos.

Antes:
```sql
INSERT INTO ruls (id, code, description, 'active') VALUES (1, 'RNI-0001', 'des RNI-0001', true);
INSERT INTO ruls (id, code, description, 'active') VALUES (2, 'RNI-0002', 'des RNI-0002', false);
```

Después:
```sql
INSERT INTO ruls (id, code, description, active) VALUES (1, 'RNI-0001', 'des RNI-0001', true);
INSERT INTO ruls (id, code, description, active) VALUES (2, 'RNI-0002', 'des RNI-0002', false);
```

Resuelve 2.4 y, junto con los cambios 1 y 2, deja el estado final de 2.5.

#### Cambio 4 — Configuración (sin cambios)

**File**: `backend-capacitacion/src/main/resources/application.yml`

Ningún cambio. Se documenta como referencia porque el mecanismo de fix se apoya en su configuración actual: `ddl-auto: create`, `defer-datasource-initialization: true`, `spring.sql.init.mode: always`, `jdbc:h2:mem:managemt`, puerto `8088`, context-path `/capacitance`. Mantener estos valores es requisito de preservación (3.4).

### Mapeo Cambio → Propiedad

| Cambio | Archivo | Fix Checking (Property 1 / Req.) | Preservation (Property 2 / Req.) |
|--------|---------|----------------------------------|----------------------------------|
| 1. `@Table(name = "users")` | `User.java` | 2.1, 2.2 (DDL válido, EMF se construye) | 3.1 (modelo/validación intactos), 3.2/3.3 (contratos y ejemplos) |
| 2. Nueva entidad `Rule` → `ruls` | `Rule.java` (nuevo) | 2.3, 2.5 (`ruls` existe y se puebla) | 3.5 (no altera el esquema de otras entidades) |
| 3. `'active'` → `active` | `data.sql` | 2.4, 2.5 (seed válido, 2 filas) | — |
| 4. Sin cambios | `application.yml` | Habilita el orden DDL→seed (2.3, 2.5) | 3.4 (config idéntica) |

## Testing Strategy

### Validation Approach

Como este es un fix de arranque, la señal primaria de éxito es que el **contexto de Spring levante** sin excepciones de DDL ni de seed. La estrategia sigue dos fases: primero surfacear los counterexamples que demuestran el bug sobre el código sin fix, y luego verificar que el fix funciona y preserva el comportamiento existente.

### Exploratory Bug Condition Checking

**Goal**: Surfacear counterexamples que demuestren el bug ANTES de implementar el fix, y confirmar/refutar el análisis de causa raíz. Si se refuta, re-hipotetizar.

**Test Plan**: Ejecutar el arranque de la aplicación (o un `@SpringBootTest` de carga de contexto) sobre el código SIN fix y observar los fallos en el log. Confirmar que los mensajes coinciden con las 3 causas hipotetizadas.

**Test Cases**:
1. **Arranque sin fix (User)**: ejecutar `mvn spring-boot:run` (o el test de contexto) y observar `JdbcSQLSyntaxErrorException: expected "identifier"` en `[*]user` (falla en código sin fix).
2. **Seed sin tabla (ruls)**: con la entidad `User` ya mapeada a `users` pero SIN la entidad `Rule`, observar `Table "RULS" not found` al ejecutar `data.sql` (falla en código sin fix).
3. **Columna citada (active)**: con `ruls` existente pero con `'active'` entre comillas, observar el error de sintaxis del seed (falla en código sin fix).
4. **Edge — arranque completo**: sin ningún fix, `Application run failed` (exit code 1) y el contexto no levanta (falla en código sin fix).

**Expected Counterexamples**:
- `JdbcSQLSyntaxErrorException: expected "identifier"` sobre `user`.
- `Table "RULS" not found` durante `dataSourceScriptDatabaseInitializer`.
- Causas posibles descartadas por evidencia: no es config de datasource, no es dialecto H2, no es dependencia faltante — es (a) identificador reservado, (b) tabla inexistente, (c) columna citada.

### Fix Checking

**Goal**: Verificar que para toda ejecución de arranque donde se cumple la condición del bug, F' produce el comportamiento esperado.

**Pseudocode:**
```
FOR ALL X WHERE isBugCondition(X) DO
  result := F'(X)
  ASSERT contextStartsSuccessfully(result)          // sin Application run failed
     AND no_ddl_syntax_error(result)                // User -> tabla users
     AND rulsTableExists(result)                    // entidad Rule genera ruls
     AND rulsSeeded(result, rows = 2)               // RNI-0001 active=true, RNI-0002 active=false
     AND tomcatListening(result, port = 8088, contextPath = "/capacitance")
END FOR
```

Validación concreta:
- `mvn compile` termina en 0 errores.
- El arranque (o `@SpringBootTest`) levanta el contexto sin excepciones de DDL ni de seed.
- Consulta `SELECT COUNT(*) FROM ruls` = 2; `SELECT code, active FROM ruls ORDER BY id` = `[('RNI-0001', true), ('RNI-0002', false)]`.

### Preservation Checking

**Goal**: Verificar que para toda ejecución/uso donde NO se cumple la condición del bug, F' produce el mismo resultado que F.

**Pseudocode:**
```
FOR ALL X WHERE NOT isBugCondition(X) DO
  ASSERT F(X) = F'(X)
END FOR
```

**Testing Approach**: El property-based testing es recomendable para la preservación porque genera muchos casos automáticamente sobre el dominio de entradas, atrapa edge cases y da garantías fuertes de que el comportamiento no cambió para las entradas no-buggy. Para este fix, el dominio "no-buggy" relevante es: el modelo Java de `User`, los contratos de los controladores y el comportamiento de EJM001..EJM006 — todos independientes del nombre físico de la tabla.

**Test Plan**: Observar primero el comportamiento sobre el código SIN fix (compilación de controladores/ejemplos, forma del modelo `User`, valores de `application.yml`), y luego escribir tests que capturen ese comportamiento para verificar que se mantiene tras el fix.

**Test Cases**:
1. **Modelo `User` preservado**: observar que `User` expone `id/firstName/secondName/firstLastName/secondLastName/dateBirth` con Lombok y `@ValidBirthday`; verificar que tras el fix la clase mantiene los mismos campos/tipos/validaciones (sólo cambió `@Table`).
2. **Contratos de controladores preservados**: observar que `UserController`, `NotificationController` y `ReportsController` compilan; verificar que tras el fix compilan y exponen los mismos contratos.
3. **Ejemplos EJM001..EJM006 preservados**: observar que compilan sin cambios; verificar que tras el fix siguen compilando sin cambios de firma ni lógica.
4. **Configuración preservada**: verificar que `application.yml` conserva `ddl-auto: create`, `defer-datasource-initialization: true`, `spring.sql.init.mode: always`, puerto `8088` y context-path `/capacitance`.

### Unit Tests

- Verificar que la entidad `Rule` mapea los 4 campos (`id`, `code`, `description`, `active`) que el seed inserta.
- Verificar que `User` conserva sus campos, tipos y la validación `@ValidBirthday` (el cambio es sólo `@Table`).
- Casos borde de arranque: presencia de la tabla `ruls` y ausencia de errores de DDL.

### Property-Based Tests

- Generar instancias arbitrarias de `Rule` y verificar el round-trip de campos (getters/setters/builder) para asegurar que la entidad refleja el contrato del seed.
- Generar entradas de validación de `User` (fechas de nacimiento válidas/ inválidas según `minAge = 15`) y verificar que el comportamiento de validación es idéntico antes y después del fix (preservación 3.1).

### Integration Tests

- **Test de carga de contexto (primario)**: `@SpringBootTest` que verifique que el `ApplicationContext` carga correctamente (equivale a "la app arranca") y que `SELECT COUNT(*) FROM ruls` devuelve 2 filas con los valores esperados (`RNI-0001` activo, `RNI-0002` inactivo). Este test cubre Fix Checking end-to-end sin depender de un arranque manual.
- **Flujo de arranque completo**: confirmar que Tomcat queda escuchando en `8088` con context-path `/capacitance` (arranque real) sin excepciones de DDL ni de seed.
- **Preservación de endpoints**: confirmar que los controladores existentes responden con los mismos contratos tras el fix.
