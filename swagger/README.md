# Swagger codegen

See https://github.com/swagger-api/swagger-codegen

Usually this would be part of the client project.

## Use the .jar file

```bash
$> java -jar swagger-codegen-cli.jar help
```

With the following command you can create just the client models (thanks to -Dmodels), otherwise everything would be created.
```bash
$> java -Dmodels -jar swagger-codegen-cli.jar generate -i http://localhost:8080/v2/api-docs -l java -o /Users/daniel/IdeaProjects/spring-boot-example-client
```
