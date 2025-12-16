package com.example.shipment.service;

import com.example.shipment.entity.ShipReadyInvoiceItem;
import com.example.shipment.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShipmentService {

    private final ShipmentRepository repository;

    public ShipReadyInvoiceItem createShipReadyItem(ShipReadyInvoiceItem item) {
        log.info("ShipmentService::createShipReadyItem START with item: {}", item);
        ShipReadyInvoiceItem savedItem = repository.save(item);
        log.info("ShipmentService::createShipReadyItem END");
        return savedItem;
    }
}
