# Spring Boot REST API

A RESTful API built with Spring Boot that provides CRUD operations for managing person records. This API is deployed and accessible at [https://springboot-rest-api-kfdtkw.fly.dev/api/v1/persons](https://springboot-rest-api-kfdtkw.fly.dev/api/v1/persons).

> **Important Note**: When making your first request to the API, please allow approximately 30 seconds for the fly.io server to spin up. The initial request may time out, but subsequent requests will be much faster once the server is running.

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [API Endpoints](#api-endpoints)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Running Locally](#running-locally)
    - [Docker Deployment](#docker-deployment)
- [Database](#database)
- [API Usage Examples](#api-usage-examples)
- [Error Handling](#error-handling)

## Features

- Create, Read, Update, and Delete person records
- Data validation with error messages
- Sorting capabilities
- In-memory H2 database
- Exception handling with meaningful error responses
- Dockerized for easy deployment

## Technologies

- Java 17
- Spring Boot 3.2.5
- Spring Data JPA
- H2 Database
- Maven
- Docker
- fly.io (for deployment)

## API Endpoints

| Method | URL                                | Description                   | Request Body | Query Parameters |
|--------|------------------------------------|------------------------------ |--------------|------------------|
| GET    | /api/v1/persons                    | Get all persons               | -            | sort (ASC/DESC)  |
| GET    | /api/v1/persons/{id}               | Get person by ID              | -            | -                |
| POST   | /api/v1/persons                    | Create a new person           | JSON         | -                |
| PUT    | /api/v1/persons/{id}               | Update an existing person     | JSON         | -                |
| DELETE | /api/v1/persons/{id}               | Delete a person               | -            | -                |

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven
- Docker (optional)

### Running Locally

1. Clone the repository:
```bash
git clone https://github.com/yourusername/spring-boot-rest-api.git
cd spring-boot-rest-api
```

2. Build the project:
```bash
mvn clean package
```

3. Run the application:
```bash
java -jar target/spring-boot-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080`.

### Docker Deployment

1. Build the Docker image:
```bash
docker build -t spring-boot-api .
```

2. Run the container:
```bash
docker run -p 8080:8080 spring-boot-api
```

## Database

The application uses an H2 in-memory database. The H2 console is enabled and can be accessed at:
- URL: `http://localhost:8080/h2-console` (when running locally)
- JDBC URL: `jdbc:h2:mem:medom5`
- Username: `sa`
- Password: ` ` (empty)

## API Usage Examples

### Get All Persons
```bash
# Get all persons (default sort: ASC)
curl -X GET https://springboot-rest-api-kfdtkw.fly.dev/api/v1/persons

# Get all persons sorted in descending order
curl -X GET https://springboot-rest-api-kfdtkw.fly.dev/api/v1/persons?sort=DESC
```

### Get Person by ID
```bash
curl -X GET https://springboot-rest-api-kfdtkw.fly.dev/api/v1/persons/1
```

### Create a New Person
```bash
curl -X POST https://springboot-rest-api-kfdtkw.fly.dev/api/v1/persons \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "age": 25,
    "gender": "MALE",
    "email": "john.doe@example.com"
  }'
```

### Update an Existing Person
```bash
curl -X PUT https://springboot-rest-api-kfdtkw.fly.dev/api/v1/persons/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Updated",
    "age": 26,
    "email": "john.updated@example.com"
  }'
```

### Delete a Person
```bash
curl -X DELETE https://springboot-rest-api-kfdtkw.fly.dev/api/v1/persons/1
```

## Error Handling

The API returns appropriate HTTP status codes and error messages:

- `200 OK`: Request successful
- `400 BAD REQUEST`: Invalid input (validation errors)
- `404 NOT FOUND`: Resource not found
- `409 CONFLICT`: Resource already exists (e.g., duplicate email)
- `500 INTERNAL SERVER ERROR`: Server error

Error response format:
```json
{
  "path": "/api/v1/persons/999",
  "message": "Person with id: 999 does not exist",
  "statusCode": 404,
  "zonedDateTime": "2025-03-31T12:00:00Z",
  "errors": []
}
```
