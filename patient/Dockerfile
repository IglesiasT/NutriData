FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 3200
# Adjust memory settings as needed in AWS
ENTRYPOINT ["java", "-Xms256m", "-Xmx512m", "-jar", "app.jar"]
