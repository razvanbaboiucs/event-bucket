FROM openjdk:23-jdk
WORKDIR /app
COPY target/eureka-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8761
CMD ["java", "-jar", "app.jar"]