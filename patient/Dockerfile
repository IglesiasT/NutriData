FROM maven:3.9.4-eclipse-temurin-17 as builder

WORKDIR /app

# Copy child pom.xml
COPY pom.xml ./pom.xml
COPY src ./src

RUN mvn clean package

FROM openjdk:17-oracle

WORKDIR /app

COPY --from=builder /app/target/patient-0.0.1-SNAPSHOT.jar /app/patient-app.jar

ENTRYPOINT ["java", "-jar", "patient-app.jar"]
