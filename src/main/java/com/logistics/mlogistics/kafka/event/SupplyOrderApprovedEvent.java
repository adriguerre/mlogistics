package com.logistics.mlogistics.kafka.event;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class SupplyOrderApprovedEvent {

    public UUID orderId;
    public UUID destinationBase;
    public Date estimated_arrival_at;
    public String trackingCode;
    public UUID equipmentId;
    public Integer qtyNeeded;

    public SupplyOrderApprovedEvent() {
    }

    public SupplyOrderApprovedEvent(UUID orderId, UUID destinationBase, Date estimated_arrival_at, String trackingCode) {
        this.orderId = orderId;
        this.destinationBase = destinationBase;
        this.estimated_arrival_at = estimated_arrival_at;
        this.trackingCode = trackingCode;
    }

    public SupplyOrderApprovedEvent(UUID orderId, UUID destinationBase, Date estimated_arrival_at, String trackingCode, UUID equipmentId, Integer qtyNeeded) {
        this.orderId = orderId;
        this.destinationBase = destinationBase;
        this.estimated_arrival_at = estimated_arrival_at;
        this.trackingCode = trackingCode;
        this.equipmentId = equipmentId;
        this.qtyNeeded = qtyNeeded;
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

    public UUID getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(UUID equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Integer getQtyNeeded() {
        return qtyNeeded;
    }

    public void setQtyNeeded(Integer qtyNeeded) {
        this.qtyNeeded = qtyNeeded;
    }
}
