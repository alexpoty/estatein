docker pull mysql:8.0
docker pull mysql:8.0
docker pull quay.io/keycloak/keycloak:24.0.1
docker pull alexpoty/estatein-booking-service
docker pull alexpoty/estatein-property-service
docker pull alexpoty/estatein-api-gateway
docker pull alexpoty/estatein-frontend
docker pull alexpoty/estatein-image-service
docker pull alexpoty/estatein-notification-service
docker pull confluentinc/cp-zookeeper:7.5.0
docker pull confluentinc/cp-kafka:7.5.0
docker pull provectuslabs/kafka-ui:latest
docker pull grafana/loki
docker pull prom/prometheus:v2.46.0
docker pull grafana/tempo:2.2.2
docker pull grafana/grafana:10.1.0

kind load docker-image -n estatein mysql:8.0
kind load docker-image -n estatein mysql:8.0
kind load docker-image -n estatein quay.io/keycloak/keycloak:24.0.1
kind load docker-image -n estatein alexpoty/estatein-booking-service:latest
kind load docker-image -n estatein alexpoty/estatein-property-service:latest
kind load docker-image -n estatein alexpoty/estatein-api-gateway:latest
kind load docker-image -n estatein alexpoty/estatein-frontend:latest
kind load docker-image -n estatein alexpoty/estatein-image-service:latest
kind load docker-image -n estatein alexpoty/estatein-image-service:latest
kind load docker-image -n estatein alexpoty/estatein-notification-service:latest
kind load docker-image -n estatein confluentinc/cp-zookeeper:7.5.0
kind load docker-image -n estatein confluentinc/cp-kafka:7.5.0
kind load docker-image -n estatein provectuslabs/kafka-ui:latest
kind load docker-image -n estatein grafana/loki
kind load docker-image -n estatein prom/prometheus:v2.46.0
kind load docker-image -n estatein grafana/tempo:2.2.2
kind load docker-image -n estatein grafana/grafana:10.1.0

