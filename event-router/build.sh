mvn clean package -DskipTests
podman build . --tag event-router:latest