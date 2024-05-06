# Reservation Service
This service stores and lists restaurant reservations

## Components
This service uses:
- Java 21
- Maven
- Spring Boot
    - Starter web
    - Starter JPA
    - Starter WebFlux
    - Starter Cloud LoadBalancer
    - Starter Test
- H2 Database
- Liquibase
- OpenApi

## Service Dependencies
This service depends on the Restaurant Service.

## Installation
To install run
`mvn clean install`

## Run
Before running this service, make sure that the restautant is running.

To run the service on port 8089, launch
`mvn spring-boot:run`

## Swagger
The Swagger page for his service is reachable at
http://localhost:8089/swagger-ui/index.html

