mvn clean package -DskipTests
podman build . --tag event-manager:latest