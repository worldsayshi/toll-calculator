FROM openjdk:21-jdk
WORKDIR /app
COPY ./build/libs/tollCalculator.jar tollCalculator.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/tollCalculator.jar"]