package com.example.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }

    @org.springframework.context.annotation.Bean
    public org.springframework.boot.CommandLineRunner demo(
            com.example.inventory.repository.ShipReadyInvoiceItemRepository repository) {
        return (args) -> {
            repository.save(new com.example.inventory.entity.ShipReadyInvoiceItem(1, 1L, 100001L, 100381L));
            repository.save(new com.example.inventory.entity.ShipReadyInvoiceItem(2, 1L, 100002L, 100487L));
            repository.save(new com.example.inventory.entity.ShipReadyInvoiceItem(3, 3L, 100047L, 101056L));
            repository.save(new com.example.inventory.entity.ShipReadyInvoiceItem(4, 3L, 100051L, 100256L));
        };
    }

}
