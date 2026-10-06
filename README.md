# Charity Management System

A Spring Boot web application for managing charity projects, donations, volunteers, and users. The user-facing pages use Thymeleaf templates, and application data is stored in MySQL.

## Requirements

- Java 17 or later
- MySQL Server

The project includes Maven Wrapper scripts, so a separate Maven installation is not required.

## Database Setup

The default configuration connects to MySQL at `localhost:3307` with these settings:

| Setting | Default |
| --- | --- |
| Database | `charity` |
| Username | `root` |
| Password | `root` |
| Port | `3307` |

Start MySQL before launching the application. The connection settings are in `src/main/resources/application.properties`. The JDBC URL uses `createDatabaseIfNotExist=true`, and Hibernate is configured with `ddl-auto=update` to update the schema from the application's entities.

If your MySQL server uses a different port or credentials, update `spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password` in `application.properties`.

## Run the Application

From the project root, run the Maven Wrapper.

**Windows PowerShell:**

```powershell
.\mvnw.cmd spring-boot:run
```

**macOS/Linux:**

```bash
./mvnw spring-boot:run
```

Once startup completes, open [http://localhost:8080/home](http://localhost:8080/home).

To run the tests:

```powershell
.\mvnw.cmd test
```

On macOS/Linux, use `./mvnw test` instead.

## Main Pages

- `/home` - Home page
- `/activity1` and `/activity2` - Activity pages
- `/contact` - Contact page
- `/volunteer` - Volunteer page
- `/donations/form` - Donation form
- `/donations/history` - Donation history
- `/admin-login` - Administrator sign-in

Administrator pages are available after signing in.

## Development Login

The default Spring Security credentials are `admin` / `admin`. These are development defaults defined in `application.properties`; change them before deploying or exposing the application beyond a trusted local environment.