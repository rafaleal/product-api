# Product REST API
This is a Product API prototype using Spring Boot, OpenFeign and MongoDB running in docker container.

### Main functions:
- Product creation (standard price in EUR)
- Product removal
- Create products in other currencies and automatically convert to EUR
- Management of product categories (assigning and removing from products)

## Requirements
- Java 8
- Maven 3.3.9
- Docker 18.09.2
- docker-compose 1.23.2

## Building and Running

### Build
Run the following command to build the jar

```mvn clean package dockerfile:build```
 
 
 ### Running
Launch an Application and a Mongo container

```docker-compose up```

### REST API Documentation
Access Swagger UI page with the REST API documentation

```http://localhost:9090/swagger-ui.html#/```
