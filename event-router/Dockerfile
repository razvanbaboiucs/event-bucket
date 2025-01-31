FROM openjdk:23-jdk
WORKDIR /app
COPY target/event-router-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
CMD ["java", "-jar", "app.jar"]