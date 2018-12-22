# Spring Boot Example

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

TODO

## Call a service

`curl -X GET http://localhost:8080/hello?title=World`

## Swagger

TODO