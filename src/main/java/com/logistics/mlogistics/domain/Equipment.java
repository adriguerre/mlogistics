package com.logistics.mlogistics.domain;

import com.logistics.mlogistics.domain.enums.EquipClassification;
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
@Table(name = "equipment")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "sku", nullable = false, unique = true, length = 60)
    private String sku;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "model", length = 150)
    private String model;

    @Column(name = "manufacturer", length = 150)
    private String manufacturer;

    @Column(name = "category_id", nullable = false)
    private UUID categoryId;

    @Column(name = "unit_weight_kg")
    private BigDecimal unitWeightKg;

    @Column(name = "unit_cost_usd")
    private BigDecimal unitCostUsd;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    @Column(name = "classification", nullable = false, columnDefinition = "equip_classification")
    private EquipClassification classification;

    @Column(name = "requires_maintenance", nullable = false)
    private Boolean requiresMaintenance;

    @Column(name = "maintenance_interval_days")
    private Integer maintenanceIntervalDays;

    @Generated(event = EventType.INSERT)
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private Timestamp createdAt;

    public Equipment() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public UUID getCategoryId() { return categoryId; }
    public void setCategoryId(UUID categoryId) { this.categoryId = categoryId; }

    public BigDecimal getUnitWeightKg() { return unitWeightKg; }
    public void setUnitWeightKg(BigDecimal unitWeightKg) { this.unitWeightKg = unitWeightKg; }

    public BigDecimal getUnitCostUsd() { return unitCostUsd; }
    public void setUnitCostUsd(BigDecimal unitCostUsd) { this.unitCostUsd = unitCostUsd; }

    public EquipClassification getClassification() { return classification; }
    public void setClassification(EquipClassification classification) { this.classification = classification; }

    public Boolean getRequiresMaintenance() { return requiresMaintenance; }
    public void setRequiresMaintenance(Boolean requiresMaintenance) { this.requiresMaintenance = requiresMaintenance; }

    public Integer getMaintenanceIntervalDays() { return maintenanceIntervalDays; }
    public void setMaintenanceIntervalDays(Integer maintenanceIntervalDays) { this.maintenanceIntervalDays = maintenanceIntervalDays; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
