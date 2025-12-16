package com.example.payment.controller;

import com.example.payment.entity.PaymentStatus;
import com.example.payment.exception.PaymentNotFoundException;
import com.example.payment.service.PaymentStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentStatusController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PaymentStatusController.class);

    private final PaymentStatusService service;

    public PaymentStatusController(PaymentStatusService service) {
        this.service = service;
    }

    @GetMapping("/{orderId}/status")
    public PaymentStatus getPaymentStatus(@PathVariable String orderId) {
        log.info("PaymentStatusController::getPaymentStatus START with orderId: {}", orderId);
        PaymentStatus status = service.getPaymentStatus(orderId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment status not found for Order ID: " + orderId));
        log.info("PaymentStatusController::getPaymentStatus END");
        return status;
    }
}
