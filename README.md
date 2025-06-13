# Course Management App

A basic Spring Boot CRUD application for managing courses, using H2 database and Thymeleaf frontend.

## Features

- List all courses
- View course details
- Add new course
- Edit existing course
- Delete course
- Filter courses by instructor

## Tech Stack

- Spring Boot
- Spring Data JPA
- H2 Database
- Thymeleaf

## How to Run

1. Open a terminal in the project root.
2. Run the following command:

   ```
   ./mvnw spring-boot:run
   ```

   or on Windows:

   ```
   mvnw.cmd spring-boot:run
   ```

3. Visit [http://localhost:9090/courses](http://localhost:9090/courses) in your browser.

## H2 Console

- Access the H2 database console at [http://localhost:9090/h2-console](http://localhost:9090/h2-console)
- JDBC URL: `jdbc:h2:mem:testdb`
- User: `sa`
- Password: _(leave blank)_
