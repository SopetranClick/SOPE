# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**SopetranClick** is a Spring Boot digital platform that centralizes commerce, tourism, and public management for the municipality of Sopetrán, Antioquia. The application uses a layered monolithic architecture with separate REST and Thymeleaf controllers, organizing functionality around five strategic business axes: Accommodation, Ecotourism, Commerce, Transport, and Public Entities.

**Tech Stack:**
- Backend: Java 21, Spring Boot 4.1.0, Spring Data JPA
- Database: PostgreSQL (configured via environment variables)
- Frontend: HTML5, CSS3, JavaScript, Thymeleaf
- Build Tool: Maven
- Dependencies: Lombok, Hibernate, Jakarta Persistence

## Build and Development Commands

```bash
# Build the project
mvn clean install

# Run the application (requires DB_URL, DB_USERNAME, DB_PASSWORD, JPA_DDL_AUTO, DB_SCHEMA env vars)
mvn spring-boot:run

# Run with specific environment variables (example):
DB_URL=jdbc:postgresql://localhost:5432/sopetran DB_USERNAME=user DB_PASSWORD=pwd JPA_DDL_AUTO=update DB_SCHEMA=public mvn spring-boot:run

# Run tests
mvn test

# Run a single test class
mvn test -Dtest=ClassName

# Run a specific test method
mvn test -Dtest=ClassName#methodName

# Format/validate code (if needed)
mvn clean verify
```

## Architecture and Code Organization

### Layered Architecture Pattern

The project follows a clean, layered architecture with clear separation of concerns:

```
src/main/java/com/sope/sopetran_click/
├── model/               → JPA entities with @Entity annotations
├── repository/          → JpaRepository interfaces for data access
├── service/             → Business logic with ServiceImpl implementations
├── controller/          → REST API endpoints (@RestController)
├── controllerWeb/       → Thymeleaf view controllers (@Controller)
├── dto/                 → Request/Response DTOs for API contracts
├── exception/           → Global exception handling (@ControllerAdvice)
└── config/              → Spring configuration classes

src/main/resources/
├── application.properties  → Spring & database configuration
├── static/              → CSS, images, JavaScript
└── templates/           → Thymeleaf HTML templates
```

### Domain Organization

Code is organized by business domain (mirroring the five strategic axes):

- **accommodation**: Hotels, Estates, Rooms with image galleries
- **ecotourism**: Sites, Iconic Places with image support
- **trade**: Restaurants, Local businesses, Products, Dishes
- **transport**: Buses, Motorbikes
- **public_Entities**: Events, News, Mayoralty
- **user**: Users, Bookings, Payments, Reviews

Each domain has its own `model`, `repository`, `service`, and `controller` subpackages.

### Key Service Patterns

**Service/ServiceImpl Pattern:**
- Services are interfaces defined in the service package (e.g., `HotelsService`)
- Implementations use `@Service` and implement the interface (e.g., `HotelsServiceImpl`)
- Use `@Transactional` on methods that modify data, `@Transactional(readOnly = true)` for queries
- Services inject repositories and handle mapping DTOs ↔ Entities

**DTO Pattern:**
- Separate `RequestDTO` for input validation (@Valid in controllers)
- Separate `ResponseDTO` for API output, preventing unintended field exposure
- Services handle Entity-to-DTO mapping in private converter methods
- Example path: `dto/accommodation/HotelRequestDTO.java`, `dto/accommodation/HotelResponseDTO.java`

**Repository Pattern:**
- Repositories extend `JpaRepository<Entity, ID>` for CRUD + query operations
- Located in `repository/` package (e.g., `HotelsRepository.java`)
- Spring automatically implements common CRUD methods

**Image Management:**
- Models like `Hotels`, `Estate`, `Site`, `Restaurant`, `Local` have `@OneToMany` relationships with image entities (e.g., `HotelImage`)
- Images are ordered via an `orden` field (0 = cover, >0 = gallery)
- Use `cascade = CascadeType.ALL` and `orphanRemoval = true` to manage lifecycle with parent entity
- Services extract cover URL (orden=0) and gallery URLs (orden>0) when mapping to DTOs

### Controllers: REST vs. Web Views

**REST Controllers** (`controller/` package):
- Use `@RestController` and return `ResponseEntity<DTO>`
- Endpoint pattern: `/api/{domain}` (e.g., `/api/hotels`, `/api/restaurants`)
- Implement full CRUD via `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`
- Accept/return JSON via `@RequestBody` / `@ResponseBody` (implicit in @RestController)

**View Controllers** (`controllerWeb/` package):
- Use `@Controller` and return template names (String)
- `MainController` handles the homepage (`/`) and domain landing pages (`/alojamiento`, `/comercio`, etc.)
- Pass data to views via `model.addAttribute(key, value)`
- Render Thymeleaf templates from `src/main/resources/templates/`

### Configuration

- Database connectivity is **environment variable–driven** (see `application.properties`):
  - `DB_URL`: PostgreSQL JDBC URL
  - `DB_USERNAME`, `DB_PASSWORD`: credentials
  - `JPA_DDL_AUTO`: Hibernate schema generation mode (e.g., `update`, `create-drop`, `validate`)
  - `DB_SCHEMA`: target schema in PostgreSQL
- Thymeleaf caching is disabled for development (`spring.thymeleaf.cache=false`)
- Lombok is used to reduce boilerplate via `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`

## Common Development Tasks

### Adding a New Entity and CRUD Endpoint

1. **Create the Entity** in `model/category/{domain}/{EntityName}.java`:
   - Use `@Entity`, `@Table`, `@Id`, `@GeneratedValue`
   - Add relationships with `@ManyToOne`, `@OneToMany` if needed
   - Use Lombok annotations (`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`)

2. **Create Request/Response DTOs** in `dto/{domain}/`:
   - `{Entity}RequestDTO.java` for input
   - `{Entity}ResponseDTO.java` for output

3. **Create a Repository** in `repository/{EntityName}Repository.java`:
   ```java
   public interface HotelsRepository extends JpaRepository<Hotels, Long> {
   }
   ```

4. **Create Service Interface** in `service/{domain}/{EntityName}Service.java`:
   - Define CRUD methods: `create`, `update`, `findById`, `listAll`, `delete`

5. **Create ServiceImpl** in `service/{domain}/{EntityName}ServiceImpl.java`:
   - Use `@Service`, inject repository, implement interface
   - Add `@Transactional` annotations
   - Include private `convertToResponseDTO()` helper for mapping

6. **Create REST Controller** in `controller/{domain}/{EntityName}Controller.java`:
   - Use `@RestController`, `@RequestMapping("/api/{entitypath}")`
   - Inject service, delegate to service methods
   - Return `ResponseEntity<DTO>`

### Working with Images

- Image entities (e.g., `HotelImage`) have `url` and `orden` fields
- When fetching the parent (e.g., Hotel), cascade loads images
- Extract cover/gallery in service layer:
  ```java
  String coverUrl = hotel.getImagenes().stream()
    .filter(i -> i.getOrden() == 0)
    .map(HotelImage::getUrl)
    .findFirst()
    .orElse("/img/placeholder-hotel.jpg");
  ```

### Adding a Thymeleaf View

1. Create HTML file in `src/main/resources/templates/{subdir}/{ViewName}.html`
2. Use Thymeleaf directives: `th:each`, `th:text`, `th:href`, `th:if`, etc.
3. For fragments: `<div th:fragment="fragment-name">...</div>`
4. Include fragments: `<div th:insert="~{path/ViewName :: fragment-name}"></div>`
5. Add route in `MainController` or dedicated view controller

### Running Tests

- Test dependencies are included (spring-boot-starter-data-jpa-test, spring-boot-starter-webmvc-test)
- Run all tests: `mvn test`
- Run single test: `mvn test -Dtest=MyServiceTest`
- Run single test method: `mvn test -Dtest=MyServiceTest#testMethod`

## Key Implementation Notes

- **No Security or Authentication**: Security, payment gateways, and authentication are still pending (see recent commits).
- **Environment-Driven Config**: Database and schema details come from environment variables, not hardcoded—essential for multi-environment deployments.
- **Lazy Loading**: Most relationships use `fetch = FetchType.LAZY` to avoid N+1 queries; use `@Transactional` to ensure sessions stay open.
- **Global Exception Handling**: `GlobalExceptionHandler` provides centralized error responses (check `exception/` package for customization).
- **Fragment-Based Views**: `MainController` uses Thymeleaf fragments to modularize the homepage without iframes, reducing the need for AJAX.

## Database Schema

The database is normalized around five strategic axes:

- **Accommodation**: Hotels, Estates, Rooms, with images
- **Ecotourism**: Sites, Iconic Places, with images
- **Trade**: Restaurants, Local shops, Products, Dishes, with images
- **Transport**: Buses, Motorbikes
- **Public Entities**: Events, News, Mayoralty, with images
- **User Management**: Users (with roles: Turista, Comerciante, Administrador), Bookings, Payments, Reviews

Use `JPA_DDL_AUTO=update` during development; validate or create-drop for testing/staging.

## Git Workflow

- Main branch: `main`
- Recent work focuses on image galleries and backend service implementations
- Check git status for uncommitted changes before starting new work
