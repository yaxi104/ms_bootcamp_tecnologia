# Reactive Technology API

API REST reactiva para gestionar tecnologías de programación. Permite registrar nuevas tecnologías y obtener una lista
paginada de las existentes.

## Tecnologías utilizadas

* ![Java](https://img.shields.io/badge/Java_17+-007396?style=for-the-badge&logo=java&logoColor=white)
* ![Spring Boot](https://img.shields.io/badge/Spring_Boot_3.2.5-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
* ![Spring WebFlux](https://img.shields.io/badge/Spring_WebFlux-reactive-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
* ![Project Reactor](https://img.shields.io/badge/Project_Reactor-3.5.0-6DB33F?style=for-the-badge)
* ![Swagger](https://img.shields.io/badge/Swagger_OpenAPI-3.0-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)
* ![JUnit 5](https://img.shields.io/badge/JUnit_5-testing-25A162?style=for-the-badge)
* ![Mockito](https://img.shields.io/badge/Mockito-mocking-FFCA28?style=for-the-badge)
* ![MapStruct](https://img.shields.io/badge/MapStruct-1.5.5.Final-FF6F00?style=for-the-badge)
* ![MySQL](https://img.shields.io/badge/MySQL-R2DBC-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
* ![Gradle](https://img.shields.io/badge/Gradle-build_tool-02303A?style=for-the-badge&logo=gradle&logoColor=white)

## Configuración (application.yml)

```yaml
server:
  port: 8080

spring:
  r2dbc:
    url: r2dbc:mysql://localhost:3306/TECNOLOGIA
    username: //Usuario
    password: //Constraseña
  sql:
    init:
      platform: mysql
      mode: always
  main:
    web-application-type: reactive

springdoc:
  swagger-ui:
    path: /swagger-ui.html
    enabled: true
  api-docs:
    path: /v3/api-docs

appDescription: "Bootcamp tecnology API"
appVersion: "1.0.0"

```

## Endpoints disponibles
### POST /api/v1/tecnology
Registra una nueva tecnología.

##### Request body:

```
json
{
"name": "Java",
"description": "Lenguaje de programación orientado a objetos"
}
```
##### Respuestas:

201 Created: Tecnología creada exitosamente.

400 Bad Request: Datos inválidos.

409 Conflict: Tecnología ya existe.

### GET /api/v1/tecnology/all?page=0&size=10
Obtiene una lista paginada de tecnologías.

##### Respuesta ejemplo:

```
json
{
"content": [
{
"id": 1,
"name": "Java",
"description": "Lenguaje de programación orientado a objetos"
},
{
"id": 2,
"name": "Python",
"description": "Lenguaje versátil"
}
],
"page": 0,
"size": 10,
"totalElements": 100,
"totalPages": 10
}
```
## Documentación Swagger
Disponible en: http://localhost:8080/swagger-ui.html