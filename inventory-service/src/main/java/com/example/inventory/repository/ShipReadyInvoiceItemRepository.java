package com.example.inventory.repository;

import com.example.inventory.entity.ShipReadyInvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ShipReadyInvoiceItemRepository extends JpaRepository<ShipReadyInvoiceItem, Integer> {
    List<ShipReadyInvoiceItem> findByOrderId(Long orderId);
}
