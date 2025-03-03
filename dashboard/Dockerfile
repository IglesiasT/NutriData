FROM maven:3.9.4-eclipse-temurin-17 as builder

WORKDIR /app

COPY pom.xml ./pom.xml
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-oracle

WORKDIR /app

COPY --from=builder /app/target/dashboard-0.0.1-SNAPSHOT.jar /app/dashboard-app.jar

ENTRYPOINT ["java", "-jar", "dashboard-app.jar"]
