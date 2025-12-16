package com.example.order.listener;

import com.example.order.dto.OrderEvent;
import com.example.order.service.OrderProcessingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventListener {

    private final OrderProcessingService highlightService; // Renamed to ensure uniqueness in context? No, sticking to
                                                           // naming.
    private final OrderProcessingService processingService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "order-created", groupId = "order-service-group")
    public void listen(String message, Acknowledgment acknowledgment) {
        log.info("OrderEventListener::listen START with message: {}", message);
        try {
            OrderEvent event = objectMapper.readValue(message, OrderEvent.class);

            // 1. Persist Event (Store)
            processingService.processEvent(event);

            // 2. Acknowledge (Commit Offset)
            acknowledgment.acknowledge();
            log.info("Acknowledged message for orderId: {}", event.getOrderId());

            // 3. Process Order (Orchestrate)
            processingService.processOrder(event);
            log.info("OrderEventListener::listen END");

        } catch (Exception e) {
            log.error("Error processing Kafka message", e);
            // Decide whether to ack or not ack.
            // If we fail to parse, we should probably ack and log to dead letter,
            // otherwise we stuck in infinite loop.
            // For this exercise, we capture error and ack to move on,
            // reliability here means we don't lose it before persisting.
            acknowledgment.acknowledge();
            log.info("OrderEventListener::listen END (with error)");
        }
    }
}
