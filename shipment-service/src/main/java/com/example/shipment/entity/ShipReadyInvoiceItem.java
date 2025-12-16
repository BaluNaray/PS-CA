package com.example.shipment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ship_ready_invoice_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipReadyInvoiceItem {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long orderId;
    private Long parcelId;
    private Long itemLocationId;
}
