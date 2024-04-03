FROM openjdk:21-jdk-alpine
WORKDIR /app
COPY /app/build/libs/tollCalculator-0.0.1-SNAPSHOT.jar tollCalculator.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/tollCalculator.jar"]