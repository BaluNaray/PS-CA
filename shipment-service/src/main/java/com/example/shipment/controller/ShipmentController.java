package com.example.shipment.controller;

import com.example.shipment.entity.ShipReadyInvoiceItem;
import com.example.shipment.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ship-ready-invoice-items")
@RequiredArgsConstructor
@Slf4j
public class ShipmentController {

    private final ShipmentService service;

    @PostMapping
    public ResponseEntity<ShipReadyInvoiceItem> createShipReadyItem(@RequestBody ShipReadyInvoiceItem item) {
        log.info("ShipmentController::createShipReadyItem START with item: {}", item);
        ShipReadyInvoiceItem createdItem = service.createShipReadyItem(item);
        log.info("ShipmentController::createShipReadyItem END");
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }
}
