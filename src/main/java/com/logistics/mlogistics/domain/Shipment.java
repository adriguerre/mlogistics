package com.logistics.mlogistics.domain;

import com.logistics.mlogistics.domain.enums.ShipmentStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "shipment")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "tracking_code", nullable = false, unique = true, length = 80)
    private String trackingCode;

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "origin_base_id")
    private UUID originBaseId;

    @Column(name = "destination_base_id")
    private UUID destinationBaseId;

    @Column(name = "vehicle_id")
    private UUID vehicleId;

    @Column(name = "driver_id")
    private UUID driverId;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "shipment_status")
    private ShipmentStatus status;

    @Column(name = "total_weight_kg")
    private BigDecimal totalWeightKg;

    @Column(name = "dispatched_at")
    private Timestamp dispatchedAt;

    @Column(name = "estimated_arrival_at")
    private Timestamp estimatedArrivalAt;

    @Column(name = "delivered_at")
    private Timestamp deliveredAt;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private Timestamp createdAt;

    public Shipment() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getTrackingCode() { return trackingCode; }
    public void setTrackingCode(String trackingCode) { this.trackingCode = trackingCode; }
    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }
    public UUID getOriginBaseId() { return originBaseId; }
    public void setOriginBaseId(UUID originBaseId) { this.originBaseId = originBaseId; }
    public UUID getDestinationBaseId() { return destinationBaseId; }
    public void setDestinationBaseId(UUID destinationBaseId) { this.destinationBaseId = destinationBaseId; }
    public UUID getVehicleId() { return vehicleId; }
    public void setVehicleId(UUID vehicleId) { this.vehicleId = vehicleId; }
    public UUID getDriverId() { return driverId; }
    public void setDriverId(UUID driverId) { this.driverId = driverId; }
    public ShipmentStatus getStatus() { return status; }
    public void setStatus(ShipmentStatus status) { this.status = status; }
    public BigDecimal getTotalWeightKg() { return totalWeightKg; }
    public void setTotalWeightKg(BigDecimal totalWeightKg) { this.totalWeightKg = totalWeightKg; }
    public Timestamp getDispatchedAt() { return dispatchedAt; }
    public void setDispatchedAt(Timestamp dispatchedAt) { this.dispatchedAt = dispatchedAt; }
    public Timestamp getEstimatedArrivalAt() { return estimatedArrivalAt; }
    public void setEstimatedArrivalAt(Timestamp estimatedArrivalAt) { this.estimatedArrivalAt = estimatedArrivalAt; }
    public Timestamp getDeliveredAt() { return deliveredAt; }
    public void setDeliveredAt(Timestamp deliveredAt) { this.deliveredAt = deliveredAt; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
