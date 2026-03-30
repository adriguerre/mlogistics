package com.logistics.mlogistics.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.logistics.mlogistics.domain.enums.VehicleStatus;
import com.logistics.mlogistics.domain.enums.VehicleType;
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

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, columnDefinition = "vehicle_type")
    private VehicleType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_id")
    @JsonIgnoreProperties({"commanding_unit", "units"})
    private Base base;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    @JsonIgnoreProperties({"home_base", "commander", "units"})
    private Unit unit;

    @Column(name = "max_payload_kg", precision = 10, scale = 2)
    private BigDecimal maxPayloadKg;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "vehicle_status")
    private VehicleStatus status;

    @Generated(event = EventType.INSERT)
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private Timestamp createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_base_id")
    @JsonIgnoreProperties({"commanding_unit", "units"})
    private Base currentBase;

    @Column(name = "passenger_capacity")
    private Integer passengerCapacity;

    public Vehicle() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getPlate() { return plate; }
    public void setPlate(String plate) { this.plate = plate; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public VehicleType getType() { return type; }
    public void setType(VehicleType type) { this.type = type; }
    public Base getBase() { return base; }
    public void setBase(Base base) { this.base = base; }
    public Unit getUnit() { return unit; }
    public void setUnit(Unit unit) { this.unit = unit; }
    public Base getCurrentBase() { return currentBase; }
    public void setCurrentBase(Base currentBase) { this.currentBase = currentBase; }
    public BigDecimal getMaxPayloadKg() { return maxPayloadKg; }
    public void setMaxPayloadKg(BigDecimal maxPayloadKg) { this.maxPayloadKg = maxPayloadKg; }
    public VehicleStatus getStatus() { return status; }
    public void setStatus(VehicleStatus status) { this.status = status; }
    public Integer getPassengerCapacity() { return passengerCapacity; }
    public void setPassengerCapacity(Integer passengerCapacity) { this.passengerCapacity = passengerCapacity; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
