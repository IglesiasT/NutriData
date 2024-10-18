FROM openjdk:17-oracle
WORKDIR /app
COPY target/meal-plan-0.0.1-SNAPSHOT.jar /app/meal-plan-app.jar

ENTRYPOINT ["java", "-jar", "meal-plan-app.jar"]