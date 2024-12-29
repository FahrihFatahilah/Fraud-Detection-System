# Transaction Fraud Detection System

This project provides a service that validates transactions based on various criteria to detect suspicious activities. It checks conditions like blacklisted IP addresses, frequent location changes, user-agent mismatches, velocity of movement, unusual transaction amounts, and more.

## Features

- Validates transactions based on multiple factors:
  - **Blacklisted IP Address**
  - **Frequent Location Changes**
  - **User-Agent Mismatch**
  - **Velocity and Movement**
  - **Transaction Amount Patterns**
  - **Transaction at Odd Hours**
  - **Historical Anomalies**
- Saves valid transactions in the database.
- Flags suspicious transactions and provides error responses.

## Requirements

- Java 21 or later
- Spring Boot framework
- MySql or another database for storing transactions

## Setup

### 1. Clone the repository


```bash
  git clone https://github.com/FahrihFatahilah/Fraud-Detection-System]
```

### 2. Build the project using Maven

./mvnw clean install

### 3.  Configure your database in application.properties
```spring.application.name=fds
spring.datasource.url=jdbc:mysql://localhost:3306/fds
spring.datasource.username=root
spring.datasource.password=
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.current_session_context_class=thread
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
logging.level.org.hibernate.type=TRACE
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

### 4. Run application
```bash
./mvnw spring-boot:run
```

# Usage
To submit a transaction for validation, you can send a POST request to the /transactions endpoint with a JSON payload like this:
```json
{
  "userId": "userId",
  "ipAddress": "192.168.1.102",
  "location": "Texas, USA",
  "transactionAmount": 150222.00,
  "transactionType": "Login",
  "transactionDate": "2024-12-27T10:00:00",
  "userAgent": "Mozilla/5.0",
  "deviceOs": "iOS",
  "deviceBrand": "Apple",
  "appName": "BankApp",
  "appVersion": "1.2.3",
  "suspiciousFlag": false,
  "createdAt": "2024-12-27T09:50:00",
  "updatedAt": "2024-12-27T10:00:00",
  "channelName": "Mobile"
}
```
