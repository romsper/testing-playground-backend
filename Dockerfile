FROM amazoncorretto:17.0.15-al2023

WORKDIR /app

COPY build/libs/testing-playground-backend-all.jar app.jar

EXPOSE 1111

ENTRYPOINT ["java", "-jar", "app.jar"]