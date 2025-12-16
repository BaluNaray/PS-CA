package com.example.inventory.service;

import com.example.inventory.entity.ShipReadyInvoiceItem;
import com.example.inventory.repository.ShipReadyInvoiceItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);

    private final ShipReadyInvoiceItemRepository repository;

    public InventoryService(ShipReadyInvoiceItemRepository repository) {
        this.repository = repository;
    }

    public List<ShipReadyInvoiceItem> getInvoiceItems(Long orderId) {
        log.info("InventoryService::getInvoiceItems START with orderId: {}", orderId);
        List<ShipReadyInvoiceItem> items = repository.findByOrderId(orderId);
        log.info("InventoryService::getInvoiceItems END");
        return items;
    }
}
