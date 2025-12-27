# jwt-api-key-mvc
Simple JAVA RestAPI


````markdown
# JWT API Key MVC

A simple RESTful API using **Jersey + Jetty + MySQL** with **API Key authentication** and proper validation.  
No connection pool is used; all DB operations are handled with plain JDBC.  

---

## Table of Contents

- [Prerequisites](##prerequisites)  
- [Database Setup](#database-setup)  
- [Project Setup](#project-setup)  
- [Running the Application](#running-the-application)  
- [API Endpoints](#api-endpoints)  
- [Example Requests](#example-requests)  

---

## Prerequisites

- Java 11+  
- Maven 3.x  
- MySQL 5.0+  

---

## Database Setup

1. Connect to MySQL and create the database:

```sql
CREATE DATABASE jwt_db;
````

2. Create the tables:

```sql
CREATE TABLE `api_keys` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `client_name` varchar(255) NOT NULL,
  `api_key` varchar(255) NOT NULL,
  `active` tinyint(1) DEFAULT '1',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `api_key` (`api_key`)
);

CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);
```

3. Insert an initial API key:

```sql
INSERT INTO api_keys (client_name, api_key, active) 
VALUES ('Client A', '123e4567-e89b-12d3-a456-426614174000', 1);
```

---

## Project Setup

1. Clone the repository:

```bash
git clone <repo-url>
cd jwt-api-key-mvc
```

2. Update **database credentials** in:

* `com.example.api.dao.UserDao.java`
* `com.example.api.dao.ApiKeyDao.java`

```java
private static final String URL = "jdbc:mysql://localhost:3306/jwt_db";
private static final String USER = "root";
private static final String PASS = "root";
```

---

## Running the Application

1. Compile the project:

```bash
mvn clean compile
```

2. Run the application:

```bash
mvn exec:java -Dexec.mainClass="com.example.api.App"
```

The server will start on **[http://localhost:8080/api](http://localhost:8080/api)**

---

## API Endpoints

### Users

| Method | URL                  | Description          |
| ------ | -------------------- | -------------------- |
| POST   | `/users/add`         | Add a new user       |
| GET    | `/users`             | List all users       |
| POST   | `/users/update/{id}` | Update existing user |
| GET    | `/users/delete/{id}` | Delete user by ID    |

### Headers

All requests require API Key in the header:

```
X-API-KEY: 123e4567-e89b-12d3-a456-426614174000
```

---

## Example Requests

### Add User

```http
POST http://localhost:8080/api/users/add
Content-Type: application/json
X-API-KEY: 123e4567-e89b-12d3-a456-426614174000

{
  "name": "John Doe",
  "email": "john@example.com"
}
```

### List Users

```http
GET http://localhost:8080/api/users
X-API-KEY: 123e4567-e89b-12d3-a456-426614174000
```

### Update User

```http
POST http://localhost:8080/api/users/update/1
Content-Type: application/json
X-API-KEY: 123e4567-e89b-12d3-a456-426614174000

{
  "name": "John Smith",
  "email": "john.smith@example.com"
}
```

### Delete User

```http
GET http://localhost:8080/api/users/delete/1
X-API-KEY: 123e4567-e89b-12d3-a456-426614174000
```

---

## Response Format

All responses follow a consistent JSON structure:

```json
{
  "success": true|false,
  "message": "Informative message",
  "data": {}
}
```

---

## Notes

* Validation is handled automatically using `@Valid` and Bean Validation (`@NotBlank`, `@Email`, `@Size`)
* API Key authentication is mandatory for all endpoints
* Exception handling returns proper JSON messages, including validation and runtime errors

```



