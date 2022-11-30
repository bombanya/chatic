FROM eclipse-temurin:17-jre-alpine
WORKDIR /config-server
ADD target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]