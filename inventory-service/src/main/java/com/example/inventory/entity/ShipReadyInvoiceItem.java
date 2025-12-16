package com.example.inventory.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ship_ready_invoice_item")
public class ShipReadyInvoiceItem {
    @Id
    private Integer id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "parcel_id")
    private Long parcelId;

    @Column(name = "item_location_id")
    private Long itemLocationId;

    public ShipReadyInvoiceItem() {
    }

    public ShipReadyInvoiceItem(Integer id, Long orderId, Long parcelId, Long itemLocationId) {
        this.id = id;
        this.orderId = orderId;
        this.parcelId = parcelId;
        this.itemLocationId = itemLocationId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getParcelId() {
        return parcelId;
    }

    public void setParcelId(Long parcelId) {
        this.parcelId = parcelId;
    }

    public Long getItemLocationId() {
        return itemLocationId;
    }

    public void setItemLocationId(Long itemLocationId) {
        this.itemLocationId = itemLocationId;
    }
}
