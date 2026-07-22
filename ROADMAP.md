# The Complete Project-Driven Spring Boot E-Commerce Roadmap (User-Facing Edition)

**What changed from the original 25-chapter version:** This edition removes all admin-side functionality (admin dashboards, admin order management, role-based admin/customer split) and keeps only what a logged-in customer interacts with. Two chapters were removed outright (old Ch.9 Role-Based Authorization, old Ch.15 Admin Dashboard & Aggregation), one chapter had its admin half surgically removed (old Ch.12 → now just "Order History"), and minor admin-only mentions were trimmed from a few others (old Ch.13's optional restock endpoint, old Ch.20's admin-triggered cache eviction framing). Everything else is unchanged in substance. Chapters are renumbered 1–23 to stay sequential.

**How to use this document:** Same as before — copy one entire chapter block (Current Project State → Preview of Next Chapter) into a brand-new AI chat. That AI needs zero prior context. Do them in order. Don't let the AI write your code — it should explain, question, and review, not generate.

Keep this file updated as you go: tick checklists, note deviations, keep "Current Project State" honest for the next chapter.

---

## Master Chapter Index

| # | Chapter | Core Feature Added | Big Concepts Unlocked |
|---|---------|--------------------|-----------------------|
| 1 | Product Search & Filtering | Search by name/category/brand/price | `@RequestParam`, JPA Query Methods, Specifications, `Optional` |
| 2 | Pagination & Sorting | Paged, sorted product listing | `Pageable`, `Page<T>`, `Sort`, API contract design |
| 3 | The DTO Layer | Clean request/response contracts | DTO pattern, entity/DTO separation, MapStruct or manual mapping |
| 4 | Validation & Global Exception Handling | Reject bad input cleanly | Bean Validation, `@ControllerAdvice`, custom exceptions |
| 5 | Category & Brand Relationships | Real relational catalog | `@ManyToOne`, `@OneToMany`, fetch types, N+1 problem intro |
| 6 | User Registration & Password Security | Real user accounts | `BCrypt`, unique constraints, entity design for auth |
| 7 | Spring Security Fundamentals | Login-gated endpoints | Security filter chain, `UserDetailsService`, sessions vs tokens |
| 8 | JWT Authentication | Stateless login | JWT structure, signing, filters, stateless sessions |
| 9 | Persistent Shopping Cart | Cart tied to logged-in user | Cart-user relationship, concurrency basics, transactional updates |
| 10 | Checkout & Order Creation | Cart → real Order | `@Transactional`, order/order-item modeling, stock decrement |
| 11 | Order History | View your own past orders | Query by user, order status state machine |
| 12 | Inventory Management & Concurrency | Prevent overselling | Optimistic locking, `@Version`, race conditions |
| 13 | Profile & Address Management | Editable user profile, addresses | One-to-many address modeling, partial updates (PATCH) |
| 14 | API Documentation with Swagger/OpenAPI | Self-documenting API | springdoc-openapi, annotations, API contracts as artifacts |
| 15 | Logging & Observability | Real production logging | SLF4J/Logback, MDC, structured logs, log levels |
| 16 | Unit Testing | Test business logic in isolation | JUnit 5, Mockito, testing services without a DB |
| 17 | Integration Testing | Test the real stack | `@SpringBootTest`, Testcontainers, MockMvc |
| 18 | Caching | Speed up hot reads | Spring Cache abstraction, Redis, cache invalidation |
| 19 | Performance Optimization | Fix slow queries | N+1 fixes, indexing, `EXPLAIN`, connection pooling |
| 20 | Dockerizing the Application | Portable, reproducible builds | Dockerfile, multi-stage builds, docker-compose |
| 21 | Production Configuration & Deployment | Ship it | Spring profiles, environment config, actuator, deployment target |
| 22 | React Frontend Integration | A real UI talking to your API | CORS, API consumption, auth token handling client-side |
| 23 | Final System Design Review & Interview Prep | Defend the whole system | Architecture walkthrough, tradeoff articulation, mock interview |

---

# Chapter 1 — Product Search & Filtering

> **Mentor Rules for the AI in this chat (read before responding):** You are a mentor, not a code generator. Do NOT write, generate, or paste implementation code for me unless I explicitly say "write the code" or "show me the code." Your default behavior is to explain concepts, ask me guiding questions, give hints, point out what's missing, and review code *I* paste in — never produce it yourself first. If I ask "how do I do X," explain the approach and relevant concepts; do not hand me a working snippet unless I ask for it directly.

### Current Project State
This is a Spring Boot backend for an e-commerce application. Completed so far: a `Product` entity persisted via Spring Data JPA to MySQL; a `Repository`, `Service`, and `Controller` layer; basic CRUD endpoints (`GET /products`, `GET /products/{id}`, `POST /products`, presumably `PUT`/`DELETE`); a basic Cart implementation. No DTOs yet. No validation, no exception handling, no security. Architecture is a simple 3-layer (Controller → Service → Repository). Note: there is no admin role in this project — product creation/editing endpoints will later just require the requester to be a logged-in user, not a special role.

### Today's Goal
Add the ability for a customer to search and filter products by name, category, brand, and price range via query parameters — e.g. `GET /products/search?name=shoe&minPrice=500&maxPrice=2000`.

### Feature List
- Search products by partial name match
- Filter by category and/or brand (plain string fields for now — relational category/brand comes in Chapter 5)
- Filter by min/max price range
- All filters combinable and all optional

### Spring Concepts Introduced
- `@RequestParam` (including `required = false` and default values)
- Spring Data JPA derived query methods (e.g. `findByNameContainingIgnoreCase`)
- Introduction to `JpaSpecificationExecutor` / Specifications for combining optional filters dynamically

### Java Concepts Introduced
- `Optional<T>` for values that may or may not be present
- Method overloading vs. flexible parameter design

### Database Concepts Introduced
- `LIKE` queries and how `Containing` translates to SQL
- Basic index awareness (just awareness, not indexing yet)

### HTTP Concepts Introduced
- Query parameters vs. path variables — when to use which
- Idempotency of `GET` requests

### Implementation Order
**Theory Before Coding:** Understand the difference between raw JPQL, derived query method names, and the Specification API. Understand why a search endpoint should be `GET` with query params, not `POST` with a body.

**Implementation Tasks**
1. Design the search endpoint signature: what query params exist, which are optional.
2. Add derived query methods for each filter combination, OR implement a single dynamic Specification-based query.
3. Wire the new method through the service layer.
4. Add the controller endpoint accepting the query params.
5. Handle the "no filters provided" case sensibly.

### Testing Checklist (Postman)
- Search with only `name`
- Search with only price range
- Search with all filters combined
- Search with no filters at all
- Search with a price range matching nothing (empty list, not an error)
- Search with `minPrice > maxPrice` (should this be validated? Note it for Chapter 4)

### Common Mistakes
- Giant `if/else` chain in the service instead of pushing filtering into the query layer
- Returning `null` instead of an empty list
- Case-sensitive search
- Forgetting `IgnoreCase`

### Interview Questions
- How does Spring Data JPA turn `findByNameContainingIgnoreCase` into SQL?
- When would you reach for the Specification API instead of derived query methods?
- Why is search implemented as GET and not POST?

### Mini Exercises
- Add a filter for "in stock only" (boolean) without being told the exact method signature.
- Explain what SQL your derived method name would generate.

### Completion Checklist
- [ ] Search endpoint implemented and working in Postman
- [ ] All four filter types work individually and combined
- [ ] Empty results return `200 OK` with an empty array
- [ ] You can explain how the query method name maps to SQL

### Project Progress
**Completed:** CRUD, search/filtering.
**Left:** Pagination, DTOs, validation, relationships, auth, cart persistence, checkout, order history, inventory concurrency, profile, docs, logging, testing, caching, performance, docker, deployment, frontend.
**Next chapter preview:** Chapter 2 adds pagination and sorting so results come back in manageable, ordered pages.

---

# Chapter 2 — Pagination & Sorting

> **Mentor Rules for the AI in this chat (read before responding):** You are a mentor, not a code generator. Do NOT write, generate, or paste implementation code for me unless I explicitly say "write the code" or "show me the code." Explain, question, review — don't generate unless asked directly.

### Current Project State
Spring Boot e-commerce backend. Completed: CRUD, and a search/filter endpoint (`/products/search`) supporting name, category, brand, and price-range filters. No pagination yet.

### Today's Goal
Make every product-listing endpoint return paged, sorted results instead of the entire table.

### Feature List
- `GET /products?page=0&size=10&sort=price,asc`
- Search endpoint also accepts pagination/sorting params
- Response includes page metadata: total elements, total pages, current page, page size

### Spring Concepts Introduced
- `Pageable` as a controller method parameter
- `Page<T>` and `Slice<T>` — what's the difference and when each matters
- `PageRequest.of(page, size, sort)`
- Repository methods returning `Page<Product>` instead of `List<Product>`

### Java Concepts Introduced
- Generics recap in the context of `Page<T>`
- Immutability of `Pageable`/`Sort` objects

### Database Concepts Introduced
- `LIMIT`/`OFFSET` under the hood
- Why offset-based pagination gets slow at huge scale (mention keyset pagination — later, at scale)

### HTTP Concepts Introduced
- Designing paginated API responses (`content`, `page`, `totalElements`, etc.)
- Why sorting/pagination belong in query params, not the body

### Implementation Order
**Theory Before Coding:** Understand `Page<T>` (total count, total pages) vs `Slice<T>` (cheaper, no total count). Understand your target response shape.

**Implementation Tasks**
1. Change repository/search methods to accept `Pageable`, return `Page<Product>`.
2. Update service layer signatures.
3. Update controllers to accept a `Pageable` (auto-built from `page`/`size`/`sort`).
4. Implement a default page size and a max allowed page size.
5. Confirm the JSON response shape is sensible for a frontend.

### Testing Checklist (Postman)
- Default pagination (no params) returns sensible defaults
- Explicit `page` and `size` work
- Sorting ascending/descending on at least two fields
- Requesting a page beyond the last returns empty content, not an error
- Oversized `size` is capped

### Common Mistakes
- Forgetting to cap `size`
- Sorting on a nonexistent field without graceful handling
- Returning `Page<Product>` (entity) directly instead of thinking about the client's real needs (foreshadowing Chapter 3)

### Interview Questions
- Difference between `Page` and `Slice`?
- Why does offset-based pagination degrade at large offsets, and what's the alternative?
- How would you prevent unbounded page size requests?

### Mini Exercises
- Implement a max page size cap yourself, with a clear error or clamped behavior — justify the choice.

### Completion Checklist
- [ ] Listing and search endpoints both paginated
- [ ] Sorting works on multiple fields
- [ ] Page size is capped
- [ ] You can explain `Page` vs `Slice`

### Project Progress
**Completed:** CRUD, search/filter, pagination, sorting.
**Left:** DTOs, validation, relationships, auth, cart persistence, checkout, order history, inventory concurrency, profile, docs, logging, testing, caching, performance, docker, deployment, frontend.
**Next chapter preview:** Chapter 3 introduces the DTO pattern so your API has its own stable contract, independent of your database schema.

---

# Chapter 3 — The DTO Layer

> **Mentor Rules for the AI in this chat (read before responding):** You are a mentor, not a code generator. Do NOT write, generate, or paste implementation code for me unless I explicitly say "write the code" or "show me the code." Explain, question, review — don't generate unless asked directly.

### Current Project State
Spring Boot e-commerce backend with CRUD, search/filtering, and pagination/sorting. Controllers return `Product` entities directly. No DTOs, no validation, no security yet.

### Today's Goal
Introduce a proper DTO layer so request/response shapes are decoupled from JPA entities.

### Feature List
- `ProductRequestDTO` for creating/updating products
- `ProductResponseDTO` for what clients receive
- Mapping logic between entity and DTO (manual, or via MapStruct)

### Spring Concepts Introduced
- Why controllers should never accept/return entities directly
- Where mapping logic belongs
- MapStruct as an option vs. manual mapping

### Java Concepts Introduced
- Records vs. classes for DTOs (Java 17+ records)
- Builder pattern as an alternative to telescoping constructors

### Database Concepts Introduced
- None new — this is about the boundary between DB and API

### HTTP Concepts Introduced
- API contract stability: DB schema changes shouldn't break API clients, and vice versa

### Implementation Order
**Theory Before Coding:** Entities carry persistence concerns (lazy-loading proxies, internal IDs) that don't belong in an API contract. Understand where mapping should live architecturally.

**Implementation Tasks**
1. Define `ProductRequestDTO` (should it include `id`? think about it).
2. Define `ProductResponseDTO` (should it expose internal audit fields?).
3. Write mapping logic (manual, dedicated mapper class, or MapStruct — justify your choice).
4. Update controllers to accept/return DTOs only.
5. Update service layer to work with DTOs at the boundary, entities internally.

### Testing Checklist (Postman)
- Create via `ProductRequestDTO` shape, confirm `ProductResponseDTO` shape response
- No internal-only fields leak into responses
- Search and paginated listing endpoints now return DTOs

### Common Mistakes
- Mapping logic inside the controller
- One DTO doing double duty for request and response
- Forgetting to update all endpoints, leaving some still leaking entities

### Interview Questions
- Why not expose JPA entities directly through the API?
- Tradeoff between manual mapping and MapStruct?
- Where should mapping logic live in a layered architecture, and why?

### Mini Exercises
- Add a `CategorySummaryDTO` used inside `ProductResponseDTO` (plain string field wrapped in a DTO for now) to practice nested DTO design.

### Completion Checklist
- [ ] No controller method accepts or returns a raw entity
- [ ] Request and response DTOs are distinct types
- [ ] Mapping logic lives in one clear, consistent place

### Project Progress
**Completed:** CRUD, search/filter, pagination, DTO layer.
**Left:** Validation, exception handling, relationships, auth, cart persistence, checkout, order history, inventory concurrency, profile, docs, logging, testing, caching, performance, docker, deployment, frontend.
**Next chapter preview:** Chapter 4 adds validation and a global exception handler so bad input is rejected cleanly.

---

# Chapter 4 — Validation & Global Exception Handling

> **Mentor Rules for the AI in this chat (read before responding):** You are a mentor, not a code generator. Do NOT write, generate, or paste implementation code for me unless I explicitly say "write the code" or "show me the code." Explain, question, review — don't generate unless asked directly.

### Current Project State
Spring Boot e-commerce backend with CRUD, search/filter, pagination, and a DTO layer. No input validation exists yet. No centralized exception handling.

### Today's Goal
Add request validation on DTOs and a global exception handler so the API responds with clean, consistent error responses.

### Feature List
- Validated `ProductRequestDTO` (name required, price positive, etc.)
- Custom exceptions (e.g. `ProductNotFoundException`)
- A global `@ControllerAdvice` mapping exceptions to proper HTTP status codes and a consistent error body

### Spring Concepts Introduced
- Bean Validation annotations (`@NotBlank`, `@Positive`, `@Min`, `@Max`)
- `@Valid` on controller method parameters
- `@ControllerAdvice` / `@RestControllerAdvice`, `@ExceptionHandler`
- `MethodArgumentNotValidException`

### Java Concepts Introduced
- Custom exception classes and hierarchies
- Checked vs. unchecked exceptions

### Database Concepts Introduced
- Database-level constraints (`NOT NULL`, `UNIQUE`) as a second line of defense

### HTTP Concepts Introduced
- Proper status codes: `400`, `404`, `409`, `422`
- Consistent error response shape (timestamp, status, message, path, field errors)

### Implementation Order
**Theory Before Coding:** Layered defense: app-level validation plus DB constraints. Understand why leaking stack traces to clients is a security/professionalism problem.

**Implementation Tasks**
1. Add Bean Validation annotations to `ProductRequestDTO` fields.
2. Add `@Valid` to relevant controller parameters.
3. Create custom exceptions for meaningful failure cases.
4. Build a `GlobalExceptionHandler` with `@RestControllerAdvice`.
5. Design a consistent error response DTO.

### Testing Checklist (Postman)
- Blank name → clean `400` with field-level message
- Negative price → rejected
- Non-existent product ID → clean `404`, not a stack trace
- Unexpected error path doesn't leak internal details

### Common Mistakes
- Validating in the service layer instead of at the DTO/controller boundary
- One giant `catch (Exception e)` swallowing meaningful distinctions
- Leaking stack traces or internal class names

### Interview Questions
- How does `@Valid` + Bean Validation actually trigger validation?
- Why a global exception handler instead of try/catch everywhere?
- Difference between `400` and `422`, does your API need to distinguish them?

### Mini Exercises
- Add validation for a field type you haven't handled (string length constraint or `@Pattern`).

### Completion Checklist
- [ ] Invalid product data rejected with clear field-level errors
- [ ] Not-found cases return `404` with a clean body
- [ ] All exceptions handled in one central place

### Project Progress
**Completed:** CRUD, search/filter, pagination, DTOs, validation, exception handling.
**Left:** Relationships, auth, cart persistence, checkout, order history, inventory concurrency, profile, docs, logging, testing, caching, performance, docker, deployment, frontend.
**Next chapter preview:** Chapter 5 turns category/brand into real relational entities — `@ManyToOne`/`@OneToMany` and the N+1 problem.

---

# Chapter 5 — Category & Brand Relationships

> **Mentor Rules for the AI in this chat (read before responding):** You are a mentor, not a code generator. Do NOT write, generate, or paste implementation code for me unless I explicitly say "write the code" or "show me the code." Explain, question, review — don't generate unless asked directly.

### Current Project State
Spring Boot e-commerce backend with CRUD, search/filter, pagination, DTOs, validation/exception handling. `Category`/`Brand` are plain string fields on `Product` (or don't exist as separate entities yet).

### Today's Goal
Model `Category` and `Brand` as real entities with proper JPA relationships to `Product`, and understand fetch performance implications.

### Feature List
- `Category` entity (flat is fine for now)
- `Brand` entity
- `Product` gets a `@ManyToOne` to both
- Endpoints to list categories/brands; filter products by category/brand ID instead of string match

### Spring Concepts Introduced
- `@ManyToOne`, `@OneToMany`, `@JoinColumn`
- `FetchType.LAZY` vs `FetchType.EAGER`
- Cascade types (brief)

### Java Concepts Introduced
- Bidirectional vs. unidirectional relationships
- `equals()`/`hashCode()` for JPA entities in relationships

### Database Concepts Introduced
- Foreign keys and how relationships map to them
- The N+1 query problem — what it is, how to spot it, at least one fix (`JOIN FETCH` or `@EntityGraph`)

### HTTP Concepts Introduced
- Designing nested resource responses (full category object vs. just name/ID?)

### Implementation Order
**Theory Before Coding:** Why LAZY is the safer default; what `LazyInitializationException` is. Understand N+1 conceptually.

**Implementation Tasks**
1. Create `Category` and `Brand` entities with their own repositories.
2. Update `Product` to reference them via `@ManyToOne`.
3. Update the DTOs to reflect the relationship (request takes `categoryId`, response includes `CategorySummaryDTO`).
4. Update search/filter logic to filter by category/brand ID.
5. Deliberately reproduce an N+1, observe it in SQL logs, then fix it.

### Testing Checklist (Postman)
- Create categories and brands
- Create a product referencing a category and brand ID
- List products and confirm category/brand data appears correctly
- Enable SQL logging, count queries before/after your N+1 fix

### Common Mistakes
- `FetchType.EAGER` everywhere "to be safe"
- Exposing full nested entity graphs in DTOs (infinite recursion / bloat)
- Not noticing N+1 on a too-small dataset

### Interview Questions
- What's the N+1 query problem and how do you detect/fix it?
- Why is `FetchType.LAZY` generally preferred?
- How do bidirectional JPA relationships risk infinite loops in serialization?

### Mini Exercises
- Add a category hierarchy (parent category) and think through the fetching impact.

### Completion Checklist
- [ ] Category and Brand are real entities with proper relationships
- [ ] You've observed and fixed an N+1 query in logs
- [ ] DTOs cleanly represent nested relationships

### Project Progress
**Completed:** CRUD, search/filter, pagination, DTOs, validation, exception handling, relational catalog.
**Left:** Auth, cart persistence, checkout, order history, inventory concurrency, profile, docs, logging, testing, caching, performance, docker, deployment, frontend.
**Next chapter preview:** Chapter 6 introduces user registration with securely hashed passwords.

---

# Chapter 6 — User Registration & Password Security

> **Mentor Rules for the AI in this chat (read before responding):** You are a mentor, not a code generator. Do NOT write, generate, or paste implementation code for me unless I explicitly say "write the code" or "show me the code." Explain, question, review — don't generate unless asked directly.

### Current Project State
Spring Boot e-commerce backend with a relational product catalog, search/pagination, DTOs, validation/exception handling. No user accounts exist yet.

### Today's Goal
Build a `User` entity and a registration endpoint that stores passwords securely. Foundation for "my cart," "my orders," "who is allowed to do this."

### Feature List
- `User` entity (email, password, name)
- `POST /auth/register`
- Passwords hashed with BCrypt
- Duplicate email prevented at both application and database level

### Spring Concepts Introduced
- `PasswordEncoder` / `BCryptPasswordEncoder`

### Java Concepts Introduced
- One-way hashing vs. encryption
- Salting (BCrypt handles it, but know why it matters)

### Database Concepts Introduced
- Unique constraints (`@Column(unique = true)`)
- Why you never store plaintext passwords

### HTTP Concepts Introduced
- `409 Conflict` for duplicate registration attempts

### Implementation Order
**Theory Before Coding:** Why hashing (BCrypt) instead of reversible encryption. What a salt does.

**Implementation Tasks**
1. Create the `User` entity with email, hashed password, name.
2. Add a unique constraint on email.
3. Add `BCryptPasswordEncoder` as a bean.
4. Build registration: validate input, check existing email, hash password, save.
5. Never echo the password back in the response.

### Testing Checklist (Postman)
- Register a new user successfully
- Register same email twice — clean `409`
- Confirm the stored password is a BCrypt hash, not plaintext
- Confirm the API response never includes the password field

### Common Mistakes
- Returning the password (even hashed) in the API response
- Using MD5/SHA-256 directly for passwords instead of BCrypt
- Relying only on a DB unique constraint without a friendly app-level check first

### Interview Questions
- Why is BCrypt preferred over plain SHA-256 for passwords?
- What is a salt, and why does it matter even with a strong hash algorithm?
- Why write `equals()`/`toString()` on `User` carefully to avoid leaking the password into logs?

### Mini Exercises
- Add basic password strength validation (min length, at least one number) via Bean Validation.

### Completion Checklist
- [ ] Users can register
- [ ] Passwords hashed with BCrypt, never plaintext
- [ ] Duplicate emails rejected cleanly
- [ ] Password never appears in any API response

### Project Progress
**Completed:** Catalog, search, pagination, DTOs, validation, user registration.
**Left:** Login/auth, JWT, cart persistence, checkout, order history, inventory concurrency, profile, docs, logging, testing, caching, performance, docker, deployment, frontend.
**Next chapter preview:** Chapter 7 introduces Spring Security fundamentals — the filter chain, authentication, and locking down endpoints for the first time.

---

# Chapter 7 — Spring Security Fundamentals

> **Mentor Rules for the AI in this chat (read before responding):** You are a mentor, not a code generator. Do NOT write, generate, or paste implementation code for me unless I explicitly say "write the code" or "show me the code." Explain, question, review — don't generate unless asked directly.

### Current Project State
Spring Boot e-commerce backend with a relational catalog and user registration (BCrypt-hashed passwords). No login exists yet. No endpoints are protected.

### Today's Goal
Introduce Spring Security: understand the filter chain, implement session-based login as a stepping stone (JWT next chapter), protect at least one endpoint.

### Feature List
- `POST /auth/login` (session-based this chapter)
- At least one protected endpoint (e.g. viewing your own profile)
- Public endpoints (browsing, registration) remain open

### Spring Concepts Introduced
- The Spring Security filter chain
- `UserDetailsService` and `UserDetails`
- `SecurityFilterChain` bean configuration
- `AuthenticationManager`
- `HttpSecurity` request matchers (`permitAll()`, `authenticated()`)

### Java Concepts Introduced
- Interfaces as extension points

### Database Concepts Introduced
- None new — this sits on top of your existing `User` table

### HTTP Concepts Introduced
- `401 Unauthorized` vs `403 Forbidden`
- Sessions and cookies (context for why JWT comes next)

### Implementation Order
**Theory Before Coding:** Every request passes through a filter chain; Spring Security inserts auth filters into it. Understand why session-based auth doesn't scale as cleanly as tokens for APIs.

**Implementation Tasks**
1. Add the Spring Security dependency.
2. Implement `UserDetailsService` backed by your `User` repository.
3. Configure a `SecurityFilterChain`: public endpoints permitted, everything else authenticated.
4. Implement `/auth/login` using the authentication manager.
5. Protect one real endpoint.

### Testing Checklist (Postman)
- Access a public endpoint without logging in — should work
- Access a protected endpoint without logging in — `401`
- Log in, access the protected endpoint with the session — should work
- Wrong credentials — clean failure, no leaking whether the email exists

### Common Mistakes
- Accidentally locking down `permitAll()` endpoints
- Leaking whether an email exists via different error messages
- Confusing `401`/`403`

### Interview Questions
- Walk through what happens when a request hits a Spring Security–protected endpoint.
- Difference between authentication and authorization?
- Why is session-based auth a poor fit for a stateless REST API serving mobile/web clients?

### Mini Exercises
- Protect a second endpoint and verify it yourself first.

### Completion Checklist
- [ ] Login works
- [ ] At least one endpoint is genuinely protected
- [ ] Public endpoints remain public
- [ ] You can explain the filter chain in your own words

### Project Progress
**Completed:** Catalog, registration, session-based login, first protected endpoint.
**Left:** JWT, cart persistence, checkout, order history, inventory concurrency, profile, docs, logging, testing, caching, performance, docker, deployment, frontend.
**Next chapter preview:** Chapter 8 replaces sessions with JWT — the token-based approach real APIs and interviews expect.

---

# Chapter 8 — JWT Authentication

> **Mentor Rules for the AI in this chat (read before responding):** You are a mentor, not a code generator. Do NOT write, generate, or paste implementation code for me unless I explicitly say "write the code" or "show me the code." Explain, question, review — don't generate unless asked directly.

### Current Project State
Spring Boot e-commerce backend with working session-based Spring Security: registration, login, one protected endpoint. `UserDetailsService` implemented.

### Today's Goal
Replace session-based auth with JWT — stateless, no server-side session storage.

### Feature List
- Login returns a signed JWT
- A JWT filter validating the token per request and populating the security context
- Protected endpoints require `Authorization: Bearer <token>`

### Spring Concepts Introduced
- Custom `OncePerRequestFilter` for JWT validation
- `SessionCreationPolicy.STATELESS`
- Wiring a custom filter into the `SecurityFilterChain`

### Java Concepts Introduced
- Working with a JWT library (e.g. `jjwt`) — building, signing, parsing
- Base64 encoding basics as related to JWT structure

### Database Concepts Introduced
- None new — but discuss why you're not storing tokens (statelessness) vs. refresh-token strategies

### HTTP Concepts Introduced
- The `Authorization: Bearer <token>` convention
- Why JWT enables stateless, horizontally scalable APIs

### Implementation Order
**Theory Before Coding:** JWT structure: header, payload, signature — payload is encoded, not encrypted. Never put secrets in it.

**Implementation Tasks**
1. Add a JWT library dependency.
2. Build a `JwtUtil` to generate/validate tokens, including expiration.
3. Update `/auth/login` to return a JWT.
4. Build a `JwtAuthenticationFilter` extending `OncePerRequestFilter`.
5. Update the `SecurityFilterChain` to `STATELESS` and register the filter.

### Testing Checklist (Postman)
- Login returns a valid JWT
- Valid token on a protected endpoint works
- No token → `401`
- Tampered/invalid token → `401`
- Expired token → `401` (test with a short expiry)

### Common Mistakes
- Putting sensitive data in the JWT payload
- Forgetting expiration
- Not validating the signature properly

### Interview Questions
- Three parts of a JWT, and what each is responsible for?
- Why is a JWT payload "readable but tamper-evident," and what does that mean for what you put in it?
- Session-based vs. token-based auth, scalability-wise?

### Mini Exercises
- Decode one of your generated JWTs manually (base64-decode header/payload by hand).

### Completion Checklist
- [ ] Login returns a JWT
- [ ] Protected endpoints require and validate the token
- [ ] Expired/invalid tokens rejected
- [ ] You can explain JWT structure without looking it up

### Project Progress
**Completed:** Stateless JWT authentication fully working.
**Left:** Cart persistence, checkout, order history, inventory concurrency, profile, docs, logging, testing, caching, performance, docker, deployment, frontend.
**Next chapter preview:** Chapter 9 rebuilds your Cart to be tied to the logged-in user, persisted properly, and safe under concurrent updates.

---

# Chapter 9 — Persistent Shopping Cart

> **Mentor Rules for the AI in this chat (read before responding):** You are a mentor, not a code generator. Do NOT write, generate, or paste implementation code for me unless I explicitly say "write the code" or "show me the code." Explain, question, review — don't generate unless asked directly.

### Current Project State
Spring Boot e-commerce backend with full JWT-based stateless authentication. A basic Cart exists but predates user accounts.

### Today's Goal
Rebuild the cart so it belongs to the logged-in user, persists in the database, and supports add/update-quantity/remove.

### Feature List
- `Cart` entity owned by a `User` (one active cart per user)
- `CartItem` entity (product + quantity)
- `POST /cart/items`, `PUT /cart/items/{id}`, `DELETE /cart/items/{id}`, `GET /cart`

### Spring Concepts Introduced
- Deriving the current user from the security context (`Authentication`/`Principal`)
- `@OneToOne` (User–Cart) alongside `@OneToMany` (Cart–CartItem)

### Java Concepts Introduced
- Encapsulating cart total/quantity calculations as entity or service behavior

### Database Concepts Introduced
- Composite uniqueness (one active cart per user; duplicate product row handling)

### HTTP Concepts Introduced
- Designing endpoints around "the current user's resource" (`/cart` means "my cart")

### Implementation Order
**Theory Before Coding:** Decide cart semantics: does adding an existing product increment quantity or error? One active cart per user, kept simple.

**Implementation Tasks**
1. Create `Cart` (one-to-one with `User`) and `CartItem` (many-to-one to `Cart` and `Product`).
2. Retrieve the current user from the security context in the controller/service.
3. Implement add-to-cart logic, handling the "already in cart" case.
4. Implement update-quantity and remove-item.
5. Implement `GET /cart` with computed total.

### Testing Checklist (Postman)
- Add a product to cart as a logged-in user
- Add the same product again — confirm your chosen behavior
- Update an item's quantity
- Remove an item
- Confirm one user cannot see or modify another user's cart

### Common Mistakes
- Trusting a client-supplied `userId` instead of deriving it from the authenticated principal
- Not handling the "add existing product" case explicitly
- Forgetting to recalculate totals after mutations

### Interview Questions
- Why derive the current user from the security context, never a client-supplied ID?
- How would "save for later" differ from an active cart?
- What DB constraint prevents two active carts per user?

### Mini Exercises
- Add a "clear cart" endpoint yourself, including the authorization check.

### Completion Checklist
- [ ] Cart is tied to the authenticated user, never a client-supplied ID
- [ ] Add/update/remove/view all work correctly
- [ ] One user cannot access another's cart
- [ ] Duplicate-add behavior is deliberate and tested

### Project Progress
**Completed:** Persistent, user-owned shopping cart.
**Left:** Checkout, order history, inventory concurrency, profile, docs, logging, testing, caching, performance, docker, deployment, frontend.
**Next chapter preview:** Chapter 10 turns a cart into a real, permanent Order — transactions, order/order-item modeling.

---

# Chapter 10 — Checkout & Order Creation

> **Mentor Rules for the AI in this chat (read before responding):** You are a mentor, not a code generator. Do NOT write, generate, or paste implementation code for me unless I explicitly say "write the code" or "show me the code." Explain, question, review — don't generate unless asked directly.

### Current Project State
Spring Boot e-commerce backend with full auth and a persistent, user-owned shopping cart.

### Today's Goal
Implement checkout: convert the cart into a permanent `Order`, decrement stock, clear the cart — atomically.

### Feature List
- `Order` and `OrderItem` entities (snapshot of what was purchased, at what price, at that time)
- `POST /checkout`
- Stock decremented on checkout; insufficient stock rejected
- Cart emptied after successful checkout

### Spring Concepts Introduced
- `@Transactional` — what it guarantees
- Propagation and rollback basics

### Java Concepts Introduced
- Why `OrderItem` snapshots price/product name rather than referencing the live `Product`

### Database Concepts Introduced
- Multi-table writes inside one transaction
- Why snapshotting avoids historical order records silently changing

### HTTP Concepts Introduced
- `409 Conflict` for "insufficient stock"

### Implementation Order
**Theory Before Coding:** What `@Transactional` does and doesn't guarantee. Why `OrderItem` needs its own copies of price/name.

**Implementation Tasks**
1. Create `Order`/`OrderItem` with snapshot fields.
2. Implement checkout: validate stock for every cart item, decrement stock, create order + items, clear cart — all in one `@Transactional` method.
3. Implement the failure path: any insufficient item rolls back the whole operation, with a clear error.
4. Wire the controller endpoint.

### Testing Checklist (Postman)
- Successful checkout with sufficient stock — order created, stock decremented, cart emptied
- One item insufficient — entire operation rolls back
- `OrderItem` retains its price even after subsequently changing the `Product`'s price

### Common Mistakes
- Checking and decrementing stock as two separate, non-atomic steps (race condition — Chapter 12 fixes this)
- Forgetting `@Transactional`
- Referencing live `Product` price instead of snapshotting

### Interview Questions
- What does `@Transactional` guarantee, and how do developers accidentally break it (e.g. self-invocation)?
- Why should `OrderItem` store its own price/name copy?
- Walk through what happens if checkout fails partway through.

### Mini Exercises
- Deliberately trigger a rollback and confirm nothing was partially persisted.

### Completion Checklist
- [ ] Checkout is a single atomic operation
- [ ] Insufficient stock cleanly rolls back the entire checkout
- [ ] Order/OrderItem snapshot data correctly
- [ ] You understand `@Transactional`'s guarantees and self-invocation gotcha

### Project Progress
**Completed:** Full checkout flow with atomic order creation.
**Left:** Order history, inventory concurrency, profile, docs, logging, testing, caching, performance, docker, deployment, frontend.
**Next chapter preview:** Chapter 11 lets customers view their own past orders.

---

# Chapter 11 — Order History

> **Mentor Rules for the AI in this chat (read before responding):** You are a mentor, not a code generator. Do NOT write, generate, or paste implementation code for me unless I explicitly say "write the code" or "show me the code." Explain, question, review — don't generate unless asked directly.

### Current Project State
Spring Boot e-commerce backend with a working, atomic checkout flow producing real `Order`/`OrderItem` records and decrementing stock. There is no admin side in this project — order history is scoped entirely to the requesting user.

### Today's Goal
Let customers view their own past orders — a paginated list and a single-order detail view, both strictly scoped to the requester.

### Feature List
- `GET /orders` (the current user's order history, paginated)
- `GET /orders/{id}` (single order detail — only if it belongs to the requesting user)
- An `OrderStatus` enum (`PENDING`, `CONFIRMED`, `SHIPPED`, `DELIVERED`, `CANCELLED`) — set automatically at checkout and by whatever fulfillment logic you choose, since there's no admin to update it manually

### Spring Concepts Introduced
- Deriving ownership from the security context and filtering queries by the current user
- Query methods filtering by both user and status

### Java Concepts Introduced
- Enum-based state — even without an admin to trigger transitions, decide what states exist and what a customer should be able to see/do with them (e.g. can a customer cancel a `PENDING` order?)

### Database Concepts Introduced
- Indexing considerations for frequently filtered columns (user ID, status) — awareness now, applied in Chapter 19

### HTTP Concepts Introduced
- `403` vs `404` for "this order exists but isn't yours" vs. "this order doesn't exist" — and the debate over whether to even reveal the difference (some APIs return `404` for both, to avoid leaking existence)

### Implementation Order
**Theory Before Coding:** Since there's no admin to move orders through states, decide who/what changes `OrderStatus` — a scheduled job, a manual DB update for now, or a customer-triggered cancellation for `PENDING` orders only. Decide whether unauthorized access to someone else's order should be `403` or `404`.

**Implementation Tasks**
1. Add `OrderStatus` enum to `Order`.
2. Implement `GET /orders` for the current user, paginated.
3. Implement `GET /orders/{id}` with an ownership check (must belong to the requester).
4. Decide and implement whether customers can cancel their own `PENDING`/`CONFIRMED` orders (a reasonable customer-facing status transition without needing an admin).

### Testing Checklist (Postman)
- Customer views their own order history — correct orders returned
- Customer attempts to view another user's order by ID — rejected
- If you implemented customer-initiated cancellation, confirm it only works on eligible statuses

### Common Mistakes
- Letting any authenticated user hit `/orders/{id}` for any order ID without an ownership check
- Allowing a customer to move an order into a status they shouldn't control (e.g. marking their own order `DELIVERED`)
- Not paginating order history

### Interview Questions
- How would you design authorization so a customer can only ever see their own orders, structurally, not just by convention?
- What's the tradeoff between `403` vs `404` for a resource that exists but isn't yours?
- Without an admin to manage fulfillment, what would you use in a real system to advance order status (a message queue, a scheduled job, a webhook from a shipping provider)?

### Mini Exercises
- Draw your `OrderStatus` transition diagram, and mark which transitions a customer is allowed to trigger themselves (likely just cancellation) versus which need an external process.

### Completion Checklist
- [ ] Customers see only their own orders
- [ ] Status transitions available to customers are validated, not freeform
- [ ] You've decided and can defend the `403` vs `404` question
- [ ] You have an honest answer for how order status would advance without an admin in a real deployment

### Project Progress
**Completed:** Order history scoped to the authenticated user.
**Left:** Inventory concurrency, profile, docs, logging, testing, caching, performance, docker, deployment, frontend.
**Next chapter preview:** Checkout currently checks and decrements stock in separate steps — under concurrent load, two customers could both "successfully" buy the last unit. Chapter 12 fixes this with optimistic locking.

---

# Chapter 12 — Inventory Management & Concurrency

> **Mentor Rules for the AI in this chat (read before responding):** You are a mentor, not a code generator. Do NOT write, generate, or paste implementation code for me unless I explicitly say "write the code" or "show me the code." Explain, question, review — don't generate unless asked directly.

### Current Project State
Spring Boot e-commerce backend with full checkout, order creation, and user-scoped order history. Stock is checked and decremented during checkout, but not yet protected against concurrent requests racing on the same product's stock.

### Today's Goal
Prevent overselling under concurrent load using optimistic locking.

### Feature List
- Stock updates safe under concurrent checkout attempts
- A clean, specific error when a concurrent conflict is detected (distinct from "out of stock")

### Spring Concepts Introduced
- `@Version` for optimistic locking on `Product`
- `OptimisticLockException` and translating it into an API-friendly response
- Brief contrast with pessimistic locking

### Java Concepts Introduced
- Race conditions — precise, technical understanding
- Retry patterns (briefly)

### Database Concepts Introduced
- How `@Version` translates into a version column and a `WHERE version = ?` clause
- Isolation levels (brief conceptual overview)

### HTTP Concepts Introduced
- `409 Conflict` again, for genuine concurrent-modification, distinct from the simple stock check in Chapter 10

### Implementation Order
**Theory Before Coding:** The race condition precisely: two threads read stock=1 simultaneously, both proceed, both decrement, ending at stock=-1. Understand how `@Version` prevents this.

**Implementation Tasks**
1. Add a `@Version` field to `Product`.
2. Reproduce the race deliberately (near-simultaneous requests against stock=1) and observe the failure mode.
3. Handle `OptimisticLockException` in checkout, translating it to a clean `409`.
4. Decide on (optionally implement) a retry strategy for transient conflicts.

### Testing Checklist (Postman)
- Simulate two near-simultaneous checkouts against a product with stock=1 — only one succeeds
- The loser gets a clean `409`, not a raw exception
- Normal (non-concurrent) checkout still works

### Common Mistakes
- Believing `@Transactional` alone prevents races
- Forgetting to handle `OptimisticLockException`, letting it surface as `500`
- Confusing optimistic and pessimistic locking

### Interview Questions
- Explain precisely how a race could oversell the last unit without any locking.
- How does `@Version` prevent this at the SQL level?
- When would you choose pessimistic over optimistic locking?

### Mini Exercises
- Write out, without looking anything up, the exact `UPDATE` statement Hibernate generates for a versioned entity, and why the `WHERE` clause includes the version.

### Completion Checklist
- [ ] `@Version` is in place on `Product`
- [ ] You've personally reproduced and then fixed a real race condition
- [ ] Conflicts return a clean `409`
- [ ] You can explain optimistic vs. pessimistic locking correctly

### Project Progress
**Completed:** Concurrency-safe inventory.
**Left:** Profile, docs, logging, testing, caching, performance, docker, deployment, frontend.
**Next chapter preview:** Chapter 13 adds profile management, including partial updates via PATCH.

---

# Chapter 13 — Profile & Address Management

> **Mentor Rules for the AI in this chat (read before responding):** You are a mentor, not a code generator. Do NOT write, generate, or paste implementation code for me unless I explicitly say "write the code" or "show me the code." Explain, question, review — don't generate unless asked directly.

### Current Project State
Spring Boot e-commerce backend with full auth, cart, checkout, order history, and concurrency-safe inventory. Users have no way to view/edit their own profile, and there's no address model yet.

### Today's Goal
Add profile viewing/editing for the logged-in user, and a one-to-many address model.

### Feature List
- `GET /profile`, `PUT /profile` (full update), `PATCH /profile` (partial update)
- `Address` entity, one-to-many from `User`
- `POST/GET/PUT/DELETE /addresses`
- A "default address" concept

### Spring Concepts Introduced
- Handling `PATCH` semantics properly (no overwriting untouched fields with `null`)
- `@OneToMany` recap applied to a new relationship

### Java Concepts Introduced
- Nullable wrapper fields in a "patch DTO" to distinguish "not provided" from "explicitly null"

### Database Concepts Introduced
- Enforcing "exactly one default address" at the application level

### HTTP Concepts Introduced
- `PUT` vs `PATCH` — full replacement vs. partial update

### Implementation Order
**Theory Before Coding:** Why `PATCH` is harder than `PUT`: a `PUT` DTO can safely require all fields, but a `PATCH` DTO needs to represent "field not sent" distinctly from "field sent as null."

**Implementation Tasks**
1. Create the `Address` entity linked to `User`.
2. Implement address CRUD, scoped to the authenticated user only.
3. Implement the default-address rule (setting a new default unsets the previous, inside a transaction).
4. Implement `GET`/`PUT /profile`.
5. Implement `PATCH /profile` with genuine partial-update semantics.

### Testing Checklist (Postman)
- Add multiple addresses for one user
- Set a different default — old default is unset
- `PUT /profile` replacing the whole profile
- `PATCH /profile` updating only one field — others untouched
- A user cannot view/edit another user's addresses or profile

### Common Mistakes
- Implementing `PATCH` the same as `PUT` (accidentally nulling fields)
- Allowing more than one default address due to a non-atomic update
- Forgetting the ownership check on address endpoints

### Interview Questions
- Real semantic difference between `PUT` and `PATCH`?
- How do you represent "field not sent" vs. "field sent as null" in a partial-update DTO?
- How would you enforce "only one default address per user" against concurrent requests?

### Mini Exercises
- Implement a `DELETE` on the current default address and decide what happens to the "default" designation afterward.

### Completion Checklist
- [ ] Profile view/full-update/partial-update all work
- [ ] Address CRUD works and is scoped to the owner
- [ ] Exactly one default address is enforced at all times
- [ ] You can clearly explain `PUT` vs `PATCH`

### Project Progress
**Completed:** Profile and address management.
**Left:** Docs, logging, testing, caching, performance, docker, deployment, frontend.
**Next chapter preview:** Chapter 14 adds Swagger/OpenAPI so the entire API is self-documenting.

---

# Chapter 14 — API Documentation with Swagger/OpenAPI

> **Mentor Rules for the AI in this chat (read before responding):** You are a mentor, not a code generator. Do NOT write, generate, or paste implementation code for me unless I explicitly say "write the code" or "show me the code." Explain, question, review — don't generate unless asked directly.

### Current Project State
Spring Boot e-commerce backend with a full customer-facing feature set: catalog, cart, checkout, order history, profile. No formal API documentation exists.

### Today's Goal
Add springdoc-openapi so your API is documented and explorable via Swagger UI, with meaningful descriptions.

### Feature List
- Interactive Swagger UI at a dev endpoint
- Every endpoint documented: summary, description, request/response schemas, error responses
- Security scheme documented (how to supply the JWT in Swagger UI)

### Spring Concepts Introduced
- springdoc-openapi dependency and auto-configuration
- `@Operation`, `@ApiResponse`, `@Schema` annotations
- Documenting a bearer-token security scheme

### Java Concepts Introduced
- None major new — this is about annotating existing code thoughtfully

### Database Concepts Introduced
- None new

### HTTP Concepts Introduced
- The OpenAPI specification format — beyond Swagger UI (client SDKs, contract testing)

### Implementation Order
**Theory Before Coding:** OpenAPI is a specification; Swagger UI is one tool that renders it.

**Implementation Tasks**
1. Add the springdoc-openapi dependency.
2. Configure the security scheme so Swagger UI supports entering a JWT.
3. Add meaningful `@Operation` summaries/descriptions to every controller.
4. Document expected error responses (`400`, `401`, `403`, `404`, `409`) where relevant.

### Testing Checklist (Postman / Browser)
- Swagger UI loads and lists all endpoints, grouped sensibly
- Log in, paste the JWT into Swagger UI's auth, successfully call a protected endpoint from the docs
- Spot-check a few endpoints' documented schemas against reality

### Common Mistakes
- Leaving auto-generated generic descriptions
- Forgetting to document the security scheme
- Documenting only happy-path responses

### Interview Questions
- Relationship between the OpenAPI spec and tools like Swagger UI?
- What value does API documentation provide beyond human readability?
- How would you document a bearer-token-secured endpoint in OpenAPI?

### Mini Exercises
- Fully document one endpoint end-to-end as a model for the rest.

### Completion Checklist
- [ ] Swagger UI is live and functional
- [ ] Every endpoint has a real, specific description
- [ ] JWT auth is testable directly from Swagger UI
- [ ] Error responses are documented, not just happy paths

### Project Progress
**Completed:** Full API documentation.
**Left:** Logging, testing, caching, performance, docker, deployment, frontend.
**Next chapter preview:** Chapter 15 introduces real structured logging.

---

# Chapter 15 — Logging & Observability

> **Mentor Rules for the AI in this chat (read before responding):** You are a mentor, not a code generator. Do NOT write, generate, or paste implementation code for me unless I explicitly say "write the code" or "show me the code." Explain, question, review — don't generate unless asked directly.

### Current Project State
Spring Boot e-commerce backend with a fully documented, feature-complete customer-facing API. Logging is likely limited to default console output.

### Today's Goal
Introduce structured logging with SLF4J/Logback: meaningful log levels, contextual information.

### Feature List
- Consistent logging across all layers (controller entry, service decisions, errors)
- Correlation of logs to a specific request (MDC + a request ID)
- Appropriate log levels used correctly

### Spring Concepts Introduced
- SLF4J as the logging facade, Logback as the implementation
- `Logger` instantiation patterns
- A logging filter/interceptor injecting a request-correlation ID into MDC

### Java Concepts Introduced
- `MDC` (Mapped Diagnostic Context) and thread-local logging context

### Database Concepts Introduced
- None new

### HTTP Concepts Introduced
- Request correlation across a flow — matters even in a single-service app

### Implementation Order
**Theory Before Coding:** Standard log levels (`TRACE`, `DEBUG`, `INFO`, `WARN`, `ERROR`). Why `e.printStackTrace()`/`System.out` are a real production problem.

**Implementation Tasks**
1. Replace ad-hoc console output with SLF4J loggers.
2. Add log statements at request entry, key business decisions (checkout success/failure), and all caught exceptions.
3. Implement a filter generating/extracting a request ID into MDC.
4. Configure log output format to include the MDC request ID.

### Testing Checklist (Postman)
- A normal request produces sensible `INFO`-level logs
- A validation error logs at an appropriate level (be ready to justify)
- An unexpected server error logs the full stack trace at `ERROR`
- Two concurrent requests have distinguishable request IDs

### Common Mistakes
- Logging everything at `INFO` (or `ERROR`)
- Logging sensitive data (passwords, full JWTs)
- Using `e.printStackTrace()` instead of the logger

### Interview Questions
- Difference between SLF4J and Logback, and why does Spring Boot separate facade from implementation?
- Why does correlating logs to a request matter even in a single-instance app?
- What kinds of data should never appear in logs?

### Mini Exercises
- Deliberately cause each handled exception type and confirm each logs at a defensible level.

### Completion Checklist
- [ ] No ad-hoc console printing remains
- [ ] Log levels used deliberately and correctly
- [ ] Requests correlated via MDC
- [ ] No sensitive data appears in logs

### Project Progress
**Completed:** Structured logging and request correlation.
**Left:** Testing, caching, performance, docker, deployment, frontend.
**Next chapter preview:** Chapter 16 introduces unit testing your services in isolation.

---

# Chapter 16 — Unit Testing

> **Mentor Rules for the AI in this chat (read before responding):** You are a mentor, not a code generator. Do NOT write, generate, or paste implementation code for me unless I explicitly say "write the code" or "show me the code." Explain, question, review — don't generate unless asked directly.

### Current Project State
Spring Boot e-commerce backend, feature-complete with logging in place. No automated tests exist yet.

### Today's Goal
Write real unit tests for service layer business logic using JUnit 5 and Mockito.

### Feature List
- Unit tests for at least: product search/filter logic, checkout logic (including the rollback path), and one ownership-authorization-sensitive service method (e.g. rejecting access to another user's cart/order)
- A clear mental model of unit vs. integration testing (integration comes next chapter)

### Spring Concepts Introduced
- `@ExtendWith(MockitoExtension.class)` (no Spring context loaded)
- Mockito basics: `@Mock`, `@InjectMocks`, `when(...).thenReturn(...)`, `verify(...)`

### Java Concepts Introduced
- Dependency injection's testing payoff
- Arrange–Act–Assert (Given–When–Then)

### Database Concepts Introduced
- None — that's the point of unit tests

### HTTP Concepts Introduced
- None new — unit tests operate below the HTTP layer

### Implementation Order
**Theory Before Coding:** A unit test isolates the service's logic, with dependencies replaced by mocks.

**Implementation Tasks**
1. Set up JUnit 5 + Mockito.
2. Write unit tests for product search/filter logic, mocking the repository.
3. Write unit tests for checkout logic: successful, insufficient-stock rollback, optimistic-lock conflict — all with mocked repositories.
4. Write a unit test confirming a non-owner is correctly rejected at the service level, if that logic lives there.

### Testing Checklist (Self-review)
- Every test follows Arrange–Act–Assert clearly
- Tests cover happy paths and failure paths
- No test touches a real database or loads the Spring context
- Test names clearly describe what's being verified

### Common Mistakes
- Accidentally writing an integration test and calling it a unit test
- Over-mocking to the point the test just re-asserts mock configuration
- Not testing failure/edge-case paths

### Interview Questions
- Actual difference between a unit test and an integration test?
- Why does dependency injection make unit testing easier?
- What's the risk of over-mocking?

### Mini Exercises
- Write a unit test for a failure path not yet covered (e.g. registration with a duplicate email).

### Completion Checklist
- [ ] Core service logic has real unit test coverage
- [ ] Both happy paths and failure paths are tested
- [ ] No test touches a real database
- [ ] You can clearly distinguish unit vs. integration testing

### Project Progress
**Completed:** Unit test coverage for core business logic.
**Left:** Integration testing, caching, performance, docker, deployment, frontend.
**Next chapter preview:** Chapter 17 adds integration tests that verify the whole stack works together.

---

# Chapter 17 — Integration Testing

> **Mentor Rules for the AI in this chat (read before responding):** You are a mentor, not a code generator. Do NOT write, generate, or paste implementation code for me unless I explicitly say "write the code" or "show me the code." Explain, question, review — don't generate unless asked directly.

### Current Project State
Spring Boot e-commerce backend with solid unit test coverage on service-layer logic (mocked dependencies).

### Today's Goal
Add integration tests spinning up the real Spring context (and a real/realistic database) to verify layers work together.

### Feature List
- `@SpringBootTest` tests covering: a full checkout flow against a real (test) database, and a security test confirming an unauthenticated request is genuinely rejected end-to-end
- Testcontainers (or H2 fallback, tradeoffs explained) for a realistic test database

### Spring Concepts Introduced
- `@SpringBootTest` and what it actually loads
- `MockMvc` for testing the HTTP layer without a running server
- Testcontainers for a real, throwaway MySQL instance

### Java Concepts Introduced
- Test lifecycle annotations (`@BeforeEach`, `@AfterEach`)

### Database Concepts Introduced
- Why testing against the same DB engine as production catches bugs H2 would miss

### HTTP Concepts Introduced
- Testing the full request/response cycle via `MockMvc`

### Implementation Order
**Theory Before Coding:** What `@SpringBootTest` costs (slower, real context) vs. what it buys (integration confidence).

**Implementation Tasks**
1. Set up Testcontainers (or H2, articulating the tradeoff) for the test environment.
2. Write an integration test for the full checkout flow: register/login a real test user, add to cart, checkout, assert real DB state.
3. Write a `MockMvc` test confirming an unauthenticated request to a protected endpoint returns `401` end-to-end.
4. Ensure tests clean up after themselves.

### Testing Checklist (Self-review)
- Integration tests pass consistently on repeated runs
- The checkout test verifies real database rows, not mocked behavior
- The security test genuinely exercises the filter chain

### Common Mistakes
- Tests depending on execution order or leftover data
- Using H2 without understanding dialect divergence from MySQL
- Writing integration tests that are really over-mocked unit tests

### Interview Questions
- Why choose Testcontainers over H2?
- What does `@SpringBootTest` actually load, and why does that make tests slower but more trustworthy?
- How do you keep integration tests from becoming flaky?

### Mini Exercises
- Write an integration test for the optimistic-locking conflict path against a real database with two competing transactions.

### Completion Checklist
- [ ] Integration tests exist for checkout and security
- [ ] Tests run against a real or realistic database
- [ ] Tests are not flaky and clean up their own state
- [ ] You can explain the unit vs. integration tradeoff fluently

### Project Progress
**Completed:** Integration test coverage of critical flows.
**Left:** Caching, performance, docker, deployment, frontend.
**Next chapter preview:** Chapter 18 introduces caching to reduce database load on hot read paths.

---

# Chapter 18 — Caching

> **Mentor Rules for the AI in this chat (read before responding):** You are a mentor, not a code generator. Do NOT write, generate, or paste implementation code for me unless I explicitly say "write the code" or "show me the code." Explain, question, review — don't generate unless asked directly.

### Current Project State
Spring Boot e-commerce backend, feature-complete with unit and integration test coverage. Every product listing/search/detail request hits the database every time.

### Today's Goal
Introduce caching on hot, frequently-read, infrequently-changed data (product listings, category lists) using Spring's cache abstraction backed by Redis, and understand cache invalidation.

### Feature List
- Cached product detail lookups and category/brand listings
- Cache invalidation on product/category/brand update or delete (triggered by the same authenticated-user endpoints from Chapter 5 — no admin role needed here)
- Configurable TTL for cached entries

### Spring Concepts Introduced
- `@EnableCaching`
- `@Cacheable`, `@CacheEvict`, `@CachePut`
- Configuring Redis as the cache provider

### Java Concepts Introduced
- Cache key design for methods with multiple parameters

### Database Concepts Introduced
- None new — caching sits in front of the database to reduce load

### HTTP Concepts Introduced
- HTTP-level caching (`ETag`, `Cache-Control`) briefly, as a contrast to application-level caching

### Implementation Order
**Theory Before Coding:** "There are only two hard things in computer science: cache invalidation and naming things." Understand why: a stale cached product entry after a price update means every consumer sees stale data until eviction.

**Implementation Tasks**
1. Add Redis dependency and configure the connection (locally via Docker, foreshadowing Chapter 20).
2. Enable caching, apply `@Cacheable` to product detail lookup and category/brand listing methods.
3. Apply `@CacheEvict` on the corresponding update/delete methods.
4. Set a sensible TTL as a backstop.

### Testing Checklist (Postman)
- Fetch a product detail twice — confirm the second call is served from cache
- Update that product — confirm the next fetch reflects the update immediately
- Confirm cache entries expire after the configured TTL even without a write

### Common Mistakes
- Caching data that changes too frequently to benefit
- Forgetting `@CacheEvict` on update/delete paths, causing stale reads
- Poor cache key design causing collisions or cache misses

### Interview Questions
- Why is cache invalidation considered genuinely hard?
- Difference between `@Cacheable`, `@CachePut`, and `@CacheEvict`?
- TTL-only vs. explicit eviction, or both together?

### Mini Exercises
- Identify one more read-heavy endpoint that's a good caching candidate, and justify it.

### Completion Checklist
- [ ] Hot read paths are cached via Redis
- [ ] Writes correctly evict stale cache entries
- [ ] TTL is configured as a backstop
- [ ] You can explain cache invalidation risk clearly

### Project Progress
**Completed:** Application-level caching on hot paths.
**Left:** Performance optimization, docker, deployment, frontend.
**Next chapter preview:** Chapter 19 goes hunting for slow queries and fixes them directly.

---

# Chapter 19 — Performance Optimization

> **Mentor Rules for the AI in this chat (read before responding):** You are a mentor, not a code generator. Do NOT write, generate, or paste implementation code for me unless I explicitly say "write the code" or "show me the code." Explain, question, review — don't generate unless asked directly.

### Current Project State
Spring Boot e-commerce backend, feature-complete, tested, with caching on hot read paths. No deliberate performance profiling done yet.

### Today's Goal
Profile and optimize: find slow queries, add appropriate indexes, tune the connection pool, eliminate remaining N+1 patterns.

### Feature List
- At least one genuinely slow query identified and fixed using `EXPLAIN`
- Indexes on columns used heavily in `WHERE`/`ORDER BY`/`JOIN` (product name search, order status, user email)
- HikariCP connection pool settings reviewed and tuned deliberately

### Spring Concepts Introduced
- HikariCP configuration (`maximum-pool-size`, `connection-timeout`)
- `@EntityGraph` as an alternative N+1 fix, revisited across the app

### Java Concepts Introduced
- None major new — applying earlier concepts rigorously

### Database Concepts Introduced
- `EXPLAIN` / `EXPLAIN ANALYZE`
- Index design tradeoffs (faster reads, slower writes, more storage)

### HTTP Concepts Introduced
- None new — backend-internal work

### Implementation Order
**Theory Before Coding:** Indexing isn't free — speeds reads, costs writes. Learn to read an `EXPLAIN` plan.

**Implementation Tasks**
1. Enable SQL logging with timing, identify your slowest realistic queries under representative data volume.
2. Run `EXPLAIN` on at least one slow query and interpret it.
3. Add indexes to columns that justify them based on real query patterns.
4. Audit the app for remaining N+1 patterns, fixing with `JOIN FETCH`/`@EntityGraph`.
5. Review and adjust HikariCP pool settings with a stated rationale.

### Testing Checklist
- Before/after query timing comparison for at least one optimized query
- `EXPLAIN` output shown and interpreted, before and after indexing
- No functional regressions from your changes

### Common Mistakes
- Adding indexes everywhere "just in case"
- Optimizing a query that was never actually slow
- Setting HikariCP pool size arbitrarily large

### Interview Questions
- Walk through diagnosing a slow endpoint in a production Spring Boot app.
- What's the tradeoff of adding an index?
- Why doesn't a bigger connection pool always improve throughput?

### Mini Exercises
- Take one endpoint you haven't profiled and go through diagnose → `EXPLAIN` → fix → verify on your own.

### Completion Checklist
- [ ] At least one real slow query found, explained, and fixed with evidence
- [ ] Indexes are deliberate, not blanket
- [ ] Connection pool settings are reasoned, not default-and-forgotten
- [ ] You can walk through a full performance-diagnosis process out loud

### Project Progress
**Completed:** Deliberate performance optimization pass.
**Left:** Docker, deployment, frontend.
**Next chapter preview:** Chapter 20 containerizes the app with Docker.

---

# Chapter 20 — Dockerizing the Application

> **Mentor Rules for the AI in this chat (read before responding):** You are a mentor, not a code generator. Do NOT write, generate, or paste implementation code for me unless I explicitly say "write the code" or "show me the code." Explain, question, review — don't generate unless asked directly.

### Current Project State
Spring Boot e-commerce backend, feature-complete, tested, cached, and performance-tuned. Runs only via local IDE/Maven against a locally installed MySQL and Redis.

### Today's Goal
Containerize the application: a proper Dockerfile, and a `docker-compose.yml` bringing up app + MySQL + Redis with one command.

### Feature List
- A working multi-stage `Dockerfile` producing a lean production image
- `docker-compose.yml` orchestrating app + MySQL + Redis
- The whole stack startable with `docker compose up`

### Spring Concepts Introduced
- Externalizing configuration for containerized environments (env vars overriding `application.yml`)

### Java Concepts Introduced
- None major new — build/deployment tooling

### Database Concepts Introduced
- Running MySQL as a container with a persisted volume

### HTTP Concepts Introduced
- Container networking basics: reaching MySQL/Redis by service name, not `localhost`

### Implementation Order
**Theory Before Coding:** Why a multi-stage Dockerfile produces a smaller, more secure final image.

**Implementation Tasks**
1. Write a multi-stage `Dockerfile`: build stage compiles the jar, final stage copies it into a slim JRE base image.
2. Externalize environment-specific config (DB URL, Redis host, JWT secret) via environment variables.
3. Write `docker-compose.yml` defining app, MySQL (with a named volume), and Redis services.
4. Bring the whole stack up and verify it functions identically to your local setup.

### Testing Checklist (Postman)
- Full stack starts cleanly with `docker compose up` on a clean attempt
- Register, login, browse, add to cart, checkout — all working against the containerized stack
- Data persists across a container restart, but is genuinely gone if you remove the volume

### Common Mistakes
- Baking secrets into the image instead of passing them as env vars
- Forgetting a persistent volume for MySQL
- Using `localhost` inside the app container to reach MySQL/Redis

### Interview Questions
- Why use a multi-stage Docker build?
- How does container-to-container networking work in `docker-compose`?
- Why shouldn't secrets be baked into a Docker image?

### Mini Exercises
- Remove the MySQL volume, restart the stack, and explain exactly what data is lost and why.

### Completion Checklist
- [ ] Multi-stage Dockerfile builds a lean image
- [ ] `docker-compose.yml` brings up the full stack with one command
- [ ] Secrets are externalized
- [ ] Data persists correctly across restarts via a volume

### Project Progress
**Completed:** Fully containerized application stack.
**Left:** Production configuration/deployment, frontend.
**Next chapter preview:** Chapter 21 prepares the app for a real deployment target.

---

# Chapter 21 — Production Configuration & Deployment

> **Mentor Rules for the AI in this chat (read before responding):** You are a mentor, not a code generator. Do NOT write, generate, or paste implementation code for me unless I explicitly say "write the code" or "show me the code." Explain, question, review — don't generate unless asked directly.

### Current Project State
Spring Boot e-commerce backend, fully containerized with Docker/docker-compose.

### Today's Goal
Prepare and execute a real deployment: Spring profiles for dev/prod, production-safe configuration, a working deployment to a real hosting target.

### Feature List
- `application-dev.yml` / `application-prod.yml` with genuinely different settings (log levels, `show-sql`, error detail exposure)
- Spring Boot Actuator for health checks
- A live deployment (cloud VM, managed container platform, or PaaS)

### Spring Concepts Introduced
- Spring Profiles (`@Profile`, `spring.profiles.active`)
- Spring Boot Actuator (`/actuator/health`, awareness of what else it exposes)

### Java Concepts Introduced
- None major new — operational/configuration maturity

### Database Concepts Introduced
- Production DB configuration: pool sizing revisited, backup awareness

### HTTP Concepts Introduced
- HTTPS/TLS awareness (even if terminated by a platform/load balancer)

### Implementation Order
**Theory Before Coding:** What differs between dev and prod: verbose errors/SQL logging useful in dev, a liability in prod. Secrets never in checked-in config.

**Implementation Tasks**
1. Split configuration into dev/prod profiles with real, justified differences.
2. Ensure the global exception handler never leaks stack traces in prod.
3. Add Spring Boot Actuator, expose only what's safe, lock down the rest.
4. Choose a deployment target and deploy, wiring env vars/secrets appropriately.
5. Verify the live deployment end-to-end.

### Testing Checklist
- Prod profile genuinely hides stack traces/internal details on error
- `/actuator/health` reachable, other endpoints appropriately restricted
- Full customer journey against the actual deployed instance

### Common Mistakes
- Dev-level logging/error verbosity still active in "prod"
- Committing secrets to source control
- Leaving all Actuator endpoints wide open

### Interview Questions
- What concretely should differ between dev and prod Spring profiles?
- What is Spring Boot Actuator, and what are the security implications of exposing all its endpoints?
- Where does TLS termination typically happen in a deployed Spring Boot app's request path?

### Mini Exercises
- Write down every piece of configuration that must differ between dev and prod, then check it against what you actually implemented.

### Completion Checklist
- [ ] Dev/prod profiles have real, justified differences
- [ ] No stack traces or sensitive detail leak in prod
- [ ] Actuator is present but appropriately restricted
- [ ] The app is genuinely deployed and reachable

### Project Progress
**Completed:** Production-ready configuration and a real deployment.
**Left:** Frontend integration, final review.
**Next chapter preview:** Chapter 22 connects a simple React frontend to it.

---

# Chapter 22 — React Frontend Integration

> **Mentor Rules for the AI in this chat (read before responding):** You are a mentor, not a code generator. Do NOT write, generate, or paste implementation code for me unless I explicitly say "write the code" or "show me the code." Explain, question, review — don't generate unless asked directly.

### Current Project State
Spring Boot e-commerce backend, feature-complete, tested, containerized, and deployed. No frontend exists yet.

### Today's Goal
Build a simple React frontend that talks to your real API: browsing, login, cart, checkout — a customer-only client.

### Feature List
- Product browsing/search/pagination UI
- Login flow storing the JWT and attaching it to subsequent requests
- Cart and checkout UI
- Order history view

### Spring Concepts Introduced
- CORS configuration (`@CrossOrigin` or a global CORS config)

### Java Concepts Introduced
- None new — intentionally frontend-light on the backend side

### Database Concepts Introduced
- None new

### HTTP Concepts Introduced
- CORS in depth: preflight `OPTIONS` requests, allowed origins/methods/headers

### Implementation Order
**Theory Before Coding:** What CORS protects against, and why it's browser-enforced, not server-enforced (Postman never hit this).

**Implementation Tasks**
1. Configure CORS for your frontend's origin (not a wildcard `*` combined with credentials).
2. Scaffold a minimal React app; implement API calls for browsing, login, cart, checkout.
3. Handle JWT storage on the client (discuss `localStorage` vs. httpOnly cookie tradeoffs, pick deliberately).
4. Wire up the full customer journey against your real backend.

### Testing Checklist
- Full customer journey works through the actual UI: browse → search → add to cart → checkout → view order history
- Confirm a CORS preflight succeeds by inspecting network traffic
- An expired/invalid token correctly triggers a re-login flow, not a silent failure

### Common Mistakes
- Wildcard CORS origin with credentialed requests (browsers reject this)
- Storing the JWT in `localStorage` without understanding the XSS tradeoff
- Not handling token expiration gracefully in the UI

### Interview Questions
- What is CORS protecting against, and why is it browser-enforced?
- Security tradeoff between `localStorage` and an httpOnly cookie for a JWT?
- Walk through a CORS preflight request.

### Mini Exercises
- Deliberately misconfigure CORS, observe the browser console error, then fix it.

### Completion Checklist
- [ ] Full customer journey works end-to-end through a real UI
- [ ] CORS is correctly and deliberately configured
- [ ] You've made and can defend a JWT storage decision on the client

### Project Progress
**Completed:** End-to-end working system, backend and frontend.
**Left:** Final system design review and interview preparation.
**Next chapter preview:** Chapter 23 is a comprehensive review — defending your architecture and rehearsing interview presentation.

---

# Chapter 23 — Final System Design Review & Interview Prep

> **Mentor Rules for the AI in this chat (read before responding):** You are a mentor, not a code generator. Do NOT write, generate, or paste implementation code for me unless I explicitly say "write the code" or "show me the code." Explain, question, review — don't generate unless asked directly.

### Current Project State
A complete, production-configured, containerized, tested, documented Spring Boot e-commerce backend with a working React frontend — a customer-only system (search/filter, cart, checkout, order history, profile, no admin side).

### Today's Goal
Consolidate everything into a coherent architectural narrative for interviews: what you built, why you made each decision, what you'd do differently at scale, honest limitations — including being ready to explain the deliberate scope decision to leave out an admin/management side.

### Feature List
- No new feature — this produces an artifact of understanding: a walkthrough of the system
- A "if this had 100x the traffic" scaling discussion for the database, the cache, and checkout
- A list of known tradeoffs and honest limitations, including the admin-side scope cut

### Spring Concepts Introduced
- None new — synthesis chapter

### Java Concepts Introduced
- None new

### Database Concepts Introduced
- Scaling considerations beyond what's implemented: read replicas, sharding awareness (conceptual only)

### HTTP Concepts Introduced
- None new

### Implementation Order
**Theory Before Coding:** No coding today. The "implementation" is a clear, honest, technically precise narrative.

**Implementation Tasks**
1. Write a one-paragraph elevator pitch for the project.
2. Draw/write out the full architecture: layers, security flow, caching layer, and how a checkout request flows end-to-end.
3. List every major technical decision across all chapters and justify each in one sentence (DTOs, JWT over sessions, optimistic locking over pessimistic, Redis caching, Testcontainers over H2, and the decision to scope this as customer-only with no admin console).
4. List honest limitations: what would break first under heavy load, what you'd add given more time (rate limiting? read replicas? a message queue for order processing? refresh tokens? an admin/management layer, if a real deployment needed one?).
5. Do a mock interview pass: have your mentor grill you on the hardest questions from every previous chapter, cold.

### Testing Checklist
- Can you draw your architecture from memory?
- Can you trace a single checkout request through every layer, out loud, without missing a step?
- Can you name the most likely first bottleneck under 100x traffic, and why?
- Can you explain, clearly and without defensiveness, why this project has no admin side and what you'd add if one were needed?

### Common Mistakes
- Overclaiming scale you haven't actually tested
- Being unable to explain *why* a decision was made, only that it was made
- Treating this as a formality instead of genuinely rehearsing out loud

### Interview Questions
- Present your entire architecture in under three minutes.
- What was the single hardest bug or concept in this project, and how did you solve it?
- If you had one more month, what would you add or change, and why that over anything else?
- Where does this design intentionally cut corners appropriate for a portfolio project, and how would you close those gaps in a real production system?

### Mini Exercises
- Record yourself giving the three-minute architecture walkthrough, then listen back and tighten it.

### Completion Checklist
- [ ] You can explain the full architecture and every major decision, unaided, out loud
- [ ] You can trace a checkout request through every layer from memory
- [ ] You have an honest, specific answer for "what would break first at scale"
- [ ] You're ready to present this project as the centerpiece of your resume

### Project Progress
**Completed:** The entire roadmap. A cohesive, production-grade, customer-facing E-Commerce backend (plus a functional frontend) that you built layer by layer and can fully explain and defend.
**Left:** Nothing in this roadmap. From here: rate limiting, message queues for async order processing, read replicas, refresh tokens, an admin/management layer (if you ever want one), multi-service decomposition, or going deep on system design for the next project.
**Next chapter preview:** None — this is the final chapter.

---

## Closing Notes on How to Actually Use This

- **One chapter, one new chat.** Paste that chapter's full block as your first message. Hold the mentor AI to reviewing/questioning, not code-generating.
- **Don't skip "Theory Before Coding."** It exists so concepts are understood because the feature needs them.
- **Update "Current Project State" honestly** if you deviate from a chapter.
- **The Interview Questions are not decoration.** By Chapter 23 you should be able to answer nearly all of them across all prior chapters without hesitation.
- **If you ever do want an admin side later**, the natural place to reinsert it is right after old Chapter 8 (JWT) — add a role field and `@PreAuthorize`-gated endpoints for whatever admin actions you actually need (order status management, a dashboard), rather than bolting it on at the end.