FROM openjdk:17-jdk-slim
ADD . /app
WORKDIR /app


COPY target/juxBar-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "-Dspring.profiles.active=docker", "juxBar-0.0.1-SNAPSHOT.jar"]