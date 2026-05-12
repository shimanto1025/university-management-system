# University Student Management System

## Microservices-based cloud application for managing students, courses, and enrollments.

## Architecture
Client → API Gateway (:8080)
├── Student Service (:8081) → H2 DB
├── Course Service (:8082) → H2 DB
├── Enrollment Service (:8083) → H2 DB
└── Notification Service (:8084) → In-Memory


## Tech Stack

| Component | Technology |
|-----------|------------|
| Language | Java 21 |
| Framework | Spring Boot 3.5.14 |
| API Gateway | Spring Cloud Gateway |
| Build Tool | Gradle |
| Database | H2 (In-Memory) |
| Testing | JUnit 5, Mockito |
| Containerization | Docker, Docker Compose |

---

## Quick Start (Docker)

### Prerequisites
- Docker
- Java 21 (for local build)

### Run All Services

```bash
docker compose up --build
```

# Test 
## ./test-script.sh

# Stop
## docker compose down

## github: https://github.com/shimanto1025/university-management-system