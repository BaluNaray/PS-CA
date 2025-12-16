package com.example.order.controller;

import com.example.order.dto.OrderEvent;
import com.example.order.service.OrderProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderProcessingService orderProcessingService;

    @PostMapping("/{orderId}/process")
    public ResponseEntity<String> processOrder(@PathVariable String orderId) {
        log.info("OrderController::processOrder START with orderId: {}", orderId);
        OrderEvent event = new OrderEvent();
        event.setOrderId(orderId);
        orderProcessingService.processOrder(event);
        log.info("OrderController::processOrder END");
        return ResponseEntity.ok("Order processing initiated for orderId: " + orderId);
    }
}
