# Spring Boot Microservices — Dockerized

This project demonstrates a simple microservices architecture built using Spring Boot and secured with JWT authentication.

The system is composed of three independent services:

• **API Gateway** — entry point for all client requests
• **Auth Service** — handles user authentication and token generation
• **Product Service** — protected business APIs

All client traffic flows through the Gateway, which validates the JWT token before forwarding requests to downstream services.

---

## Architecture Flow

Client → API Gateway → Auth Service → Product Service

---

## Tech Stack

Java 17
Spring Boot
Spring Security
JWT Authentication
REST APIs
Maven
Docker
Docker Compose

---

## Running the Project (Without Docker)

Open three terminals.

Start Auth Service:
cd auth-service
mvn spring-boot:run

Start Product Service:
cd product-service
mvn spring-boot:run

Start API Gateway:
cd api-gateway1
mvn spring-boot:run

---

## Running the Project (With Docker)

### Prerequisites

Install Docker Desktop

Verify installation:
docker --version
docker compose version

### Step 1 — Build JAR files

cd auth-service
mvn clean package

cd ../product-service
mvn clean package

cd ../api-gateway1
mvn clean package

### Step 2 — Start all services

From project root folder:
docker compose up --build

This command will:
• Build images
• Create containers
• Connect services via network
• Start the full system

### Step 3 — Stop services

docker compose down

---

## Default Ports

API Gateway — 5000
Auth Service — 5001
Product Service — 5002

---

## API Usage

### Register User

POST http://localhost:5000/auth/register

### Login (Get Token)

POST http://localhost:5000/auth/login

Body:
{
"email": "[user@test.com](mailto:user@test.com)",
"password": "password123"
}

### Access Protected API

GET http://localhost:5000/products

Header:
Authorization: Bearer <JWT_TOKEN>

---

## Swagger Documentation

http://localhost:5000/swagger-ui.html
http://localhost:5001/swagger-ui.html
http://localhost:5002/swagger-ui.html

---

## Docker Networking

Inside Docker, services communicate using container names instead of localhost:

auth-service
product-service

Gateway internally routes to:
http://auth-service:5001
http://product-service:5002

---

## Concepts Demonstrated

Microservice communication
API Gateway routing
JWT authentication
Containerized deployment
Service orchestration

---

## Author

Anurag Pal
