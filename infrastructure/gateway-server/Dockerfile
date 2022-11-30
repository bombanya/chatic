FROM eclipse-temurin:17-jre-alpine
WORKDIR /gateway
ADD target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]