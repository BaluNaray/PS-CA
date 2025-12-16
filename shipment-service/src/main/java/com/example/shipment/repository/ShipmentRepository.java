package com.example.shipment.repository;

import com.example.shipment.entity.ShipReadyInvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<ShipReadyInvoiceItem, Integer> {
}
