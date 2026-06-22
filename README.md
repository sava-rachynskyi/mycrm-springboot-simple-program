# Simple CRM & Contract Management System

A lightweight, efficient CRM application developed as a university project to demonstrate clean backend architecture and seamless integration with a single-page frontend.

---

## Tech Stack & Architecture

The application is built using a classic **Three-Tier (Layered) Architecture** (Controller -> Service -> Repository) ensuring clear separation of concerns, high maintainability, and loose coupling.

*   **Backend Core:** Java 21 / Spring Boot 3.x (Spring Data JPA, Hibernate)
*   **Database:** H2 Database Engine (File-based local persistent storage)
*   **Frontend:** Asynchronous Vanilla JavaScript (ES6+), HTML5, Bootstrap 5 UI Framework
*   **Testing Suite:** JUnit 5, Mockito Framework

### Application Architecture Flow
```mermaid
graph TD
    UI[Vanilla JS / Bootstrap UI] -->|Asynchronous Fetch API| Controller[Spring Boot REST Controllers]
    Controller -->|DTOs / Domain Models| Service[Service Layer - Business Logic & RegEx Validation]
    Service -->|Spring Data JPA| Repository[Repository Layer - Database Interaction]
    Repository -->|SQL / Persistent State| DB[(H2 File Database)]
