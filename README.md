# Tourist Website Backend

Thanks for the clear request — I put together an industry‑style README that matches the codebase and makes onboarding easy.

**Tourist Website Backend** is a Spring Boot REST API for managing hotels, rooms, bookings, payments, and media uploads for a tourism/hospitality platform. It includes JWT authentication, role‑based access control, and MySQL persistence.

## Highlights
1. JWT auth with Google sign‑up integration
2. Role‑based access control (`admin` / `user`)
3. Hotel and room management with image uploads
4. Room booking and payment flows
5. Static file hosting for uploaded images

## Tech Stack
1. Java 17
2. Spring Boot 3.4.9 (Web, Security, Data JPA, Validation)
3. MySQL 8 (via `mysql-connector-j`)
4. JWT (`jjwt`)
5. ModelMapper
6. Maven

## Architecture Overview
1. `controller` — REST endpoints
2. `service` — business logic
3. `repo` — Spring Data JPA repositories
4. `entity` — database entities
5. `dto` — request/response payloads
6. `authentication` + `util` — JWT & security helpers

## Requirements
1. Java 17+
2. Maven 3.9+
3. MySQL 8+

## Quick Start
1. Create a MySQL database (or let the app create it):
   - `touristWebsite` (auto‑created if missing)
2. Configure DB credentials in `src/main/resources/application.properties`
3. Run the app:

```bash
./mvnw spring-boot:run
```

Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

The API starts on the default Spring Boot port `8080`.

## Configuration
Key settings live in `src/main/resources/application.properties`:

1. `spring.datasource.url`
2. `spring.datasource.username`
3. `spring.datasource.password`
4. `spring.jpa.hibernate.ddl-auto` (currently `update`)
5. `file.upload-dir` (currently `uploads/`)

Note: replace the database password with your own secure value.

## Authentication & Roles
1. JWT tokens are issued on `/auth/login` and `/auth/signUp`
2. Roles are stored in `UserEntity.role`
3. `admin` endpoints are protected using `@PreAuthorize("hasRole('admin')")`
4. Default role prefix is removed in `SecurityConfig` (roles are plain `admin`, `user`)

Include the token in requests:

```
Authorization: Bearer <JWT>
```

## API Summary
Base URL: `http://localhost:8080`

### Auth
1. `POST /auth/login` — email/password login
2. `POST /auth/signUp` — Google token sign‑up/login

### Users
1. `POST /` — create user
2. `GET /users` — list users (requires auth)
3. `GET /user` — current user (requires auth)

### Hotels
1. `GET /hotels/` — list hotels (public)
2. `GET /hotels/{id}` — hotel details (auth required)
3. `GET /hotels/search/{keyword}` — search (public)
4. `POST /hotels/create` — create hotel (admin)
5. `PUT /hotels/update/{id}` — update hotel (admin)
6. `DELETE /hotels/delete/{id}` — delete hotel (admin)
7. `POST /hotels/{hotelId}/uploadImage` — upload hotel image (admin)

### Rooms
1. `GET /rooms/` — list rooms (public)
2. `GET /rooms/{id}` — room details (auth required)
3. `GET /rooms/hotel/{hotelId}` — rooms by hotel (auth required)
4. `GET /rooms/search/{hotelId}/{type}` — search by type (public)
5. `POST /rooms/create` — create room (admin)
6. `PUT /rooms/update/{id}` — update room (public per current security config)
7. `DELETE /rooms/delete/{id}` — delete room (admin)
8. `POST /rooms/{id}/uploadImage` — upload room image (admin)

### Bookings
1. `POST /booking/create` — create booking (public)
2. `GET /booking/` — list all bookings (admin)
3. `GET /booking/{bookingId}` — booking details (public)
4. `PUT /booking/update/{id}` — update booking (public)
5. `DELETE /booking/delete/{bookingId}` — delete booking (public)

### Payments
1. `POST /payment/rooms/create` — create payment (public)
2. `GET /payment/rooms/` — list payments (admin)
3. `GET /payment/rooms/{bookingId}/{paymentId}` — payment for booking (public)
4. `GET /payment/rooms/{paymentId}` — payment by ID (admin)

### Media
1. `GET /uploads/**` — public access to uploaded files

## File Uploads
1. Uploaded images are stored in `uploads/`
2. Served via `/uploads/**`
3. Update `file.upload-dir` if you want a different location

## Development Notes
1. CORS is currently open to `http://localhost:5173`
2. Sessions are stateless (JWT)
3. Packaging is `war` (Tomcat provided)

## Testing
Run unit tests:

```bash
./mvnw test
```

## Project Structure
```
src/
  main/
    java/com/example/touristWebsite/
      authentication/
      config/
      controller/
      dto/
      entity/
      repo/
      service/
      util/
    resources/
      application.properties
uploads/
```

## License
Add your license of choice (e.g., MIT, Apache-2.0).
