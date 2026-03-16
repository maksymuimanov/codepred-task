# Podsumowanie Projektu

Projekt jest prostą aplikacją backendową zbudowaną przy użyciu Spring Boot,
która udostępnia REST API do zarządzania zadaniami TODO.

# Tech Stack

- Java 21
- Spring Boot 4.0.3
- Spring Data JPA
- PostgreSQL
- Flyway
- Maven
- Swagger
- MapStruct
- Lombok
- Testcontainers

# Jak Uruchomić Aplikację

## Wymagania

- Java 21
- Maven 3.8+
- Docker (do uruchomienia PostgreSQL)

## Uruchomienie

```shell
./start.sh
```

Aplikacja uruchamia się na: http://localhost:8080

# API Endpoints

| Metoda   | Endpoint              | Opis                                  |
|----------|-----------------------|---------------------------------------|
| `POST`   | `/api/1.0/tasks`      | Tworzy nowe zadanie                   |
| `GET`    | `/api/1.0/tasks`      | Pobiera wszystkie zadania (paginacja) |
| `GET`    | `/api/1.0/tasks/{id}` | Pobiera zadanie po ID                 |
| `PUT`    | `/api/1.0/tasks/{id}` | Aktualizuje zadanie                   |
| `DELETE` | `/api/1.0/tasks/{id}` | Usuwa zadanie                         |

# Swagger / Dokumentacja API

Swagger UI dostępny pod adresem:
http://localhost:8080/swagger-ui.html
