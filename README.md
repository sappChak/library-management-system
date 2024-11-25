# Bookstore API

A Spring Boot application providing a RESTful API for managing a bookstore. Includes book management, user accounts, authentication with JWT, and transaction handling.

## Features

- **Books**: Borrow, return, and manage books.
- **Users**: Manage user accounts.
- **Authentication**: JWT-based authentication.
- **Transactions**: Track borrowing and returning of books.

## Running the Application

### With Docker

1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/bookstore-api.git
   cd bookstore-api
   ```

2. Start the app:
   ```bash
   docker-compose up --build
   ```

3. Access the Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Without Docker (Gradle-based)

1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/bookstore-api.git
   cd bookstore-api
   ```

2. Create the SQLite database directory:
   ```bash
   mkdir data
   ```

3. Run the app:
   ```bash
   ./gradlew bootRun
   ```

## Testing the Application

Run unit and integration tests:
```bash
./gradlew test
```

## Endpoints Overview

The API provides the following main endpoints, as seen in the Swagger UI:

### Books

- `GET /api/books`: Retrieve all books.
- `POST /api/books`: Add a new book.
- `POST /api/books/return/{bookId}`: Return a borrowed book.
- `POST /api/books/borrow/{bookId}`: Borrow a book.
- `GET /api/books/borrowed`: View borrowed books.
- `GET /api/books/available`: List available books.
- `DELETE /api/books/{bookId}`: Delete a book.

### Users

- `GET /api/users/{userId}`: Retrieve a user by ID.
- `PUT /api/users/{userId}`: Update an existing user.
- `DELETE /api/users/{userId}`: Delete a user.
- `GET /api/users`: Retrieve all users.
- `POST /api/users`: Create a new user.

### Authentication

- `POST /api/auth/register`: Register a new user.
- `POST /api/auth/login`: Log in and receive a JWT.

### Transactions

- `GET /api/transactions`: View all transactions.
- `GET /api/transactions/user/{userId}`: View transactions for a specific user.
- `DELETE /api/transactions/{transactionId}`: Delete a transaction by ID.

## Environment Variables

- `SPRING_DATASOURCE_URL`: SQLite database URL (e.g., `jdbc:sqlite:/app/data/app.db`).
- `SPRING_DATASOURCE_DRIVER_CLASS_NAME`: Database driver class (default: `org.sqlite.JDBC`).
- `SPRING_JPA_HIBERNATE_DDL_AUTO`: Database initialization strategy (e.g., `update`).
- `SECURITY_JWT_SECRET`: Secret key for signing JWT tokens.
- `APP_ADMIN_USERNAME`, `APP_ADMIN_PASSWORD`, `APP_ADMIN_EMAIL`: Default admin user credentials.

## Database

The application uses SQLite as its database. The database file is stored in the `./data` directory.

## Admin User

By default, the application initializes an admin user upon startup. The credentials are configurable via environment variables:

- Username: `APP_ADMIN_USERNAME`
- Password: `APP_ADMIN_PASSWORD`
- Email: `APP_ADMIN_EMAIL`
