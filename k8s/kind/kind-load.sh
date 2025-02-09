docker pull mysql:8.0
docker pull mysql:8.0
docker pull quay.io/keycloak/keycloak:24.0.1
docker pull alexpoty/estatein-booking-service
docker pull alexpoty/estatein-property-service
docker pull alexpoty/estatein-api-gateway
docker pull alexpoty/estatein-frontend

kind load docker-image -n estatein mysql:8.0
kind load docker-image -n estatein mysql:8.0
kind load docker-image -n estatein quay.io/keycloak/keycloak:24.0.1
kind load docker-image -n estatein alexpoty/estatein-booking-service:latest
kind load docker-image -n estatein alexpoty/estatein-property-service:latest
kind load docker-image -n estatein alexpoty/estatein-api-gateway:latest
kind load docker-image -n estatein alexpoty/estatein-frontend:latest
