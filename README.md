\# Spring Boot Microservices – E-Commerce Demo



\## Overview



This project demonstrates a simple microservices architecture using Spring Boot.



The system consists of three independent services:



\* \*\*API Gateway\*\* – Entry point for all client requests

\* \*\*Auth Service\*\* – Handles authentication and JWT token validation

\* \*\*Product Service\*\* – Provides product related APIs



All requests first pass through the Gateway → validated by Auth → forwarded to Product service.



---



\## Architecture Flow



Client → API Gateway → Auth Service → Product Service



The API Gateway routes requests and verifies JWT authentication before allowing access to protected endpoints.



---



\## Technologies Used



\* Java 17

\* Spring Boot

\* Spring Cloud Gateway

\* JWT Authentication

\* Maven

\* REST APIs



---



\## Running the Project



\### 1. Clone Repository



```

git clone https://github.com/AnuragPal02/springboot-microservices.git

cd springboot-microservices

```



\### 2. Start Services (run in order)



Open 3 terminals:



\#### Start Auth Service



```

cd auth-service

mvn spring-boot:run

```



\#### Start Product Service



```

cd product-service

mvn spring-boot:run

```



\#### Start API Gateway



```

cd api-gateway1

mvn spring-boot:run

```



---



\## Default Ports



| Service         | Port |

| --------------- | ---- |

| Auth Service    | 5001 |

| Product Service | 5002 |

| API Gateway     | 5000 |



---



\## API Usage



\### 1. Login (Generate Token)



POST



```

http://localhost:5001/auth/login

```



Body:



```

{

&nbsp; "username": "user",

&nbsp; "password": "password"

}

```



Returns JWT token.



---



\### 2. Access Protected Product API



GET



```

http://localhost:5000/products

```



Header:



```

Authorization: Bearer <your\_token>

```

---



\## API Documentation (Swagger UI)



Interactive API documentation is available once services are running:



Auth Service Swagger:

http://localhost:5001/swagger-ui.html



Product Service Swagger:

http://localhost:5002/swagger-ui.html



Api-gateway Swagger: 

http://localhost:5000/swagger-ui.html



Using Swagger, you can test APIs directly from the browser without Postman.

JWT token generated from login can be pasted into the Authorize button to access protected endpoints.



---



\## Purpose



This project demonstrates:



\* Microservices communication

\* API Gateway routing

\* JWT based authentication

\* Service separation \& scalability design



---



\## Author



Anurag Pal



