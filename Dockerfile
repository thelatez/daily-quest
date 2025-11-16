# Use Eclipse Temurin JDK 21 (Rahti-compatible)
FROM eclipse-temurin:21-jdk AS builder

# Create work directory
WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven

# Copy Maven wrapper and project files
COPY mvnw .
COPY ./daily-quest/.mvn .mvn
COPY ./daily-quest/pom.xml .
COPY ./daily-quest/src src

# Build the application
RUN ./mvnw -Dmaven.test.skip=true package

# --- Runtime image ---
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy only the built jar
COPY --from=builder /app/target/*.jar app.jar

# Expose port (optional on Rahti)
EXPOSE 8080

# Run Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
