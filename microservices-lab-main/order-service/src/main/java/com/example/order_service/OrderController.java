package com.example.order_service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private List<Map<String, Object>> orders = new ArrayList<>();
    private int idCounter = 1;

    private final String ITEM_SERVICE_URL = "http://item-service:8081/items";
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    public List<Map<String, Object>> getOrders() {
        return orders;
    }

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody Map<String, Object> order) {
        String itemName = (String) order.get("item");

        // Fetch items from Item Service
        List<Map> items = Arrays.asList(restTemplate.getForObject(ITEM_SERVICE_URL, Map[].class));
        boolean exists = items.stream().anyMatch(i -> itemName.equals(i.get("name")));

        if (!exists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Item '" + itemName + "' does not exist"));
        }

        order.put("id", idCounter++);
        order.put("status", "PENDING");
        orders.add(new HashMap<>(order));
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getOrder(@PathVariable int id) {
        return orders.stream()
                .filter(o -> o.get("id").equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}