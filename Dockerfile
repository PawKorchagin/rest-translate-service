# Stage 1: Build the application
FROM openjdk:17-jdk-alpine AS build
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code and package the application
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM openjdk:17-jdk-alpine
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/translate-0.0.1-SNAPSHOT.jar ./translate.jar

# Run the JAR file
ENTRYPOINT ["java","-jar","./translate.jar"]
