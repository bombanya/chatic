FROM eclipse-temurin:17-jre-alpine
WORKDIR /message-service
ADD target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]