package com.example.order.service;

import com.example.order.dto.OrderEvent;
import com.example.order.dto.ProcessedOrderEvent;
import com.example.order.dto.InvoiceItemDTO;
import com.example.order.entity.OrderEventEntity;
import com.example.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProcessingService {

    private final OrderRepository repository;
    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, ProcessedOrderEvent> kafkaTemplate;

    private static final String PAYMENT_SERVICE_URL = "http://localhost:8081/payments/{orderId}/status";
    private static final String INVENTORY_SERVICE_URL = "http://localhost:8082/api/v1/orders/{orderId}/invoice-items";
    private static final String SHIPMENT_SERVICE_URL = "http://localhost:8083/api/v1/ship-ready-invoice-items";
    private static final String PROCESSED_TOPIC = "order-processed";

    public void processEvent(OrderEvent event) {
        if (repository.existsById(event.getOrderId())) {
            log.info("Event already processed for orderId: {}", event.getOrderId());
            return;
        }
        OrderEventEntity entity = new OrderEventEntity(event.getOrderId(), "RECEIVED");
        repository.save(entity);
        log.info("Persisted event for orderId: {}", event.getOrderId());
    }

    public void processOrder(OrderEvent event) {
        log.info("OrderProcessingService::processOrder START with orderId: {}", event.getOrderId());
        String orderId = event.getOrderId();
        ProcessedOrderEvent result = ProcessedOrderEvent.builder()
                .orderId(orderId)
                .paymentStatus("UNKNOWN")
                .inventoryStatus("NOT_ATTEMPTED")
                .shipmentStatus("NOT_ATTEMPTED")
                .build();

        try {
            System.out.println("Processing order: 1 // Payment Call" + orderId);
            // 1. Payment Call
            String paymentStatus = checkPaymentStatus(orderId);
            result.setPaymentStatus(paymentStatus);

            if (!"SUCCESS".equals(paymentStatus) && !"COD".equals(paymentStatus)) {
                log.warn("Payment failed or unknown for orderId: {}", orderId);
                publishResult(result);
                return;
            }

            // 2. Inventory Call
            List<InvoiceItemDTO> invoiceItems = checkInventory(orderId);
            if (invoiceItems == null) {
                result.setInventoryStatus("FAILED");
                result.setErrorMessage("Inventory service failure");
                publishResult(result);
                return;
            }
            result.setInventoryStatus("SUCCESS");

            // 3. Shipment Call
            boolean shipmentSuccess = createShipment(event, invoiceItems);
            result.setShipmentStatus(shipmentSuccess ? "SUCCESS" : "FAILED");
            if (!shipmentSuccess) {
                result.setErrorMessage("Shipment service failure");
            }

        } catch (Exception e) {
            log.error("Error processing orderId: {}", orderId, e);
            result.setErrorMessage(e.getMessage());
        } finally {
            publishResult(result);
            log.info("OrderProcessingService::processOrder END");
        }
    }

    private String checkPaymentStatus(String orderId) {
        log.info("OrderProcessingService::checkPaymentStatus START with orderId: {}", orderId);
        try {
            // Assuming Payment Service returns a simple JSON with status or string.
            // Based on previous knowledge, let's assume it returns an entity. We need a DTO
            // or map.
            // For simplicity, we'll try to get the raw response and extract status if
            // possible,
            // or assume the service endpoint might need adjustment.
            // Let's assume the user's previous Payment Service returns PaymentStatus
            // entity.
            // We'll use a Map to avoid strong coupling for now.
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restTemplate.getForObject(
                    PAYMENT_SERVICE_URL,
                    Map.class,
                    orderId);
            if (response != null && response.get("paymentStatus") != null) {
                return (String) response.get("paymentStatus");
            }
        } catch (Exception e) {
            log.error("Payment service call failed", e);
        }
        log.info("OrderProcessingService::checkPaymentStatus END");
        return "UNKNOWN";
    }

    private List<InvoiceItemDTO> checkInventory(String orderId) {
        log.info("OrderProcessingService::checkInventory START with orderId: {}", orderId);
        try {
            ResponseEntity<List<InvoiceItemDTO>> response = restTemplate.exchange(
                    INVENTORY_SERVICE_URL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<InvoiceItemDTO>>() {
                    },
                    orderId);
            List<InvoiceItemDTO> items = response.getBody();
            log.info("OrderProcessingService::checkInventory END");
            return items;
        } catch (NumberFormatException e) {
            log.error("Invalid orderId format: {}", orderId, e);
            return null;
        } catch (Exception e) {
            log.error("Inventory service call failed", e);
            return null;
        }
    }

    private boolean createShipment(OrderEvent event, List<InvoiceItemDTO> invoiceItems) {
        log.info("OrderProcessingService::createShipment START with orderId: {}", event.getOrderId());
        try {
            // Shipment service expects one item at a time or list?
            // The requirement says "Call Shipment Service with... ShipReadyInvoiceItem
            // list".
            // But my Shipment Service create endpoint takes ONE item.
            // I will iterate and create for EACH item found in inventory.
            // This is a design decision: The prompt implies a batch call or logic.
            // Since Shipment Service only has POST /api/v1/ship-ready-invoice-items
            // (singular),
            // I will call it for each item.

            for (InvoiceItemDTO item : invoiceItems) {
                // We need to map Inventory Item to Shipment Item.
                // Assuming properties roughly match or we construct a generic payload.
                // Ideally we should have a DTO. Let's send the event details + item details.
                // For this scratch implementation, I will construct a basic Payload.
                // Actually, Shipment Service Entity has: orderId, parcelId, itemLocationId.
                // Inventory returns ShipReadyInvoiceItem (id, orderId, parcelId,
                // itemLocationId).
                // So we can pass the body directly.

                restTemplate.postForEntity(SHIPMENT_SERVICE_URL, item, Object.class);
            }
            log.info("OrderProcessingService::createShipment END");
            return true;
        } catch (Exception e) {
            log.error("Shipment service call failed", e);
            return false;
        }
    }

    private void publishResult(ProcessedOrderEvent result) {
        try {
            kafkaTemplate.send(PROCESSED_TOPIC, result.getOrderId(), result);
            log.info("Published order-processed event for orderId: {}", result.getOrderId());
        } catch (Exception e) {
            log.error("Failed to publish result", e);
        }
    }
}
