package com.example.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_processed_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEventEntity {
    @Id
    @Column(name = "order_id")
    private String orderId;

    private String status;
}
