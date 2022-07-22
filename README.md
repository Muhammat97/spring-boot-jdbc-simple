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
![Get All Customers Test](doc/image/PostmanGetAllCust2.png "Get All Customers Test")

- Add customer
![Add Customer](doc/image/PostmanEntryCust1.png "Entry Customer")
![Add Customer Test](doc/image/PostmanEntryCust2.png "Entry Customer Test")

- Get all transactions
![Get All Transactions](doc/image/PostmanGetAllTrans1.png "Get All Transactions")
![Get All Transactions Test](doc/image/PostmanGetAllTrans2.png "Get All Transactions Test")

- Get customer transactions
![Get Customer Transactions](doc/image/PostmanGetAllTransByCust1.png "Get Customer Transactions")
![Get Customer Transactions](doc/image/PostmanGetAllTransByCust2.png "Get Customer Transactions Test")

- Add transaction
![Add Transaction](doc/image/PostmanEntryTrans1.png "Add Transaction")
![Add Transaction Test](doc/image/PostmanEntryTrans2.png "Add Transaction Test")

#### Negative Case

- Full Name `null`
![Full Name null](doc/image/PostmanNEntryCustNameRequired1.png "Full Name null")
![Full Name null Test](doc/image/PostmanNEntryCustNameRequired2.png "Full Name null Test")

- Full Name less than 3 characters long
![Full Name Min 3](doc/image/PostmanNEntryCustNameMin31.png "Full Name Min 3")
![Full Name Min 3 Test](doc/image/PostmanNEntryCustNameMin32.png "Full Name Min 3 Test")

- Full Name longer than 60 characters
![Full Name Max 60](doc/image/PostmanNEntryCustNameMax601.png "Full Name Max 60")
![Full Name Max 60 Test](doc/image/PostmanNEntryCustNameMax602.png "Full Name Max 60 Test")

- Birth Date in wrong format
![Birth Date Wrong Format](doc/image/PostmanNEntryCustBirthDateFormat1.png "Birth Date Wrong Format")
![Birth Date Wrong Format Test](doc/image/PostmanNEntryCustBirthDateFormat2.png "Birth Date Wrong Format Test")

- Birth Date with future value
![Birth Date Future](doc/image/PostmanNEntryCustBirthDateFuture1.png "Birth Date Future")
![Birth Date Future Test](doc/image/PostmanNEntryCustBirthDateFuture2.png "Birth Date Future Test")

- Hamlet Number with non-number value
![Hamlet Number](doc/image/PostmanNEntryCustHamletNumDigit1.png "Hamlet Number")
![Hamlet Number Test](doc/image/PostmanNEntryCustHamletNumDigit2.png "Hamlet Number Test")

- Transaction Amount with negative value
![Transaction Amount Negative](doc/image/PostmanNEntryTransNegativeAmount1.png "Transaction Amount Negative")
![Transaction Amount Negative Test](doc/image/PostmanNEntryTransNegativeAmount2.png "Transaction Amount Negative Test")
