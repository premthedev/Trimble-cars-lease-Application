FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/car-lease-0.0.1-SNAPSHOT.jar /app/car-lease.jar
COPY src/main/resources/application.properties /app/application.properties

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/car-lease.jar", "--spring.config.location=file:/app/application.properties"]