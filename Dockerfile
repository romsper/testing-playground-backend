FROM amazoncorretto:17.0.15-al2023

WORKDIR /app

COPY build/libs/qa-backend-playground-all.jar app.jar

EXPOSE 1111

ENTRYPOINT ["java", "-jar", "app.jar"]