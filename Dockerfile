# Use Maven base image to build the application
FROM maven:3.9.9-eclipse-temurin-23 AS build
WORKDIR /app

# Copy the pom.xml and download dependencies (for caching purposes)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the rest of the application source code
COPY src ./src

# Build the application (creates JAR file in the "target" directory)
RUN mvn clean package -DskipTests

# Use a lightweight JDK image to run the application
FROM openjdk:23-jdk-slim
WORKDIR /app

# Copy only the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

ENV DB_HOST=host.docker.internal
ENV DB_PORT=5432
ENV DB_DATABASE=task_management
ENV DB_USERNAME=postgres
ENV DB_PASSWORD=1234
ENV DB_SCHEMA=public

ENV APP_ENV=localhost
ENV APP_PORT=4000
ENV ALLOW_ORIGINS=http://localhost:3000

# Expose the port Spring Boot application will run on
EXPOSE 4000

# Command to run the application
CMD ["java", "-jar", "app.jar"]