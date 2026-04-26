# 🔐 Spring Boot JWT Authentication & CRUD API

A production-ready REST API built with **Spring Boot** featuring JWT-based authentication, centralized exception handling, and full CRUD operations.

---

## 📖 What is JSON Web Token (JWT)?

**JSON Web Token (JWT)** is an open standard (RFC 7519) for securely transmitting information between two parties — a client and a server — as a compact, self-contained JSON object. The token is digitally signed, which means the server can verify its authenticity without storing any session data.

A JWT consists of 3 parts separated by dots:

```
header.payload.signature
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyb2hpdCJ9.abc123xyz
```

| Part | Description |
|---|---|
| **Header** | Algorithm used to sign the token (e.g. HS256) |
| **Payload** | User data — username, roles, expiry time |
| **Signature** | Verifies the token hasn't been tampered with |

---

## 💡 Why JWT? — Real World Use Cases

- **Stateless Authentication** — Server doesn't need to store session. Token itself carries all the info.
- **Microservices** — One token works across multiple services without shared session storage.
- **Mobile & SPA Apps** — Token stored on client side, sent with every API call.
- **API Security** — Protect REST endpoints so only authenticated users can access them.
- **SSO (Single Sign-On)** — One login, access to multiple applications.

---

## 🎯 Project Overview

This project demonstrates a secure REST API where:

- Users can **register and login** using their credentials
- On successful login, server generates a **JWT token** and returns it to the client
- Client must send this token in every subsequent request via the `Authorization` header
- A **JWT Filter** intercepts every request, validates the token, and grants or denies access
- All protected **CRUD endpoints** are accessible only with a valid token
- Any error returns a clean, structured JSON response via **Global Exception Handler**

---

## 🔄 Authentication Flow

```
User Login (username + password)
            ↓
Server validates credentials
            ↓
Server generates JWT token
            ↓
Token returned to client
            ↓
Client sends token in every request
Authorization: Bearer <token>
            ↓
JwtAuthFilter validates token
            ↓
Access Granted ✅  or  401 Unauthorized ❌
```

---

## 🛡️ JWT Filter

The **JwtAuthFilter** is a custom filter that runs before every incoming request. It is responsible for:

1. Extracting the JWT token from the `Authorization` header
2. Validating the token — checks signature and expiry
3. Extracting the username from the token payload
4. Loading user details and setting authentication in **Spring Security Context**
5. Allowing the request to proceed if valid, else rejecting with 401

This makes the API completely **stateless** — no sessions, no cookies.

---

## ⚠️ Exception Handling

A **Global Exception Handler** using `@ControllerAdvice` ensures all errors return a consistent, readable JSON response instead of default Spring error pages.

| Scenario | HTTP Status |
|---|---|
| Resource not found (wrong ID) | 404 Not Found |
| Invalid or expired JWT token | 401 Unauthorized |
| Validation failures (bad request body) | 400 Bad Request |
| Any unexpected server error | 500 Internal Server Error |

---

## 📌 API Endpoints

### 🔓 Public (No token needed)

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login and receive JWT token |

### 🔒 Protected (JWT token required)

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/products` | Fetch all products |
| GET | `/api/products/{id}` | Fetch product by ID |
| POST | `/api/products` | Create new product |
| PUT | `/api/products/{id}` | Update existing product |
| DELETE | `/api/products/{id}` | Delete product |

---

## 📁 Project Structure

```
src/main/java/com/project/
├── config/
│   └── SecurityConfig.java             ← Spring Security & filter registration
├── controller/
│   ├── AuthController.java             ← Login & register endpoints
│   └── ProductController.java          ← Protected CRUD endpoints
├── dto/
│   ├── LoginRequest.java               ← Request body for login
│   └── JwtResponse.java                ← Response with JWT token
├── entity/
│   └── Product.java                    ← JPA entity
├── exception/
│   ├── GlobalExceptionHandler.java     ← @ControllerAdvice
│   ├── ResourceNotFoundException.java
│   └── UnauthorizedException.java
├── filter/
│   └── JwtAuthFilter.java              ← Intercepts & validates every request
├── repository/
│   └── ProductRepository.java          ← Spring Data JPA repository
├── service/
│   ├── AuthService.java                ← Login logic
│   ├── JwtService.java                 ← Token generate, validate, extract
│   └── UserDetailsServiceImpl.java     ← Loads user from DB for Spring Security
└── SpringJwtApplication.java
```

---

## ⚙️ Setup & Run

### 1. Clone the repository
```bash
git clone https://github.com/Rohit321-bit/spring-jwt-crud.git
cd spring-jwt-crud
```

### 2. Configure `application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/jwtdb
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
jwt.secret=your_secret_key_here
jwt.expiration=86400000
```

### 3. Run the application
```bash
mvn spring-boot:run
```

### 4. Test via Swagger UI
```
http://localhost:8080/swagger-ui/index.html
```

---

## 🚀 Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 11 |
| Framework | Spring Boot, Spring Security |
| Authentication | JWT (jjwt library) |
| ORM | Spring Data JPA, Hibernate |
| Database | MySQL |
| API Docs | Swagger UI (OpenAPI) |
| Build Tool | Maven |

---

## 👨‍💻 Author

**Rohit Shaw**
- 🔗 [LinkedIn](https://www.linkedin.com/in/rohit-shaw-5199541ab/)
- 💻 [GitHub](https://github.com/Rohit321-bit)
- 📧 shawrohit502@gmail.com
