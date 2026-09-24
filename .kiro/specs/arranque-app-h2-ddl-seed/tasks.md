# Implementation Plan

- [ ] 1. Escribir test de exploración de la condición del bug (arranque de contexto)
  - **Property 1: Bug Condition** - Arranque exitoso del contexto con esquema y seed válidos
  - **CRITICAL**: Este test DEBE FALLAR sobre el código SIN fix — su fallo confirma que el bug existe
  - **DO NOT attempt to fix the test or the code when it fails** en este paso
  - **NOTE**: Este test codifica el comportamiento esperado — validará el fix cuando pase después de implementarlo (task 3.2)
  - **GOAL**: Surfacear los counterexamples que demuestran el bug (identificador reservado `user`, `Table "RULS" not found`, columna `'active'` citada)
  - **Scoped PBT Approach**: el bug es determinista (se dispara en cada arranque con el estado actual), así que se acota a los casos concretos de arranque: (a) DDL de `User`, (b) seed sobre `ruls`, (c) columna `active`
  - Crear un `@SpringBootTest` de carga de contexto en `backend-capacitacion/src/test/java/com/capacitacion/` (p. ej. `ArranqueContextoRulsTest.java`) que arranque el `ApplicationContext` de `CapacitacionApiApplication` y verifique carga sin excepciones de DDL ni de seed (de `isBugCondition`: `startupInitializesSchemaAndSeed` + `userEntityMapsToReservedTableName` OR `seedTargetsMissingRulsTable` OR `seedUsesQuotedColumnActive` del design)
  - El test debe reflejar las Expected Behavior Properties del design: contexto levanta, `SELECT COUNT(*) FROM ruls` = 2, y valores `RNI-0001 active=true` / `RNI-0002 active=false`
  - Ejecutar el test sobre el código SIN fix con `./mvnw test -Dtest=ArranqueContextoRulsTest` (desde `backend-capacitacion`)
  - **EXPECTED OUTCOME**: el test FALLA (correcto — prueba que el bug existe): `JdbcSQLSyntaxErrorException: expected "identifier"` en `[*]user`, `Table "RULS" not found` durante `dataSourceScriptDatabaseInitializer`, y/o `Application run failed`
  - Documentar los counterexamples observados para entender la causa raíz
  - Marcar la tarea como completa cuando el test esté escrito, ejecutado y el fallo documentado
  - _Requirements: 1.1, 1.2, 1.3, 1.4, 1.5_

- [ ] 2. Escribir tests de preservación (ANTES de implementar el fix)
  - **Property 2: Preservation** - Modelo, contratos, ejemplos y configuración sin cambios
  - **IMPORTANT**: Seguir la metodología observation-first (observar el comportamiento sobre el código SIN fix y capturarlo)
  - Observar sobre el código SIN fix (cases donde `isBugCondition` retorna false, independientes del nombre físico de la tabla):
    - El modelo `User` expone `id`, `firstName`, `secondName`, `firstLastName`, `secondLastName`, `dateBirth` con Lombok y la validación `@ValidBirthday(minAge = 15)`
    - `UserController`, `NotificationController`, `ReportsController` compilan con los mismos contratos request/response
    - Los ejemplos EJM001..EJM006 compilan sin cambios de firma ni de lógica
    - `application.yml` conserva `jdbc:h2:mem:managemt`, `ddl-auto: create`, `defer-datasource-initialization: true`, `spring.sql.init.mode: always`, puerto `8088`, context-path `/capacitance`, consola H2 en `/h2-console`
  - Escribir tests que capturen esos comportamientos observados (de Preservation Requirements del design):
    - Test de validación de `User`: property-based sobre fechas de nacimiento válidas/inválidas según `minAge = 15`, verificando que `@ValidBirthday` se comporta idéntico (usar el motor de validación real; recomendado property-based para cubrir el dominio de fechas)
    - Test de que la clase `User` mantiene los mismos campos/tipos (round-trip getters/setters/builder)
    - Test/verificación de que los controladores y EJM001..EJM006 compilan (el propio `./mvnw test-compile` es la señal; opcionalmente un test de forma de contrato)
    - Test que asserta los valores de `application.yml` (p. ej. leyendo las properties inyectadas: `server.port=8088`, `server.servlet.context-path=/capacitance`)
  - Property-based testing recomendado para preservación: genera muchos casos automáticamente y da garantías fuertes de que el comportamiento no cambió
  - Ejecutar los tests sobre el código SIN fix con `./mvnw test` (los tests de preservación deben compilar/ejecutarse sin depender del arranque completo del contexto)
  - **EXPECTED OUTCOME**: los tests de preservación PASAN sobre el código SIN fix (confirma el baseline a preservar)
  - Marcar la tarea como completa cuando los tests estén escritos, ejecutados y pasando sobre el código sin fix
  - _Requirements: 3.1, 3.2, 3.3, 3.4, 3.5_

- [ ] 3. Fix para el arranque de la app (H2 DDL + seed)

  - [x] 3.1 Implementar el fix (3 cambios acotados en 2 archivos + 1 nuevo)
    - Cambio 1 — `backend-capacitacion/src/main/java/com/capacitacion/model/entity/User.java`: agregar `import jakarta.persistence.Table;` y anotar la clase con `@Table(name = "users")` junto a `@Entity`. NO tocar campos, tipos, anotaciones Lombok, `@Id`, `@Schema` ni `@ValidBirthday(minAge = 15)`
    - Cambio 2 — crear `backend-capacitacion/src/main/java/com/capacitacion/model/entity/Rule.java`: entidad `@Entity` + `@Table(name = "ruls")`, paquete `com.capacitacion.model.entity`, con mismas anotaciones Lombok que `User` (`@Getter/@Setter/@NoArgsConstructor/@AllArgsConstructor/@ToString/@Builder`) y campos `Integer id` (`@Id`), `String code`, `String description`, `boolean active`
    - Cambio 3 — `backend-capacitacion/src/main/resources/data.sql`: reemplazar la columna `'active'` (comillas simples) por `active` (sin comillas) en las 2 filas, manteniendo id/code/description y los valores `RNI-0001` (true) / `RNI-0002` (false)
    - Cambio 4 — `backend-capacitacion/src/main/resources/application.yml`: SIN cambios (requisito de preservación 3.4)
    - Verificar con `./mvnw compile` que compila en 0 errores (desde `backend-capacitacion`)
    - _Bug_Condition: isBugCondition(X) = startupInitializesSchemaAndSeed(X) AND (userEntityMapsToReservedTableName(X) OR seedTargetsMissingRulsTable(X) OR seedUsesQuotedColumnActive(X))_
    - _Expected_Behavior: expectedBehavior(result) del design — contextStartsSuccessfully AND no_ddl_syntax_error AND rulsTableExists AND rulsSeeded(rows=2) AND tomcatListening(8088, "/capacitance")_
    - _Preservation: Preservation Requirements del design — modelo/validación de `User`, contratos de controladores, EJM001..EJM006 y valores de `application.yml` inalterados_
    - _Requirements: 2.1, 2.2, 2.3, 2.4, 2.5_

  - [ ] 3.2 Verificar que el test de exploración de la condición del bug ahora pasa
    - **Property 1: Expected Behavior** - Arranque exitoso del contexto con esquema y seed válidos
    - **IMPORTANT**: Re-ejecutar el MISMO test de la task 1 — NO escribir un test nuevo
    - El test de la task 1 codifica el comportamiento esperado; cuando pasa, confirma que se satisface el Expected Behavior
    - Ejecutar el test de arranque de contexto de la task 1 con `./mvnw test -Dtest=ArranqueContextoRulsTest` (desde `backend-capacitacion`)
    - **EXPECTED OUTCOME**: el test PASA (confirma que el bug está corregido): contexto levanta sin errores de DDL ni de seed, `SELECT COUNT(*) FROM ruls` = 2 con `RNI-0001 active=true` / `RNI-0002 active=false`, y Tomcat escucha en `8088` `/capacitance`
    - _Requirements: 2.1, 2.2, 2.3, 2.4, 2.5_

  - [ ] 3.3 Verificar que los tests de preservación siguen pasando
    - **Property 2: Preservation** - Modelo, contratos, ejemplos y configuración sin cambios
    - **IMPORTANT**: Re-ejecutar los MISMOS tests de la task 2 — NO escribir tests nuevos
    - Ejecutar los tests de preservación de la task 2 con `./mvnw test` (desde `backend-capacitacion`)
    - **EXPECTED OUTCOME**: los tests PASAN (confirma que no hay regresiones): modelo/validación de `User`, contratos de controladores, EJM001..EJM006 y valores de `application.yml` inalterados
    - Confirmar que todos los tests siguen pasando tras el fix (sin regresiones)
    - _Requirements: 3.1, 3.2, 3.3, 3.4, 3.5_

- [ ] 4. Checkpoint - Asegurar que todos los tests pasan
  - Ejecutar la suite completa con `./mvnw test` (desde `backend-capacitacion`) y confirmar que el test de exploración (task 1) y los tests de preservación (task 2) pasan
  - Confirmar que `./mvnw compile` termina en 0 errores
  - Asegurar que todos los tests pasan; consultar al usuario si surgen dudas
