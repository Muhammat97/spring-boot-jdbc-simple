# Simple Spring Boot Project

### With Spring Data JDBC and PostgreSQL

## Feature

- Get all customers (GET /)
- Add customer (POST /)
- Get all transactions (GET /transaction)
- Get customer transactions (GET /{custUuid}/transaction)
- Add transaction (POST /{custUuid}/transaction)
- Etc

## Prerequisite

1. Create all table with query [cooperative_ddl.sql](doc/query/cooperative_ddl.sql)
![ER Diagram](doc/image/cooperative%20-%20test.png "ER Diagram")
2. Insert initial data with query [cooperative_dml.sql](doc/query/cooperative_dml.sql)

## Run

App will run on port 8081

    ./mvnw spring-boot:run

## Test

### JUnit

Test class: [CooperativeApplicationTests.java](src/test/java/com/m97/cooperative/CooperativeApplicationTests.java)

Integration test class: [A1Tests.java](src/test/java/com/m97/cooperative/integrationtest/A1Tests.java)

![JUnit Test](doc/image/JUnitTest.png "JUnit Test")

    ./mvnw test

With integration test

    ./mvnw clean verify

### Postman

You can import this collection in [this repository](doc/Cooperative.postman_collection.json)

#### Positive Case

- Get all customers
![Get All Customers](doc/image/PostmanGetAllCust1.png "Get All Customers")

- Add customer
![Add Customer](doc/image/PostmanEntryCust1.png "Entry Customer")

#### Negative Case

- Full Name `null`
![Full Name null](doc/image/PostmanNEntryCustNameRequired1.png "Full Name null")

- Full Name longer than 60 characters
![Full Name Max 60](doc/image/PostmanNEntryCustNameMax601.png "Full Name Max 60")

- Birth Date in wrong format
![Birth Date Wrong Format](doc/image/PostmanNEntryCustBirthDateFormat1.png "Birth Date Wrong Format")

- Hamlet Number with non-number value
![Hamlet Number](doc/image/PostmanNEntryCustHamletNumDigit1.png "Hamlet Number")
