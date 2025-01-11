FROM maven:3.9.4-eclipse-temurin-17 as builder

WORKDIR /app

COPY pom.xml ./pom.xml
COPY src ./src

RUN mvn clean package

FROM openjdk:17-oracle

WORKDIR /app

COPY --from=builder /app/target/gateway-0.0.1-SNAPSHOT.jar /app/gateway.jar

ENTRYPOINT ["java", "-jar", "gateway.jar"]
