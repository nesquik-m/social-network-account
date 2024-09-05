FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/social-network-account-0.0.1-SNAPSHOT.jar app.jar
#EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]