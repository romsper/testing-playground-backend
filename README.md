# QA API Playground - Backend Service

## Description
This project is a backend service for managing users, products, and orders. It is built using Kotlin and Ktor, and uses Gradle for build automation.
Feel free to use this project as a playground to practice your API automation testing skills.

## Technologies Used
- Kotlin
- Gradle
- JDK 17 (`JAVA_HOME` environment variable must be set to `PATH`)
- Docker or Docker Desktop (required for running `Postgres` database)

## API Endpoints
### Auth Endpoints
- `POST /auth/login` - Authenticates a user using their email and password.
- `POST /auth/refresh` - Refreshes the JWT token using a refresh token.
- `GET /auth/validate` - Validates the current JWT token.

### User Endpoints
- `GET /api/v1/users/me` - Retrieve the current user's information.
- `GET /api/v1/users/{id}` - Retrieve a user's information by ID.
- `PUT /api/v1/users/{id}` - Update a user's information by ID.
- `DELETE /api/v1/users/{id}` - Delete a user by ID.

### Product Endpoints
- `GET /api/v1/products` - Retrieve a list of products.
- `GET /api/v1/products/{id}` - Retrieve a product's information by ID.
- `POST /api/v1/products` - Create a new product.
- `PUT /api/v1/products/{id}` - Update a product's information by ID.
- `DELETE /api/v1/products/{id}` - Delete a product by ID.

### Order Endpoints
- `GET /api/v1/orders` - Retrieve a list of orders.
- `GET /api/v1/orders/{id}` - Retrieve an order's information by ID.
- `POST /api/v1/orders` - Create a new order.
- `PUT /api/v1/orders/{id}` - Update an order's information by ID.
- `DELETE /api/v1/orders/{id}` - Delete an order by ID.

### Documentation
Copy/Paste `openapi.yaml` to [Swagger Editor Online](https://editor.swagger.io/) to view the API documentation.
Or you can use the [OpenAPI Specifications](https://plugins.jetbrains.com/plugin/14394-openapi-specifications) plugin to view the API documentation in IntelliJ IDEA.

## How to Run
1. Clone the repository.
2. Navigate to the project directory.
3. Run `./gradlew run` to run the project.
4. Run `./gradlew composeDown` to stop Postgres container and DROP database.

## Logs
Logs are stored in the `logs` directory and cleaned up automatically before each run.

## License
This project is licensed under the MIT License.