FROM openjdk:17-oracle
LABEL authors="igles"
COPY target/dashboard-0.0.1-SNAPSHOT.jar dashboard-app.jar

ENTRYPOINT ["java", "-jar", "dashboard-app.jar"]