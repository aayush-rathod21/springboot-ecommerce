# The Spring Boot E-Commerce Roadmap (Resume-Worthy Intermediate Edition)

**Project Scope Update:** This roadmap has been strategically scoped to act as a strong portfolio piece for job interviews. It focuses on core e-commerce mechanics and essential industry standards (Security, Testing, Documentation, Docker) while intentionally omitting overly complex enterprise patterns (DTOs, Caching/Redis, Optimistic Locking, strict Relational Mappings) to maintain development velocity. 

---

## Master Chapter Index

| # | Chapter | Core Feature Added | Status |
|---|---------|--------------------|--------|
| 1 | Product Search & Filtering | Basic string-based search | **Completed** |
| 2 | Pagination & Sorting | Paged product listing | **Completed** |
| 3 | User Registration & Passwords | Real user accounts with BCrypt | Up Next |
| 4 | Spring Security & JWT | Stateless login and endpoint protection | |
| 5 | Persistent Shopping Cart | Cart tied to logged-in user | |
| 6 | Checkout & Order Creation | Converting a cart into an Order | |
| 7 | Order History | Viewing past orders | |
| 8 | Automated Testing | Unit & Integration testing for core logic | |
| 9 | API Documentation (Swagger) | Self-documenting API interface | |
| 10 | Basic Dockerization | Packaging the app in a container | |
| 11 | React Frontend Integration | Connecting a simple UI to the backend | |

---

# Chapter 1 — Product Search & Filtering
*(Completed)*
- Built flexible searching using Strings for category and brand.

# Chapter 2 — Pagination & Sorting
*(Completed)*
- Implemented `Pageable` to return data in manageable chunks.
- Note: Global Exception Handling for 'Not Found' was also established here.

# Chapter 3 — User Registration & Password Security
**Current Goal:** Build a `User` entity and a registration endpoint that stores passwords securely using BCrypt.
- `User` entity (email, password, role).
- `POST /auth/register`.
- Duplicate email prevention.

# Chapter 4 — Spring Security & JWT
**Current Goal:** Lock down the application so only logged-in users can access their carts and orders.
- Implement stateless login (`POST /auth/login`).
- Generate and validate JSON Web Tokens (JWT).

# Chapter 5 — Persistent Shopping Cart
**Current Goal:** Rebuild the cart so it is permanently saved in the database and tied to a specific logged-in user account.
- Tie cart items to the user's username/ID.
- Endpoints to add, remove, and view the cart.

# Chapter 6 — Checkout & Order Creation
**Current Goal:** Convert the cart into a permanent Order.
- Create `Order` and `OrderItem` entities.
- Clear the cart upon successful order creation.
- Basic stock quantity decrementing.

# Chapter 7 — Order History
**Current Goal:** Allow a user to see what they have purchased.
- `GET /orders` to return a list of past orders for the logged-in user.

# Chapter 8 — Automated Testing
**Current Goal:** Prove your code works and show interviewers you understand code quality.
- Write Unit Tests using JUnit and Mockito for the checkout logic.
- Write a basic Integration Test.

# Chapter 9 — API Documentation (Swagger/OpenAPI)
**Current Goal:** Generate a professional UI for your API.
- Add the `springdoc-openapi` dependency.
- View the auto-generated Swagger UI.

# Chapter 10 — Basic Dockerization
**Current Goal:** Make your app runnable anywhere to demonstrate DevOps awareness.
- Write a simple `Dockerfile` for the Spring Boot application.

# Chapter 11 — React Frontend Integration
**Current Goal:** Build a simple UI to consume the API.
- Basic UI for searching products.
- Login screen to get the JWT.
- Simple cart and checkout button.