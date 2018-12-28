# Spring Boot Example

## Package structure

 * `config` - all Spring Boot configuration files.
 * `domain` - contains domain objects. This package must not have a dependency to any class outside this package.
 * `boundary` - contains the application layer, the "use cases" of the application. Has dependencies to the `domain` package but not to `infrastructure`. 
 * `infrastructure` - contains infrastructure objects (e.g. `@Controller` classes.) Calls services from the `boundary` but not the `domain` objects directly.

## Maven commands

Use

```bash
$> ./mvnw dependency:tree
```

to get an overview over all dependencies.

## Start / deliver the application

### Maven

To run the application, just type

```bash
$> ./mvnw spring-boot:run
```

This works since spring-boot-starter-parent is the parent of our pom.

### Create an executable .jar

```bash
$> ./mvnw package
```

This works since spring-boot-maven-plugin is configured as Maven plugin (spring-boot-loader creates an executable .jar).

Execute the .jar:

```bash
$> java -jar target/spring-boot-example.jar
```

### Start with a different profile

```bash
$> ./mvnw spring-boot:run -Dspring-boot.run.profiles=<PROFILE> # with maven
$> java -jar target/spring-boot-example.jar --spring.profiles.active=dev # as .jar file
```

### Pass arguments

For this to work, SpringApplication.setAddCommandLineProperties() must be TRUE (which is the default):

```bash
$> ./mvnw spring-boot:run -Dspring-boot.run.arguments=--myProperty="different value" # with maven
$> java -jar target/spring-boot-example.jar --myProperty="different value" # as .jar file
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
$> java -DmyProperty="different value" -jar target/spring-boot-example.jar # as .jar file

## Call a service

`curl -X GET http://localhost:8080/hello?title=World`

## Swagger

```bash
$> curl http://localhost:8080/v2/api-docs # as JSON
$> curl http://localhost:8080/swagger-ui.html # as HTML
