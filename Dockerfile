# Stage 1: Fetch IAM Token using Python
FROM python:3.13.0rc1-bookworm AS iam-fetcher
WORKDIR /app

# Install necessary packages and copy the script
COPY fetch_iam_token.py /app/
RUN pip install requests

# Execute the Python script and store the IAM token
CMD ["python", "fetch_iam_token.py"]

# Stage 2: Build the Java application
FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests  # Skip tests during build

# Stage 3: Final image
FROM openjdk:17-jdk-slim AS final
WORKDIR /app

# Copy the built Java application from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Set environment variables for IAM token (assuming you're setting this dynamically via Docker Compose)
ENV YANDEX_IAM_TOKEN=${YANDEX_IAM_TOKEN}

# Run the Java application
ENTRYPOINT ["java", "-jar", "app.jar"]
