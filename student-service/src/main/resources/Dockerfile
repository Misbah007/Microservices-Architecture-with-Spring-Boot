FROM openjdk:17-alpine AS build
# Install Maven
RUN apk add --no-cache maven
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Second stage: Create a slim runtime image
FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/student-service-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]