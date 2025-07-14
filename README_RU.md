[Read in English](README.md)

# Spring Reactive To-Do

Этот проект — реактивный REST API для управления задачами (To-Do) с использованием современных технологий.

## Используемые технологии

- Java 17
- Spring Boot с WebFlux — реактивный веб-фреймворк
- Project Reactor (Mono, Flux) — реактивное программирование
- Spring Security с реактивной аутентификацией и авторизацией
- MongoDB с ReactiveMongoRepository — реактивное взаимодействие с базой данных
- MapStruct — для маппинга между DTO и сущностями
- Lombok — для сокращения шаблонного кода
- JSR-380 (Jakarta Validation) — валидация входящих данных
- Gradle — инструмент сборки
- **Docker и Docker Compose** — для контейнеризации и запуска приложения с базой данных

## Обзор проекта

- Управление пользователями и задачами с ролевой безопасностью
- Асинхронная обработка запросов с помощью Mono и Flux
- Валидация DTO с помощью аннотаций `@Valid`
- Безопасность реализована через Spring Security и реактивный `UserDetailsService`
- Использование MongoDB для реактивного хранения данных
- Чистая архитектура с разделением контроллеров, сервисов, репозиториев и мапперов

## Примеры API

### UserController

| Метод | Эндпоинт                           | Описание                         | Разрешённые роли      |
|-------|-----------------------------------|---------------------------------|----------------------|
| GET   | `/api/v1/users`                   | Получить всех пользователей      | USER, MANAGER         |
| GET   | `/api/v1/users/{id}`              | Получить пользователя по ID      | USER, MANAGER         |
| GET   | `/api/v1/users/profile`           | Получить текущего аутентифицированного пользователя | USER, ADMIN, MANAGER  |
| POST  | `/api/v1/users/account?roleType=`| Создать пользователя с указанной ролью | Доступно всем (без авторизации) |
| PUT   | `/api/v1/users/{id}`              | Обновить пользователя по ID      | USER, MANAGER         |
| DELETE| `/api/v1/users/{id}`              | Удалить пользователя по ID       | USER, MANAGER         |

### TaskController

| Метод | Эндпоинт                                  | Описание                       | Разрешённые роли      |
|-------|-------------------------------------------|-------------------------------|----------------------|
| GET   | `/api/v1/tasks`                          | Получить все задачи             | USER, MANAGER         |
| GET   | `/api/v1/tasks/{id}`                     | Получить задачу по ID           | USER, MANAGER         |
| POST  | `/api/v1/tasks`                          | Создать новую задачу            | MANAGER               |
| PUT   | `/api/v1/tasks/{id}`                     | Обновить задачу по ID           | MANAGER               |
| PUT   | `/api/v1/tasks/observer/{id}?observer=` | Добавить наблюдателя к задаче   | USER, MANAGER         |
| DELETE| `/api/v1/tasks/{id}`                     | Удалить задачу                  | MANAGER               |

## Как использовать API через Postman

### Аутентификация

- Используйте **Basic Auth** с именем пользователя и паролем для защищённых эндпоинтов.
- Для тестирования создавайте пользователей через POST `/api/v1/users/account`.

---

### Пример запросов

#### 1. Создание пользователя (POST `/api/v1/users/account?roleType=ROLE_USER`)

- **Тело запроса (raw JSON):**

```json
{
  "username": "user",
  "password": "12345",
  "name": "user",
  "email": "user@example.com"
}
```
- **Параметр запроса:**
- roleType = ROLE_USER (или ROLE_ADMIN, ROLE_MANAGER)

#### 2. Создание задачи (POST /api/v1/tasks)  

- **Тело запроса (raw JSON):**

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

#### 3. Добавление наблюдателя к задаче (PUT /api/v1/tasks/observer/{taskId}?observer={userId})

---

## Запуск проекта

Убедитесь, что у вас установлены Java 17 и Gradle, Docker и Docker Compose.

Запустите локальный экземпляр MongoDB или настройте подключение к удалённой базе.

### Запуск базы данных

Запустите MongoDB с помощью Docker Compose:

```bash
docker-compose up -d mongo
```

Клонируйте репозиторий:

```bash
git clone https://github.com/your-username/spring-reactive-todo.git
```
Запустите приложение с помощью Gradle:
- ./gradlew bootRun
