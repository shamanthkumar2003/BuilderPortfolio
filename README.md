
#  Portfolio Management System

## Problem Statement
The local Example Association wants to digitize their portfolio tracking to eliminate data loss, avoid miscommunication, and improve client engagement.

This Spring Boot backend application supports basic operations for users and projects, with corresponding unit tests to ensure quality.

---

## Core Features

### 1. User Management
- **Entity**: `User`
- **Fields**:
  - `id` (Long)
  - `name` (String)
  - `email` (String)
  - `role` (Enum: `ADMIN`, `EXAMPLE`, `CLIENT`)

- **API Endpoints**:
  - `POST /users` – Create a new user
  - `GET /users` – Retrieve all users

### 2. Project Management
- **Entity**: `Project`
- **Fields**:
  - `id` (Long)
  - `title` (String)
  - `description` (String)
  - `status` (Enum: `UPCOMING`, `IN_PROGRESS`, `COMPLETED`)
  - `clientId` (Long)
  - `exampleId` (Long)

- **API Endpoints**:
  - `POST /projects` – Add a new project
  - `GET /projects` – Get all projects
  - `GET /projects/{id}` – Get project by ID
  - `PUT /projects/{id}` – Update project
  - `DELETE /projects/{id}` – Delete project

---

## Unit Testing Requirements

### Service Layer Tests
- `createUser()` should save and return the user.
- `getAllProjects()` should return the list of projects.

### Controller Layer Tests
- `GET /users` should return HTTP 200 and a list of users.
- `POST /projects` should return HTTP 201 when a project is created.

> **Note**: Your solution must include at least two test methods per controller and service class.

---

## Prerequisites
- **Java**: 17+
- **Maven**: 3.6+
- **MySQL Server**: Installed and running
- **IDE**: IntelliJ IDEA, Postman for testing
---

## Setup Instructions

### 1. Clone the Repository
```bash
git clone <https://github.com/shamanthkumar2003/BuilderPortfolio.git>
cd portfolio
```

### 2. Database Setup
Database Setup Documentation for the Project
1. Install MySQL
   Download and install MySQL from the official MySQL website.
   After installation, ensure that the MySQL server is running.
2. Access MySQL
   Open the terminal (macOS/Linux) or Command Prompt (Windows), and log in to MySQL:

mysql -u root -p
When prompted, enter your MySQL root password.
3. Create the Database
   Execute the following SQL command to create the project-specific database:

CREATE DATABASE builderPortfolio;
To confirm that the database was created successfully:
SHOW DATABASES;
4. Configure the Application
   Update the application.properties file of your Spring Boot application with the correct database connection details:

spring.datasource.url=jdbc:mysql://localhost:3306/builderPortfolio
spring.datasource.username=root
spring.datasource.password=mysql@123
5. Create Tables
   Use the following SQL scripts to manually create the required tables in the builderPortfolio database.
   users Table
   CREATE TABLE users (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   email VARCHAR(255) NOT NULL UNIQUE,
   role ENUM('ADMIN', 'BUILDER', 'CLIENT') NOT NULL
   );


projects Table

CREATE TABLE projects (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
title VARCHAR(255) NOT NULL,
description VARCHAR(1000) NOT NULL,
status ENUM('UPCOMING', 'IN_PROGRESS', 'COMPLETED') NOT NULL,
client_id BIGINT NOT NULL,
builder_id BIGINT NOT NULL
);
6. Enable Auto Table Creation (Optional)
   To allow Spring Boot to automatically generate and update tables based on your entity classes, include the following configuration in application.properties:

spring.jpa.hibernate.ddl-auto=update
Other possible values: create, create-drop, validate, none.
7. Test the Connection
   Start your Spring Boot application. Then verify that the tables exist by accessing MySQL and running:

USE builderPortfolio;
SHOW TABLES;

This completes the database setup for the project. The database and tables are now ready for use by the application.


### 3. Configure the Application
Update the `src/main/resources/application.properties` file with your database credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/examplePortfolio
spring.datasource.username=root
spring.datasource.password=your password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

server.port=8081
```

### 4. Build and Run
- Build the project with Maven:
  ```bash
  mvn clean install
  ```
- Run the application:
  ```bash
  mvn spring-boot:run
  ```
- Access the app at: [http://localhost:8081](http://localhost:8081)

---

## Running Tests
Run all unit tests with:
```bash
mvn test
```
# BuilderPortfolio
