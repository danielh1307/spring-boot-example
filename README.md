# Spring Boot Example

## Package structure

 * `config` - all Spring Boot configuration files.
 * `domain` - contains domain objects. This package must not have a dependency to any class outside this package.
 * `boundary` - contains the application layer, the "use cases" of the application. Has dependencies to the `domain` package but not to `infrastructure`. 
 * `infrastructure` - contains infrastructure objects (e.g. `@Controller` classes.) Calls services from the `boundary` but not the `domain` objects directly.

## Maven commands

Use

```bash
$ ./mvnw dependency:tree
```

to get an overview over all dependencies.

## Start / deliver the application

### Maven

To run the application, just type

```bash
$ ./mvnw spring-boot:run
```

This works since spring-boot-starter-parent is the parent of our pom.

### Create an executable .jar

```bash
$ ./mvnw package
```

This works since spring-boot-maven-plugin is configured as Maven plugin (spring-boot-loader creates an executable .jar).

Execute the .jar:

```bash
$ java -jar target/spring-boot-example.jar
```

### Start with a different profile

```bash
$ ./mvnw spring-boot:run -Dspring-boot.run.profiles=<PROFILE> # with maven
$ java -jar target/spring-boot-example.jar --spring.profiles.active=dev # as .jar file
```

### Pass custom arguments

For this to work, `SpringApplication.setAddCommandLineProperties()` must be `true` (which is the default):

```bash
$ ./mvnw spring-boot:run -Dspring-boot.run.arguments=--myProperty="different value" # with maven
$ java -jar target/spring-boot-example.jar --myProperty="different value" # as .jar file
```

Of course you can also set a system property:

If you use the Spring Boot Maven plugin, add the following to the configuration of the plugin:

```xml
<configuration>
	<systemPropertyVariables>
		<myProperty>different value</myProperty>
	</systemPropertyVariables>
</configuration>
```

If you start the .jar file directly:

```bash
$ java -DmyProperty="different value" -jar target/spring-boot-example.jar # as .jar file
```

## Call a service

`$ curl -X GET http://localhost:8080/hello?title=World`

### Content negotiation

TODO

## Swagger

```bash
$ curl http://localhost:8080/v2/api-docs # as JSON
$ curl http://localhost:8080/swagger-ui.html # as HTML
```

## Persistence

### JDBC

Use `spring-boot-starter-jdbc` to configure JDBC subsystem.

The database is configured with properties in `application.properties`. To access the database, use
`NamedParameterJdbcTemplate` or `JdbcTemplate` which can be injected thanks to the starter.

You can then use SQL statements to access the database.

If you are not using flyway, you can initialize the database with files `schema.sql` and `data.sql`.

For an example, see `JdbcFilmRepository`.

### JPA

Use `spring-boot-starter-data-jpa` to configure JDBC / JPA subsystem. Default JPA implementation is Hibernate.

Add `@Entity` to the entity classes (for an example, see `Person`). The schema can be created by default,
this can be configured in `application.properties` with `spring.jpa.generate-ddl`. You can also just let the
schema be validated, this is configured by `spring.jpa.hibernate.ddl-auto=validate`.

You can use one of Spring Boots CRUD repositories, see `PersonJpaRepository` for an example.