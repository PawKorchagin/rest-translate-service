# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests  # Skip tests in the build phase

# Stage 2: Run tests with the database up
FROM maven:3.8.5-openjdk-17 AS tester
WORKDIR /app

COPY --from=builder /app /app

# Copy docker-compose.yaml to run the DB service
COPY docker-compose.yaml /app/docker-compose.yaml

# Install Docker Compose and other required dependencies if needed
RUN apt-get update && apt-get install -y docker-compose

# Ensure Docker is running the database container
CMD ["sh", "-c", "docker-compose up -d db && mvn test"]

# Stage 3: Create the final image to run the application
FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
