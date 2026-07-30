# QuizApplication (Microservices-style, Java 25)

A layered Spring Boot quiz platform — matches the resume bullet:
> *Developed a quiz platform using Spring Boot with layered architecture (Controller, Service, DAO).*

## Stack
- **Java 25**
- **Spring Boot 4.1.0** (Spring Framework 7 — first-class Java 25 support)
- **PostgreSQL** for persistence
- **Spring Data JPA** for the DAO layer
- **Lombok** to cut boilerplate
- **Bean Validation** on request DTOs

## Architecture
```
controller/   -> REST endpoints (QuizController)
service/      -> business logic + grading (QuizService / QuizServiceImpl)
dao/          -> Spring Data JPA repositories
entity/       -> JPA entities (Quiz, Question, Option, QuizAttempt)
dto/          -> request/response payloads (keeps correct answers out of API responses)
exception/    -> centralized error handling
```

## Prerequisites
- JDK 25 installed (`java -version`)
- Maven 3.9+
- A running PostgreSQL instance

Create the database:
```sql
CREATE DATABASE quizdb;
```

Update credentials in `src/main/resources/application.properties` if needed:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/quizdb
spring.datasource.username=postgres
spring.datasource.password=postgres
```

## Run
```bash
mvn spring-boot:run
```
The app starts on `http://localhost:8080`. Tables are auto-created via `spring.jpa.hibernate.ddl-auto=update`.

## API Reference

### Create a quiz
```bash
curl -X POST http://localhost:8080/api/quizzes \
  -H "Content-Type: application/json" \
  -d '{"title": "Java Basics", "description": "Core Java fundamentals"}'
```

### Add a question (mark exactly one option as correct)
```bash
curl -X POST http://localhost:8080/api/quizzes/1/questions \
  -H "Content-Type: application/json" \
  -d '{
    "text": "Which keyword is used to inherit a class in Java?",
    "options": [
      {"text": "extends", "correct": true},
      {"text": "implements", "correct": false},
      {"text": "inherits", "correct": false}
    ]
  }'
```

### List all quizzes
```bash
curl http://localhost:8080/api/quizzes
```

### Get a quiz to take (correct answers are hidden)
```bash
curl http://localhost:8080/api/quizzes/1
```

### Submit answers (questionId -> chosen optionId)
```bash
curl -X POST http://localhost:8080/api/quizzes/1/submit \
  -H "Content-Type: application/json" \
  -d '{"answers": {"1": 2}}'
```
Response:
```json
{
  "attemptId": 1,
  "quizId": 1,
  "score": 1,
  "totalQuestions": 1,
  "percentage": 100.0
}
```

### View past attempts for a quiz
```bash
curl http://localhost:8080/api/quizzes/1/attempts
```

### Delete a quiz
```bash
curl -X DELETE http://localhost:8080/api/quizzes/1
```

## Notes
- Exactly one option per question must be marked `correct: true`, or the API returns a 400.
- Answer keys are never returned by `GET /api/quizzes/{id}` — `OptionResponse` omits the `correct` flag by design.
- `QuizAttempt` records let you track score history per quiz over time.
