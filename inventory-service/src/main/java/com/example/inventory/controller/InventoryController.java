package com.example.inventory.controller;

import com.example.inventory.entity.ShipReadyInvoiceItem;
import com.example.inventory.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class InventoryController {

    private static final Logger log = LoggerFactory.getLogger(InventoryController.class);

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @GetMapping("/{orderId}/invoice-items")
    public List<ShipReadyInvoiceItem> getInvoiceItems(@PathVariable Long orderId) {
        log.info("InventoryController::getInvoiceItems START with orderId: {}", orderId);
        List<ShipReadyInvoiceItem> items = service.getInvoiceItems(orderId);
        log.info("InventoryController::getInvoiceItems END");
        return items;
    }
}
