FROM eclipse-temurin:8-jdk-alpine

WORKDIR /app

COPY pom.xml mvnw* ./
COPY .mvn .mvn
COPY src ./src

RUN ./mvnw -q -DskipTests package || mvn -q -DskipTests package

EXPOSE 8080

ENTRYPOINT ["java","-jar","target/url-shortener-0.0.1-SNAPSHOT.jar"]

