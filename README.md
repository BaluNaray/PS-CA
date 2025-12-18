Project Summary : The listener guarantees durability via DB persistence before acknowledgment, while the orchestrator handles business coordination and emits a consolidated result event.

https://mermaid.live/edit   
To understand the Project please go to the above url or any mermaid viewer and paste the code below. Shall add the image to the this folder too. File Name: Screenshot 2025-12-17 042909.png

flowchart LR
    %% =========================
    %% External Infrastructure
    %% =========================
    Kafka[(Kafka)]
    OrderDB[(Order DB)]

    %% =========================
    %% Downstream Services + DBs
    %% =========================
    PaymentService["Payment Service"]
    PaymentDB[(Payment DB)]

    InventoryService["Inventory Service"]
    InventoryDB[(Inventory DB)]

    ShipmentService["Shipment Service"]
    ShipmentDB[(Shipment DB)]

    %% =========================
    %% Order Service
    %% =========================
    subgraph OrderService["Order Service"]
        direction TB

        KafkaListener["Kafka Listener - Manual Ack"]
        Persistence["Persist Order Event"]
        Orchestrator["Order Orchestrator"]

        PaymentClient["Payment Client - Timeout 2000ms"]
        InventoryClient["Inventory Client - Timeout 2000ms"]
        ShipmentClient["Shipment Client - Timeout 2000ms"]

        EventPublisher["Order Status Publisher"]
    end

    %% =========================
    %% Kafka Consumption Flow
    %% =========================
    Kafka --> KafkaListener
    KafkaListener --> Persistence
    Persistence --> OrderDB
    Persistence -->|Ack after DB save| KafkaListener
    KafkaListener --> Orchestrator

    %% =========================
    %% Payment Flow
    %% =========================
    Orchestrator --> PaymentClient
    PaymentClient --> PaymentService
    PaymentService --> PaymentDB
    PaymentDB -->|Payment status| PaymentService
    PaymentService --> PaymentClient
    PaymentClient --> Orchestrator

    %% =========================
    %% Inventory Flow
    %% =========================
    Orchestrator --> InventoryClient
    InventoryClient --> InventoryService
    InventoryService --> InventoryDB
    InventoryDB -->|Pickup location IDs| InventoryService
    InventoryService --> InventoryClient
    InventoryClient --> Orchestrator

    %% =========================
    %% Shipment Flow
    %% =========================
    Orchestrator --> ShipmentClient
    ShipmentClient --> ShipmentService
    ShipmentService --> ShipmentDB
    ShipmentService -->|Shipment saved| ShipmentClient
    ShipmentClient --> Orchestrator

    %% =========================
    %% Kafka Publishing
    %% =========================
    Orchestrator --> EventPublisher
    EventPublisher --> Kafka
