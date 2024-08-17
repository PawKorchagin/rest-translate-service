FROM python:3.13.0rc1-bookworm AS iam-fetcher
WORKDIR /app

COPY fetch_iam_token.py /app/
RUN pip install requests

CMD ["python", "fetch_iam_token.py"]

FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

FROM maven:3.8.5-openjdk-17-slim AS tester
WORKDIR /app

RUN apt-get update && apt-get install -y docker-compose

COPY --from=builder /app /app

COPY compose.yaml /app/docker-compose.yaml

CMD ["sh", "-c", "docker-compose up -d db && mvn test"]

FROM openjdk:17-jdk-slim AS final
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

ENTRYPOINT ["sh", "-c", "export YANDEX_IAM_TOKEN=$(cat /app/iam_token/token.txt) && java -jar app.jar"]

