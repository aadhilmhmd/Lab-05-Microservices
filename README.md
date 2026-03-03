---

# Microservices Lab — SE4010 / CTSE Lab 05

This lab consists of four Spring Boot microservices connected through a Spring Cloud Gateway. All services are Dockerized and configured to run on different ports.

---

## 🖥 Services & Ports

| Service         | Port | Description                                    |
| --------------- | ---- | ---------------------------------------------- |
| API Gateway     | 8080 | Handles routing to all backend services        |
| Item Service    | 8081 | Provides CRUD operations for items             |
| Order Service   | 8082 | Manages order placement and retrieval          |
| Payment Service | 8083 | Handles payment processing and status checking |

---

## ⚡ Running the Application

### Build and Start All Services

### 1️⃣ Build JAR files (skip tests)

```bash
(cd item_service && ./mvnw package -DskipTests)
(cd order_service && ./mvnw package -DskipTests)
(cd payment_service && ./mvnw package -DskipTests)
(cd api_gateway && ./mvnw package -DskipTests)
```

### 2️⃣ Launch services using Docker Compose

```bash
docker-compose up --build
```

Run in detached mode (background):

```bash
docker-compose up -d
```

### 3️⃣ Check active containers

```bash
docker ps
```

### 4️⃣ View logs of a specific service

```bash
docker-compose logs item-service
```

### 5️⃣ Stop and remove all containers

```bash
docker-compose down
```

---

## 🔗 API Endpoints

(All requests must go through the API Gateway: `http://localhost:8080`)

| Method | Endpoint          | Description                         |
| ------ | ----------------- | ----------------------------------- |
| GET    | /items            | Retrieve all items                  |
| POST   | /items            | Create a new item (plain text body) |
| GET    | /items/{id}       | Retrieve an item by index           |
| GET    | /orders           | Retrieve all orders                 |
| POST   | /orders           | Create/place a new order            |
| GET    | /orders/{id}      | Retrieve order by ID                |
| GET    | /payments         | Retrieve all payments               |
| POST   | /payments/process | Process a payment                   |
| GET    | /payments/{id}    | Retrieve payment status by ID       |

---

## 🧪 Testing Instructions

1. Import `microservices-lab.postman_collection.json` into Postman.
2. Ensure all Docker containers are running.
3. Test every endpoint using the API Gateway (`localhost:8080`) — do not access individual service ports directly.

---

## 📂 Project Structure

```
/microservices-lab
│
├─ item_service/
├─ order_service/
├─ payment_service/
├─ api_gateway/
├─ docker-compose.yml
├─ microservices-lab.postman_collection.json
└─ README.md
```

---
