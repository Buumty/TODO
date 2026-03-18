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
### 1. Clone repository

git clone https://github.com/Buumty/TODO.git
cd TODO

### 2. Build image

docker build -t todo-api .

### 3. Run container

docker run -p 8080:8080 todo-api

App will be available at:

http://localhost:8080
