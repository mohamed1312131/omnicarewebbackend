# OmniCare Backend API

Spring Boot REST API for the OmniCare telemedicine platform.

## Tech Stack

- **Java 17**
- **Spring Boot 3.2.3**
- **Spring Security** with JWT authentication
- **Spring Data JPA**
- **H2 Database** (development)
- **MySQL** (production ready)
- **Maven**

## Prerequisites

- Java 17 or higher
- Maven 3.6+

## Getting Started

### 1. Build the project

```bash
mvn clean install
```

### 2. Run the application

```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

### 3. Access H2 Console (Development)

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:omnicaredb`
- Username: `sa`
- Password: (leave empty)

## API Endpoints

### Authentication

- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user

### Professionals

- `GET /api/professionals` - Get all verified professionals
- `GET /api/professionals/{id}` - Get professional by ID
- `GET /api/professionals/type/{type}` - Get professionals by type
- `POST /api/professionals` - Create professional profile
- `PUT /api/professionals/{id}` - Update professional profile
- `DELETE /api/professionals/{id}` - Delete professional

### User Roles

- `PATIENT` - Regular users seeking healthcare
- `PROFESSIONAL` - Healthcare providers
- `ADMIN` - System administrators

### Professional Types

- `MEDECIN` - Doctor
- `INFIRMIER` - Nurse
- `PSYCHOLOGUE` - Psychologist
- `KINESITHERAPEUTE` - Physiotherapist
- `AMBULANCE` - Ambulance service

## Configuration

Edit `src/main/resources/application.properties` to configure:

- Database connection
- JWT secret and expiration
- CORS allowed origins
- Server port

## Production Setup

For production, update `application.properties`:

```properties
# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/omnicare
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

# Disable H2 Console
spring.h2.console.enabled=false

# Update JWT secret
jwt.secret=your-very-long-secure-random-secret-key
```

## Security

- JWT-based authentication
- Password encryption with BCrypt
- Role-based access control
- CORS configuration for frontend integration

## Development

The application uses:
- **Lombok** for reducing boilerplate code
- **Spring Boot DevTools** for hot reload
- **H2 in-memory database** for quick development
