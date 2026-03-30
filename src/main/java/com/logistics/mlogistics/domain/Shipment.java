package com.logistics.mlogistics.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.logistics.mlogistics.domain.enums.ShipmentStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;
import org.hibernate.annotations.DynamicInsert;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@DynamicInsert
@Table(name = "shipment")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "tracking_code", nullable = false, unique = true, length = 80)
    private String trackingCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnoreProperties({"requesting_unit", "requesting_base", "supplier", "approved_by"})
    private SupplyOrder order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_base_id")
    @JsonIgnoreProperties({"commanding_unit", "units"})
    private Base originBase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_base_id")
    @JsonIgnoreProperties({"commanding_unit", "units"})
    private Base destinationBase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    @JsonIgnoreProperties({"base", "unit", "current_base"})
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    @JsonIgnoreProperties({"unit", "base"})
    private Personnel driver;

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

    @Generated(event = EventType.INSERT)
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private Timestamp createdAt;

    public Shipment() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getTrackingCode() { return trackingCode; }
    public void setTrackingCode(String trackingCode) { this.trackingCode = trackingCode; }
    public SupplyOrder getOrder() { return order; }
    public void setOrder(SupplyOrder order) { this.order = order; }
    public Base getOriginBase() { return originBase; }
    public void setOriginBase(Base originBase) { this.originBase = originBase; }
    public Base getDestinationBase() { return destinationBase; }
    public void setDestinationBase(Base destinationBase) { this.destinationBase = destinationBase; }
    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
    public Personnel getDriver() { return driver; }
    public void setDriver(Personnel driver) { this.driver = driver; }
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
