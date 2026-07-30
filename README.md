# Quiz Application

A **RESTful Quiz Management System** developed using **Java 25**, **Spring Boot**, **Spring Data JPA**, and **PostgreSQL**. The application allows users to create quizzes, add multiple-choice questions, submit answers, calculate scores, and track quiz attempts through REST APIs.

---

## 🚀 Features

- Create, retrieve, and delete quizzes
- Add multiple-choice questions with multiple options
- Automatic quiz evaluation and score calculation
- Track quiz attempt history
- Layered architecture following Spring Boot best practices
- Bean Validation for request validation
- Global exception handling
- Hibernate ORM with automatic schema generation
- RESTful APIs tested using Postman

---

## 🛠 Tech Stack

| Technology | Version |
|------------|----------|
| Java | 25 |
| Spring Boot | 4.1.0 |
| Spring Data JPA | Latest |
| Hibernate ORM | Latest |
| PostgreSQL | 18 |
| Maven | 3.9+ |
| Lombok | Latest |
| Bean Validation | Jakarta Validation |
| Postman | API Testing |

---

# 📂 Project Structure

```text
quiz-application
│
├── src
│   └── main
│       ├── java
│       │   └── com.quizapp
│       │       ├── controller
│       │       │     └── QuizController.java
│       │       │
│       │       ├── service
│       │       │     ├── QuizService.java
│       │       │     └── QuizServiceImpl.java
│       │       │
│       │       ├── repository
│       │       │     ├── QuizRepository.java
│       │       │     ├── QuestionRepository.java
│       │       │     ├── OptionRepository.java
│       │       │     └── QuizAttemptRepository.java
│       │       │
│       │       ├── entity
│       │       │     ├── Quiz.java
│       │       │     ├── Question.java
│       │       │     ├── Option.java
│       │       │     └── QuizAttempt.java
│       │       │
│       │       ├── dto
│       │       │     ├── QuizRequest.java
│       │       │     ├── QuizResponse.java
│       │       │     ├── QuestionRequest.java
│       │       │     ├── QuestionResponse.java
│       │       │     ├── OptionRequest.java
│       │       │     ├── OptionResponse.java
│       │       │     ├── SubmitAnswersRequest.java
│       │       │     └── QuizResultResponse.java
│       │       │
│       │       ├── exception
│       │       │     ├── GlobalExceptionHandler.java
│       │       │     └── ResourceNotFoundException.java
│       │       │
│       │       └── QuizApplication.java
│       │
│       └── resources
│             └── application.properties
│
├── pom.xml
├── README.md
└── .gitignore
```

---

# 🏗 Architecture

```text
                Client
        (Browser / Postman)
                  │
                  ▼
          REST Controller
                  │
                  ▼
           Service Layer
          (Business Logic)
                  │
                  ▼
         Repository Layer
        (Spring Data JPA)
                  │
                  ▼
           Hibernate ORM
                  │
                  ▼
            PostgreSQL DB
```

---

# 🗄 Database Design

```text
Quiz
------------------------
id
title
description
createdAt
------------------------
        │
        │ One-to-Many
        ▼

Question
------------------------
id
text
quiz_id (FK)
------------------------
        │
        │ One-to-Many
        ▼

Option
------------------------
id
text
correct
question_id (FK)
------------------------

QuizAttempt
------------------------
id
score
totalQuestions
submittedAt
quiz_id (FK)
------------------------
```

---

# 🔗 API Endpoints

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/quizzes` | Create a new quiz |
| GET | `/api/quizzes` | Retrieve all quizzes |
| GET | `/api/quizzes/{id}` | Retrieve quiz by ID |
| POST | `/api/quizzes/{id}/questions` | Add question to quiz |
| POST | `/api/quizzes/{id}/submit` | Submit quiz answers |
| GET | `/api/quizzes/{id}/attempts` | View quiz attempts |
| DELETE | `/api/quizzes/{id}` | Delete quiz |

---

# ⚙️ Setup & Installation

## 1. Clone Repository

```bash
git clone https://github.com/<your-username>/quiz-application.git
```

## 2. Create Database

```sql
CREATE DATABASE quizdb;
```

## 3. Configure Database

Update `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/quizdb
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
```

## 4. Run Application

```bash
mvn spring-boot:run
```

Application runs at

```
http://localhost:8080
```

---

# 🧪 API Testing

Example: Create Quiz

**POST**

```
/api/quizzes
```

```json
{
    "title":"Java Basics",
    "description":"Core Java Fundamentals"
}
```

Example: Get All Quizzes

```
GET /api/quizzes
```

---

# 📸 Sample Response

```json
{
    "id": 1,
    "title": "Java Basics",
    "description": "Core Java Fundamentals",
    "createdAt": "2026-07-30T21:37:07",
    "questions": []
}
```

---

# 🔮 Future Enhancements

- JWT Authentication & Authorization
- Swagger / OpenAPI Documentation
- Docker Support
- Unit Testing (JUnit & Mockito)
- CI/CD using GitHub Actions
- Pagination & Sorting
- Search & Filtering
- Role-Based Access Control

---

# 👨‍💻 Author

**Deep Chakraborty**

B.Tech in Information Technology

Java Backend Developer

