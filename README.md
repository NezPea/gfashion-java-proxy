# gfashion-java-proxy structure

## g-fashion-app
- main/java: Springboot Application bootstrap class
- main/java/config: Java-based Configuration
- resources/config: `application.yml` springboot main configuration
- test/java: Springboot test and integration test using [`rest-assured`](http://rest-assured.io/)

## g-fashion-api
- rest controller with all the endpoints

## g-fashion-domain
- g-fashion model class (i.e., domain entity) simplified with [`lombok`](https://projectlombok.org/) to reduce boilerplate
- annotated as model class in Swagger UI (i.e., DTO, swagger annotation should go here)

## g-fashion-client
- restclient/: rest client to call `Magento` REST API
- restclient/magento: `Magento` model class 
- restclient/magento/mapper: `Magento` model class mapper using [`MapStruct`](https://mapstruct.org/)

## g-fashion-data
- data/: JPA entity simplified with `lombok`
- data/repository: JPA repository backed by Spring Data

## g-fashion-security
- spring security TBD

# code style and convention

## Exception Handling

- Use [`ResponseStatusException`](https://www.baeldung.com/spring-response-status-exception) offered by Spring 5
- Until we really need a global `@ExceptionHandler` with the `@ControllerAdvice` annotation

## Code Commit Notes

before your code commit, please make sure
- merge upstream code changes (`develop` and `master` branch)
- review all issues reported by SonarLint (an intellij plug-in)
- format your code (IDE default is acceptable at the moment)
- and try our best to follow a higher standard 
    - https://google.github.io/styleguide/javaguide.html
    - https://github.com/spring-projects/spring-framework/wiki/Code-Style
