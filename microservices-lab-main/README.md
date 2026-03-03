# Microservices Lab — SE4010 / CTSE Lab 05

Four Spring Boot services wired through a Spring Cloud Gateway, Dockerized and running on separate ports.

---

## 🖥 Services & Ports

| Service        | Port | Description                      |
|----------------|------|----------------------------------|
| API Gateway    | 8080 | Routes requests to all services  |
| Item Service   | 8081 | CRUD for items                   |
| Order Service  | 8082 | Place and view orders            |
| Payment Service| 8083 | Process and view payments        |

---

## ⚡ Run Instructions

### Build & Run Everything

```bash
# 1. Build all JARs (skip tests)
(cd item_service && ./mvnw package -DskipTests)
(cd order_service && ./mvnw package -DskipTests)
(cd payment_service && ./mvnw package -DskipTests)
(cd api_gateway && ./mvnw package -DskipTests)

# 2. Start all services with Docker Compose
docker-compose up --build

# Run in background (detached mode)
docker-compose up -d

# 3. Check running containers
docker ps

# 4. View logs for a specific service
docker-compose logs item-service

# 5. Stop and remove all containers
docker-compose down

🔗 API Endpoints (via API Gateway: http://localhost:8080)

| Method | Path              | Description                      |
| ------ | ----------------- | -------------------------------- |
| GET    | /items            | List all items                   |
| POST   | /items            | Add a new item (plain text body) |
| GET    | /items/{id}       | Get item by index                |
| GET    | /orders           | List all orders                  |
| POST   | /orders           | Place an order                   |
| GET    | /orders/{id}      | Get order by ID                  |
| GET    | /payments         | List all payments                |
| POST   | /payments/process | Process a payment                |
| GET    | /payments/{id}    | Get payment status by ID         |


🧪 Testing

1.Import microservices-lab.postman_collection.json into Postman.

2.Make sure Docker containers are running.

3.Test all endpoints via the API Gateway (localhost:8080), not individual service ports.


📂 Repository Structure

/microservices-lab
│
├─ item_service/
├─ order_service/
├─ payment_service/
├─ api_gateway/
├─ docker-compose.yml
├─ microservices-lab.postman_collection.json
└─ README.md


