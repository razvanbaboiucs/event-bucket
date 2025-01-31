mvn clean package -DskipTests
podman build . --tag eureka-server:latest