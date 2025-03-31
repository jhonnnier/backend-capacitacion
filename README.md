# backend-capacitacion
* [Project in GitHub](https://github.com/jhonnnier/backend-capacitacion)
* [Lombok](https://projectlombok.org/)
* [Project generator](https://start.spring.io/)
* [Swagger](http://localhost:8080/capacitance/swagger-ui/index.html)
* [H2 - console](http://localhost:8080/capacitance/h2-console)
* [SDKMan](#section-sdk-man)


| Dependencia                                                   | descripción                           |
|---------------------------------------------------------------|---------------------------------------|
| [spring-boot-starter-aop](https://acortar.link/kBg46w)|Activa las anotaciones personalizadas |
| [spring-boot-starter-validation](https://acortar.link/PimErT) | Activa los validators personalizados|
|                                                               |                                       |

* [H2 - console](http://localhost:8080/capacitance/h2-console)
  ![H2-console](src/main/resources/images/img-0003.png)

* [Swagger](http://localhost:8080/capacitance/swagger-ui/index.html)
  ![Swagger](src/main/resources/images/img-0004.png)

* Configurar UTF-8
  ![configure UTF-8](src/main/resources/images/img-0001.png)

* Configurar Lombok
  ![configure Lombok](src/main/resources/images/img-0002.png)

### <section id="section-sdk-man">Instalar SDKMan</section>
```
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk version
```
- Instalar JAVA con SDKMan

```
sdk list java
sdk install java 17.0.12-oracle
sdk use 17.0.12-oracle
java -version

sdk list java
sdk install java 11.0.14.1-jbr
sdk use java 11.0.14.1-jbr
java -version

sdk list java
sdk install java 21.0.6-oracle
sdk use java 21.0.6-oracle
java -version

sdk list java
sdk install java 8.0.442-zulu
sdk use java 8.0.442-zulu
java -version
```