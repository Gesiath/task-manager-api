# Mini Scrum Management API

RESTful backend API designed to manage users, tasks, and sprints following Scrum principles.  
Implements real business rules for sprint lifecycle and task transitions.

---

## 🚀 Features

- User management
- Task creation and assignment
- Sprint lifecycle management (PLANNED → ACTIVE → CLOSED)
- Only one ACTIVE sprint allowed at a time
- Tasks can only be added to PLANNED sprints
- When closing a sprint:
  - DONE tasks remain in the sprint
  - Unfinished tasks return to backlog
- Dedicated PATCH endpoint for status updates
- Pagination and filtering support
- Global exception handling
- Unit tests for core business logic

---

## 🧠 Business Rules Implemented

- Only one sprint can be ACTIVE simultaneously.
- Tasks cannot be assigned to CLOSED sprints.
- Tasks from CLOSED sprints cannot be modified.
- Sprint cannot be closed unless it is ACTIVE.
- On sprint closure, unfinished tasks automatically return to backlog.

---

## 🛠 Technologies Used

- Java 21
- Spring Boot 4
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- JUnit 5
- Mockito

---

## 📂 Architecture

Layered architecture:

- Controller layer
- Service layer (business logic)
- Repository layer
- DTO mapping
- Global exception handling

---

## 🧪 Testing

Unit tests cover:

- Sprint state transitions
- Active sprint uniqueness
- Sprint closing behavior
- Task backlog reassignment logic

---

## ▶ How to Run

1. Configure MySQL database  
2. Update application.properties  
3. Run:

    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

---

## 🔮 Possible Improvements

- JWT authentication
- Role-based access
- Dockerization
- CI/CD pipeline
- Integration tests
