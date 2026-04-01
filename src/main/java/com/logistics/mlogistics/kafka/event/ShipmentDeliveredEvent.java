package com.logistics.mlogistics.kafka.event;

import java.util.UUID;

public class ShipmentDeliveredEvent {

    private UUID shipmentId;
    private UUID destinationBaseId;

    public ShipmentDeliveredEvent() {}

    public ShipmentDeliveredEvent(UUID shipmentId, UUID destinationBaseId) {
        this.shipmentId = shipmentId;
        this.destinationBaseId = destinationBaseId;
    }

    public UUID getShipmentId() { return shipmentId; }
    public void setShipmentId(UUID shipmentId) { this.shipmentId = shipmentId; }
    public UUID getDestinationBaseId() { return destinationBaseId; }
    public void setDestinationBaseId(UUID destinationBaseId) { this.destinationBaseId = destinationBaseId; }
}
