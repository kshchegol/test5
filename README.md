# Getting Started

### Documentation
http://localhost:8080/swagger-ui/index.html

### Builde and start

./mvnw clean package -DskipTests

docker build -f Dockerfile  -t test5 .

docker-compose up

open in browser http://localhost:8080/swagger-ui/index.html
