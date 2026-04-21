package com.logistics.mlogistics.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.logistics.mlogistics.domain.enums.BaseStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;
import java.sql.Timestamp;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@Entity
@DynamicInsert
@Table(name = "base")
@JsonPropertyOrder({"id", "code", "name", "country", "region", "latitude", "longitude", "status", "equipment_slots", "fuel_capacity_l", "created_at", "updated_at"})
public class Base {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "code", nullable = false, unique = true, length = 20)
    private String code;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "country", nullable = false, length = 100)
    private String country;

    @Column(name = "region", length = 100)
    private String region;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "base_status")
    private BaseStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "commanding_unit_id")
    @JsonIgnoreProperties({"home_base", "units"})
    private Unit commandingUnit;

    @OneToMany(mappedBy = "homeBase", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"home_base", "commanding_unit"})
    private List<Unit> units;

    @Column(name = "equipment_slots", nullable = false)
    private Integer equipmentSlots;

    @Column(name = "fuel_capacity_l", nullable = false)
    private Integer fuelCapacityL;

    @Generated(event = EventType.INSERT)
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, insertable = false)
    private Timestamp updatedAt;

    public Base() {}

    @JsonCreator
    public Base(@JsonProperty("id") UUID id) {
        this.id = id;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public BaseStatus getStatus() { return status; }
    public void setStatus(BaseStatus status) { this.status = status; }
    public Unit getCommandingUnit() { return commandingUnit; }
    public void setCommandingUnit(Unit commandingUnit) { this.commandingUnit = commandingUnit; }
    public List<Unit> getUnits() { return units; }
    public void setUnits(List<Unit> units) { this.units = units; }
    public Integer getEquipmentSlots() { return equipmentSlots; }
    public void setEquipmentSlots(Integer equipmentSlots) { this.equipmentSlots = equipmentSlots; }
    public Integer getFuelCapacityL() { return fuelCapacityL; }
    public void setFuelCapacityL(Integer fuelCapacityL) { this.fuelCapacityL = fuelCapacityL; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}
