FROM eclipse-temurin:21-jdk-alpine AS build

RUN apk add --no-cache maven

WORKDIR /app

COPY pom.xml .
COPY ./src/main/resources/keystore.p12 /app/keystore.p12

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

COPY --from=build /app/target/juxBar-0.0.1-SNAPSHOT.jar /app.jar
COPY ./src/main/resources/keystore.p12 /app/src/main/resources/keystore.p12

ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/app.jar"]
