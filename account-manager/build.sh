mvn clean package -DskipTests
podman build . --tag account-manager:latest