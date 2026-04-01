package com.logistics.mlogistics.kafka.event;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class SupplyOrderApprovedEvent {

    public UUID orderId;
    public UUID destinationBase;
    public Date estimated_arrival_at;
    public String trackingCode;

    public SupplyOrderApprovedEvent() {
    }

    public SupplyOrderApprovedEvent(UUID orderId, UUID destinationBase, Date estimated_arrival_at, String trackingCode) {
        this.orderId = orderId;
        this.destinationBase = destinationBase;
        this.estimated_arrival_at = estimated_arrival_at;
        this.trackingCode = trackingCode;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getDestinationBase() {
        return destinationBase;
    }

    public void setDestinationBase(UUID destinationBase) {
        this.destinationBase = destinationBase;
    }

    public Date getEstimated_arrival_at() {
        return estimated_arrival_at;
    }

    public void setEstimated_arrival_at(Date estimated_arrival_at) {
        this.estimated_arrival_at = estimated_arrival_at;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }
}
