# Finance API 🚀

A robust, secure, and production-ready banking backend system built with **Java 17** and **Spring Boot**. This project simulates a secure banking environment, implementing advanced architectural patterns, modern security standards, and clean code practices.

---

> ⚠️ **NOTE FOR RECRUITERS & REVIEWERS:**
> The `main` branch contains a stable MVP version of the application. 
> I am currently conducting a massive architectural refactoring (implementing CQS, MapStruct, and a modular Feature-based structure).
> 
> You can check out my latest, most advanced code in this active Draft Pull Request:
> 👉 https://github.com/alusnia/finance-api/pull/1

---

## 🛠️ Tech Stack & Ecosystem

* **Backend Framework:** Spring Boot 3.x (Spring Web, Spring Data JPA, Spring Security)
* **Language:** Java 17 (utilizing modern features like Records, Patterns, and Streams)
* **Security & Auth:** Spring Security, JSON Web Tokens (JWT) with secure claim parsing
* **Database & Migration:** Hibernate/JPA, PostgreSQL / MySQL compatibility
* **Mapping:** MapStruct (for high-performance, type-safe compile-time object mapping)
* **Containerization:** Docker & Docker Compose (for seamless database and service orchestration)
* **Testing & Tooling:** Spring Boot Test, HTTP Client (`test.http` integration), Swagger

---

## 🏗️ Architectural Overview & Design Patterns

This project has been heavily refactored from a traditional, junior-level monolithic layout into an **Enterprise-grade Modular Structure**.

### 1. Command Query Separation (CQS)
To optimize data flows and maintain clean business logic, the application separates read operations from write operations:
* **Commands:** State-changing operations (e.g., `SaveCheckPeselCommand`). They execute business logic, trigger audits, and return `void` or specific event responses to prevent side effects.
* **Queries:** Read-only operations (e.g., `CheckPeselQuery`). They fetch data fast, bypassed from complex business transitions, optimizing read performance.

### 2. Package-by-Feature (Domain-Driven Organization)
Instead of grouping classes by technical layers (`controllers`, `services`, `repositories`), the system is organized into **autonomous domain modules** (e.g., `registration`, `account`, `user`, `core`).
* **High Encapsulation:** Classes like Mappers, Domain Services, and Repositories use package-private visibility where applicable. They are hidden from other domains, preventing "spaghetti code" dependencies.
* **Microservice Readiness:** Each feature packet contains everything it needs to function. If needed, a domain (like `registration`) can be extracted into an independent microservice with minimal friction.

### 3. Bulletproof Audit Logging & Security Intuition
The registration flow enforces tight security rules. For instance, when an employee searches for a citizen's PESEL:
* The system logs the audit trail **before** checking the database for the PESEL's existence.
* The audit trail logging runs outside the main business validation transaction state. Even if the database connection drops or a validation fails subsequently, the audit log **is never rolled back**, ensuring 100% accountability for sensitive data access.

### 4. Standardized API Contracts & Resilient Error Handling
* **Global Exception Handling:** Handled via custom business exceptions mapped cleanly through a standardized `BankingError` enum, preventing internal stack traces from leaking to the client.
* **Unified Responses:** Frontend communication is strictly wrapped inside a customizable `SuccessResponse` backed by a `SuccessDetails` enum, ensuring absolute consistency for client consumption.

---

## 📂 Project Structure (Feature-Based)

```text
com.financeapi.finance_api
├── account/               # Account & Cards Domain Module
│   ├── controller/        # REST Endpoints & DTOs
│   ├── entity/            # JPA Entities (Account, Card, CardAccount)
│   ├── mapper/            # MapStruct Mappers
│   ├── repository/        # Spring Data Repositories & Specifications
│   └── service/           # Domain Logic
├── core/                  # Shared Infrastructure Layer
│   ├── client/            # External integrations
│   ├── config/            # Core Spring Framework Configurations
│   ├── exception/         # Global Exception Handlers & BankingError Enums
│   └── security/          # Spring Security, JWT Filtering, Token Management
├── registration/          # Customer Registration Domain Module (CQS pattern)
│   ├── controller/        # Command/Query HTTP Endpoints
│   ├── entity/            # Audit tracking entities (e.g., PeselSearch)
│   ├── mapper/            # Domain compilation mappers
│   ├── repository/        # Audit and Registration Data stores
│   └── service/           # Command and Query handlers (e.g., SaveCheckPeselCommand)
├── transaction/           # Financial Transactions Module
└── user/                  # User Management & Profile Module
```

---

## 🚀 Getting Started & Local Setup

### Prerequisite Environment Variables
For security compliance, sensitive credentials are **never hardcoded** in `application.properties`. They must be injected via your IDE or local Environment Variables.

1. Clone the repository:
   ```bash
   git clone [https://github.com/alusnia/finance-api.git](https://github.com/alusnia/finance-api.git)
   cd finance-api
   ```

2. Open the project in **IntelliJ IDEA**.

3. Configure your **Run/Debug Configuration** in IntelliJ:
   * Click **Edit Configurations...** (next to the green Play button).
   * Click **Modify options** (or the settings gear icon in newer versions).
   * Under **Operating System**, select **Environment variables**.
   * Add your secure local keys:
     ```env
     DB_USER=bank_admin
     DB_PASSWORD=haslo
     JWT_SECRET_KEY=F4KN7vZSrv+Cj8mGojcG2KLJ8lTWZEUJzivlMfNWYZw=
     ```

4. Spin up the database dependency using Docker Compose:
   ```bash
   docker-compose up -d
   ```

5. Run the application via IntelliJ (`FinanceApiApplication`) or use Maven:
   ```bash
   ./mvnw spring-boot:run
   ```

---

## 📡 API Testing & Documentation

The application provides two convenient ways to test and explore the endpoints:

### 1. Swagger UI (Interactive API Docs)
The API is fully documented using OpenAPI 3. Once the application is running, you can access the interactive Swagger UI via your browser:
👉 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

> **🔒 Security Note:** The Swagger interface is configured with JWT Bearer Authentication. To test secured endpoints, generate a token via the authentication endpoint, click the **Authorize** button at the top of the Swagger page, and paste your JWT.

### 2. IntelliJ HTTP Client
For quick, code-level endpoint execution, you can use the provided HTTP request file:
* 📄 `test.http` (Located in the root directory. Compatible with the built-in IntelliJ HTTP Client plugin to execute requests instantly with authorization headers).

*Developed with a focus on Enterprise Architecture, Data Security, and Domain Integrity.*
