# Project Description

This project is a Book Store application that includes user authentication and registration features. Below are the key components and functionalities added to the project:

## Features

### 1. User Authentication and Registration
- **AuthController**: Handles user login and registration endpoints.
  - `loginUser`: Authenticates users and generates JWT tokens.
  - `registerUser`: Handles user registration with validation.

### 2. Custom User Details Service
- **CustomUserDetailService**: Retrieves user details for authentication.
- **UserService**: Manages user creation, update, and retrieval.
  - Methods for user registration, updating user details, and fetching user by ID or username.
  - Logging for user operations.

### 3. Repositories
- **UserRepository**: Operations for user entity.
  - Methods to find user by username, email, and ID.
- **RefreshTokenRepository**: Operations for refresh token entity.
  - Methods to find and delete refresh token by user.

### 4. Configuration
- **BeansConfig**: Configures beans for authentication, password encoding, and CORS.
- **OpenApiConfig**: Configures OpenAPI documentation with security schemes and server information.

### 5. JWT Security
- **JwtAuthenticationEntryPoint**: Handles unauthorized access.
- **JwtAuthenticationFilter**: Validates JWT tokens in requests.
- **JwtTokenProvider**: Generates and validates JWT tokens.
- **SecurityConfig**: Configures security settings and applies JWT filters.

### 6. Custom Exceptions
- **TokenRefreshException**: Handles token refresh errors.
- **UserExistsException**: Handles cases where a user already exists.

### 7. Validation
- **EmailValidation**: Validates email format.
- **PasswordMatchesValidator**: Ensures password and confirm password match.
- **ResponseErrorValidation**: Handles validation errors and returns appropriate responses.

### 8. Custom Validation Annotations
- **@PasswordMatches**: Validates that password and confirm password fields match.
- **@ValidEmail**: Validates email format.

### 9. Data Transfer Objects (DTOs)
- **UserDTO**: For user data transfer.
- **LoginRequest**: For login request payload.
- **SignupRequest**: For user registration payload.
- **InvalidLoginResponse**: For invalid login attempts.
- **JwtTokenSuccessResponse**: For successful JWT token generation.
- **MessageResponse**: For generic message responses.
