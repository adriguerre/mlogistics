package com.logistics.mlogistics.domain;

import com.logistics.mlogistics.domain.enums.VehicleStatus;
import com.logistics.mlogistics.domain.enums.VehicleType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "plate", nullable = false, unique = true, length = 50)
    private String plate;

    @Column(name = "model", nullable = false, length = 150)
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private VehicleType type;

    @Column(name = "base_id")
    private UUID baseId;

    @Column(name = "unit_id")
    private UUID unitId;

    @Column(name = "max_payload_kg", precision = 10, scale = 2)
    private BigDecimal maxPayloadKg;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private VehicleStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "current_base_id")
    private UUID currentBaseId;

    public Vehicle() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getPlate() { return plate; }
    public void setPlate(String plate) { this.plate = plate; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public VehicleType getType() { return type; }
    public void setType(VehicleType type) { this.type = type; }
    public UUID getBaseId() { return baseId; }
    public void setBaseId(UUID baseId) { this.baseId = baseId; }
    public UUID getUnitId() { return unitId; }
    public void setUnitId(UUID unitId) { this.unitId = unitId; }
    public UUID getCurrentBaseId() { return currentBaseId; }
    public void setCurrentBaseId(UUID currentBaseId) { this.currentBaseId = currentBaseId; }
    public BigDecimal getMaxPayloadKg() { return maxPayloadKg; }
    public void setMaxPayloadKg(BigDecimal maxPayloadKg) { this.maxPayloadKg = maxPayloadKg; }
    public VehicleStatus getStatus() { return status; }
    public void setStatus(VehicleStatus status) { this.status = status; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
