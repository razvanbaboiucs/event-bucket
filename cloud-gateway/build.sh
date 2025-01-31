mvn clean package -DskipTests
podman build . --tag cloud-gateway-server:latest