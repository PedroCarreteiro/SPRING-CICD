# Todo List API - DevOps CI/CD Pipeline

<!--![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/PedroCarreteiro/SPRING-CICD/pipeline.yml?style=for-the-badge)-->
![Docker Pulls](https://img.shields.io/docker/pulls/padraoo/todo-spring-app?style=for-the-badge)
![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-green?style=for-the-badge&logo=springboot)

Simple API REST with **Spring Boot** and **Docker**

**CI/CD** pipeline by **GitHub Actions**

## DevOps Cycle

```mermaid
graph LR
    A[Developer] -->|Push Code| B(GitHub Repo)
    B -->|Trigger| C{GitHub Actions}
    C -->|Test & Build| D[JUnit 5 + Maven]
    D -- Ok? --> E[Build Docker Image]
    E -->|Push| F[Docker Hub Registry]
    F -->|Pull| G[Server / Localhost]

```
## Run project 
### With Docker
```bash
docker pull padraoo/todo-spring-app:latest
```
```bash
docker run -p 8080:8080 padraoo/todo-spring-app:latest
```

### Locally for dev
```bash
git clone [https://github.com/PedroCarreteiro/SPRING-CICD.git](https://github.com/PedroCarreteiro/SPRING-CICD.git)
cd SPRING-CD
./mvnw spring-boot:run
```

## Endpoints
- GET: /tasks
- POST: /tasks
- DELETE: /tasks/{id}

## General payload
```json
{
  "description": "Description",
  "completed": false
}
```
