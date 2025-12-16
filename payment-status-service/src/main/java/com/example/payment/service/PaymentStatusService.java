package com.example.payment.service;

import com.example.payment.entity.PaymentStatus;
import com.example.payment.repository.PaymentStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentStatusService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PaymentStatusService.class);

    private final PaymentStatusRepository repository;

    public PaymentStatusService(PaymentStatusRepository repository) {
        this.repository = repository;
    }

    public Optional<PaymentStatus> getPaymentStatus(String orderId) {
        log.info("PaymentStatusService::getPaymentStatus START with orderId: {}", orderId);
        Optional<PaymentStatus> status = repository.findByOrderId(orderId);
        log.info("PaymentStatusService::getPaymentStatus END");
        return status;
    }
}
