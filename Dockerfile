FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/juxBar-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/app.jar", "--debug"]