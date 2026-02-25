FROM maven:3.8.1-openjdk-8-slim AS builder

WORKDIR /app

COPY pom.xml ./
COPY src ./src

RUN mvn -q -DskipTests clean package

FROM eclipse-temurin:8-jre-alpine

WORKDIR /app

COPY --from=builder /app/target/url-shortener-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

