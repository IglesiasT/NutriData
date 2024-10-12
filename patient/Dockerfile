FROM openjdk:17-oracle
WORKDIR /app
COPY target/patient-0.0.1-SNAPSHOT.jar /app/patient-app.jar

ENTRYPOINT ["java", "-jar", "patient-app.jar"]