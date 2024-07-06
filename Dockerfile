# Build stage
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install 
#-DskipTests

FROM openjdk:17-jdk-alpine
WORKDIR /home/project
COPY --from=build /app/target/its-service.jar .
CMD ["java", "-jar", "./its-service.jar","-Dspring.profiles.active=dev"]