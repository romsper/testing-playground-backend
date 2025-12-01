# API Playground - Backend Service for Testing purposes
[![](https://img.shields.io/badge/-LinkedIn-blue?style=flat&logo=Linkedin&logoColor=white)](https://www.linkedin.com/in/romsper/) [![](https://img.shields.io/static/v1?label=Telegram&message=%23&logo=Telegram&color=%23fe8e86)](http://t.me/romsper_qa_buddy) ![](https://komarev.com/ghpvc/?username=romsper) 

## Description
This project is a backend service for managing users, products, and orders. It is built using Kotlin and Ktor, and uses Gradle for build automation.
Feel free to use this project as a playground to practice your API automation testing skills.
Moreover, you can connect this project with the [testing-playground-frontend](https://github.com/romsper/testing-playground-frontend) project to create a full-stack application for testing purposes.

## Technologies Used
- Kotlin
- Exposed
- Gradle
- JDK 17 (`JAVA_HOME` environment variable must be set to `PATH`)
- Docker or Docker Desktop (required for running `Postgres` database)

## API Documentation
## Swagger
Copy/Paste `openapi.yaml` to [Swagger Editor Online](https://editor.swagger.io/) to view the API Documentation.
Or you can use the [OpenAPI Specifications](https://plugins.jetbrains.com/plugin/14394-openapi-specifications) plugin to view the API documentation in IntelliJ IDEA.

## Postman
Feel free to use the `Playground` [Postman Collection](http://postman.com/romsper-public/romsper-public) to test the API. You can import the collection into Postman and run the requests.
Or you can import the `openapi.yaml` file into `Postman` to view the API documentation and test the endpoints.


## How to Run using Docker
### Docker Compose FULL
This project uses `Docker Compose` to run the `Postgres` database and the `App`. The `docker-compose.yml` file is located in the root directory of the project.
1. Make sure you have `Docker` or `Docker Desktop` installed.
2. Open a `Terminal` and navigate to the root directory of the project.
3. Run the `Terminal` and type this command `docker compose up -d` to build and start the containers.
   (`-d` option is not required, but it is recommended to run the containers in detached mode.)
4. Wait for the containers to start. You can check the logs in the terminal.

### Docker Compose DATABASE
This project uses `Docker Compose` to run the `Postgres` database **ONLY**. The `docker-compose.yml` file is located in the root directory of the project.
1. Make sure you have `Docker` or `Docker Desktop` installed.
2. Open a `Terminal` and navigate to the root directory of the project.
3. Run the `Terminal` and type this command `docker compose -f docker-compose-database.yml up -d` to build and start the `Postgres` container.
   (`-d` option is not required, but it is recommended to run the container in detached mode.)

## How to Run using IntelliJ IDEA
### *nix
This project has been tested on `Linux` and `MacOS` operating systems. The steps below should work on both, but there may be some differences in the installation process.
1. Install a fresh instance of `Docker Desktop`.
2. Download the `JDK 17 Corretto` installer from the Amazon website.
3. Install/Update `IntelliJ IDEA Community Edition` to the latest version.
4. Open the `IDEA` project (check in the settings that `Gradle` is using the correct `JDK` version).
5. IDEA automatically downloads all `dependencies`.
6. Follow the **Docker Compose DATABASE** section to run the `Postgres` database.
7. Go to the `src/main/kotlin` directory and open the `Application.kt` file.
8. Right-click on the `main` function and select `Run 'ApplicationKt'` or use the `play` button in the top right corner of the IDE.

### Windows
This project has been tested on `Windows` operating system. The steps below should work on Windows, but there may be some differences in the installation process.
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
10. Follow the **Docker Compose DATABASE** section to run the `Postgres` database.
11. Go to the `src/main/kotlin` directory and open the `Application.kt` file.
12. Right-click on the `main` function and select `Run 'ApplicationKt'` or use the `play` button in the top right corner of the IDE.

### Configuration
The application configuration is located in the `src/main/resources/application.conf` file. You can change the database connection settings, server port, and other parameters there.

## Logs
Logs are written to the `logs` directory in the root of the project. You can change the logging configuration in the `src/main/resources/logback.xml` file.

## Telegram (RU)
This [romsper | QA Buddy](https://t.me/romsper_qa_buddy) is my personal Telegram channel where I share my experience and knowledge about testing. I will be glad to see you there!

## License
This project is licensed under the MIT License.
