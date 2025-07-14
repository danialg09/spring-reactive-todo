[Read in Russian](README_RU.md)

# Spring Reactive To-Do

This project is a reactive REST API for managing tasks (To-Do) using modern technologies.

## Technologies Used

- Java 17
- Spring Boot with WebFlux — reactive web framework
- Project Reactor (Mono, Flux) — reactive programming
- Spring Security with reactive authentication and authorization
- MongoDB with ReactiveMongoRepository — reactive database interaction
- MapStruct — for mapping between DTOs and entities
- Lombok — to reduce boilerplate code
- JSR-380 (Jakarta Validation) — input validation
- Gradle — build tool
- **Docker and Docker Compose** — for containerization and running the application along with the database

## Project Overview

- User and task management with role-based security
- Asynchronous request processing using Mono and Flux
- DTO validation with `@Valid` annotations
- Security implemented via Spring Security and a reactive `UserDetailsService`
- MongoDB used for reactive data persistence
- Clean architecture with separation of controllers, services, repositories, and mappers

## API Examples

### UserController

| Method | Endpoint                             | Description                       | Roles Allowed          |
|--------|------------------------------------|---------------------------------|-----------------------|
| GET    | `/api/v1/users`                    | Retrieve all users               | USER, MANAGER          |
| GET    | `/api/v1/users/{id}`               | Retrieve user by ID              | USER, MANAGER          |
| GET    | `/api/v1/users/profile`            | Get current authenticated user  | USER, ADMIN, MANAGER   |
| POST   | `/api/v1/users/account?roleType=` | Create user with specified role | Public (no auth)       |
| PUT    | `/api/v1/users/{id}`               | Update user by ID                | USER, MANAGER          |
| DELETE | `/api/v1/users/{id}`               | Delete user by ID                | USER, MANAGER          |

### TaskController

| Method | Endpoint                                    | Description                       | Roles Allowed          |
|--------|---------------------------------------------|---------------------------------|-----------------------|
| GET    | `/api/v1/tasks`                            | Retrieve all tasks               | USER, MANAGER          |
| GET    | `/api/v1/tasks/{id}`                       | Retrieve task by ID              | USER, MANAGER          |
| POST   | `/api/v1/tasks`                            | Create new task                 | MANAGER                |
| PUT    | `/api/v1/tasks/{id}`                       | Update task by ID               | MANAGER                |
| PUT    | `/api/v1/tasks/observer/{id}?observer=`   | Add observer to task             | USER, MANAGER          |
| DELETE | `/api/v1/tasks/{id}`                       | Delete task                    | MANAGER                |

## How to Use the API with Postman

### Authentication

- Use **Basic Auth** with username and password for protected endpoints.
- For testing, create users via the POST `/api/v1/users/account` endpoint.

---

### Example Requests

#### 1. Create User (POST `/api/v1/users/account?roleType=ROLE_USER`)

- **Body (raw JSON):**

```json
{
  "username": "user",
  "password": "12345",
  "name": "user",
  "email": "user@example.com"
}
```
- **Query Parameter:**
- roleType = ROLE_USER (or ROLE_ADMIN, ROLE_MANAGER)

#### 2. Create Task (POST /api/v1/tasks)  

- **Body (raw JSON):**

```json
{
  "name": "Write project documentation",
  "description": "Complete the README and API docs for the project",
  "status": "OPEN",
  "authorId": "userId123",
  "assigneeId": "userId456",
  "observerIds": ["userId789", "userId101"]
}
```

#### 3. Add Observer to Task (PUT /api/v1/tasks/observer/{taskId}?observer={userId})  

---

## Running the Project

Make sure you have Java 17, Gradle, Docker, and Docker Compose installed.

Run a local MongoDB instance or configure connection to a remote database.

### Starting the Database

Start MongoDB using Docker Compose:

```bash
docker-compose up -d mongo
```

Clone the repository:

```bash
git clone https://github.com/your-username/spring-reactive-todo.git
```
Start the application using Gradle:
 - ./gradlew bootRun
