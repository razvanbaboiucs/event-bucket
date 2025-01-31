FROM openjdk:23-jdk
WORKDIR /app
COPY target/cloud-gateway-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8060
CMD ["java", "-jar", "app.jar"]