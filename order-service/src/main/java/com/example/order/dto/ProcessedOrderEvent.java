package com.example.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessedOrderEvent {
    private String orderId;
    private String paymentStatus;
    private String inventoryStatus;
    private String shipmentStatus;
    private String errorMessage;
}
