package com.example.payment_service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private List<Map<String, Object>> payments = new ArrayList<>();
    private int idCounter = 1;

    private final String ORDER_SERVICE_URL = "http://order-service:8082/orders";
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    public List<Map<String, Object>> getPayments() {
        return payments;
    }

    @PostMapping("/process")
    public ResponseEntity<?> processPayment(@RequestBody Map<String, Object> payment) {
        Integer orderId = (Integer) payment.get("orderId");

        // Fetch orders from Order Service
        List<Map> orders = Arrays.asList(restTemplate.getForObject(ORDER_SERVICE_URL, Map[].class));
        boolean orderExists = orders.stream().anyMatch(o -> orderId.equals(o.get("id")));

        if (!orderExists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Order with id " + orderId + " does not exist"));
        }

        payment.put("id", idCounter++);
        payment.put("status", "SUCCESS");
        payments.add(new HashMap<>(payment));
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getPayment(@PathVariable int id) {
        return payments.stream()
                .filter(p -> p.get("id").equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}