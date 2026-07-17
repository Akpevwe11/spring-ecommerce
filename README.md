# Spring Ecommerce

REST API for ecommerce with JWT auth (Spring Boot 4.1, PostgreSQL, Docker).

## Prerequisites

- Java 17+
- Maven (or use `./mvnw`)
- Docker & Docker Compose

If your user is not in the `docker` group, prefix Docker commands with `sg docker -c '...'`.

## Start the project

### Option A — Docker (app + database)

From the project root:

```bash
sg docker -c "docker compose up --build"
```

| Service  | URL / port        |
|----------|-------------------|
| App      | http://localhost:8080 |
| Postgres | `localhost:5432`  |

Stop everything:

```bash
sg docker -c "docker compose down"
```

### Option B — Local app + Docker Postgres (IDE / `mvnw`)

Use this when developing or debugging in IntelliJ. Do **not** run the Docker `app` service at the same time — both bind port 8080.

1. Start Postgres only:

   ```bash
   sg docker -c "docker compose up -d postgres"
   ```

   If the full stack is already running and port 8080 is in use:

   ```bash
   sg docker -c "docker compose stop app"
   ```

2. Run the Spring Boot app:

   - IntelliJ: run `SpringEcommerceApplication`, or
   - Terminal:

     ```bash
     ./mvnw spring-boot:run
     ```

App: http://localhost:8080

## Database credentials

| Setting  | Value            |
|----------|------------------|
| Database | `ecommerce`      |
| User     | `ecommerce`      |
| Password | `ecommerce_pass` |

- Local / IDE: `jdbc:postgresql://localhost:5432/ecommerce` (see `application.properties`)
- Docker `app` service: overrides URL to `jdbc:postgresql://postgres:5432/ecommerce`

## Auth endpoints

Public routes under `/api/auth/**` (e.g. `POST /api/auth/register`). All other endpoints require a JWT.
