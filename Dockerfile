FROM maven:3.8.5-openjdk-17
COPY pom.xml .
COPY src .
RUN mvn clean package
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]