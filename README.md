# QA API Playground - Backend Service

## Telegram (RU)
This [romsper | QA Buddy](https://t.me/qa_api_playground) is my personal Telegram channel where I share my experience and knowledge about testing. I will be glad to see you there!

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
### *nix
1. Install a fresh instance of `Docker Desktop`.
2. Download the `JDK 17 Corretto` installer from the Amazon website.
3. Install/Update `IntelliJ IDEA Community Edition` to the latest version.
4. Open the `IDEA` project (check in the settings that `Gradle` is using the correct `JDK` version).
5. IDEA automatically downloads all `dependencies`.
6. Run the `Terminal` and type `./gradlew run` to run the project.
7. Run `./gradlew composeDown` to stop the Postgres container and `DROP` the database.

### Windows
1. Install a fresh instance of `Docker Desktop`.
2. The Docker installer offered to install WSL and run through it instead of Hyper-V (a pleasant surprise ❤️).
3. Install/Update `IntelliJ IDEA Community Edition` to the latest version.
4. Download the `JDK 17 Corretto` installer from the Amazon website.
5. Verify the system and user `PATH` variables (one was incorrect, so I updated it manually).
6. Reboot the computer to apply the new `PATH`.
7. Open the `IDEA` project (check in the settings that `Gradle` is using the correct `JDK` version).
8. IDEA automatically downloads all `dependencies`.
9. Open a new type of `Terminal` in IDEA via right-clicking on the terminal tab (see screenshot).
(This terminal is some kind of illegitimate child of Linux and Windows...)
10. Enter the command `.\gradlew.bat run`.
11. Run `.\gradlew.bat composeDown` to stop the `Postgres` container and `DROP` the database.

## Logs
Logs are stored in the `logs` directory and cleaned up automatically before each run.

## License
This project is licensed under the MIT License.
