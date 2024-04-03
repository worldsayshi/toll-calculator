# Stage 1: Build and Test
FROM openjdk:21-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN ./gradlew test
RUN ./gradlew clean build

# Stage 2: Copy the built jar and run it
FROM openjdk:21-jdk-alpine
COPY --from=build /app/build/libs/tollCalculator-0.0.1-SNAPSHOT.jar tollCalculator.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/tollCalculator.jar"]