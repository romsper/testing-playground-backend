FROM gradle:8-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle build --no-daemon -x test

FROM amazoncorretto:17.0.15-al2023
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 1111
ENTRYPOINT ["java", "-jar", "app.jar"]