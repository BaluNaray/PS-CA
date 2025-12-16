package com.example.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentStatusApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentStatusApplication.class, args);
    }

    @org.springframework.context.annotation.Bean
    public org.springframework.boot.CommandLineRunner demo(
            com.example.payment.repository.PaymentStatusRepository repository) {
        return (args) -> {
            repository.save(new com.example.payment.entity.PaymentStatus(1L, "1",
                    com.example.payment.entity.PaymentStatusEnum.SUCCESS));
            repository.save(new com.example.payment.entity.PaymentStatus(2L, "2",
                    com.example.payment.entity.PaymentStatusEnum.FAILED));
            repository.save(new com.example.payment.entity.PaymentStatus(3L, "3",
                    com.example.payment.entity.PaymentStatusEnum.COD));
        };
    }

}
