FROM openjdk:23-jdk
WORKDIR /app
COPY target/account-manager-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
CMD ["java", "-jar", "app.jar"]