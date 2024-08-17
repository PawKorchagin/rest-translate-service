FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests  # Skip tests in the build phase

FROM maven:3.8.5-openjdk-17 AS tester
WORKDIR /app

COPY --from=builder /app /app

COPY compose.yaml /app/docker-compose.yaml

RUN apt-get update && apt-get install -y docker-compose

CMD ["sh", "-c", "docker-compose up -d db && mvn test"]

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
