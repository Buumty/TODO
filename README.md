The application allows full CRUD operations on TODO tasks.

## Features

- create task
- get all tasks
- get task by id
- update task
- delete task
- task status support: NEW, IN_PROGRESS, DONE

## Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- H2 Database
- Maven
- Docker

## API

Base path:
/api/tasks


Endpoints:

- `POST /api/tasks`
- `GET /api/tasks`
- `GET /api/tasks/{id}`
- `PUT /api/tasks/{id}`
- `DELETE /api/tasks/{id}`

## Run with Docker

### Build image

docker build -t todo-api .

App will be available at:

http://localhost:8080
