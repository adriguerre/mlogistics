package com.logistics.mlogistics.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@DynamicInsert
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false)
    @JsonIgnoreProperties({"category"})
    private Equipment equipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_id")
    @JsonIgnoreProperties({"commanding_unit", "units"})
    private Base base;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    @JsonIgnoreProperties({"home_base", "commander", "units"})
    private Unit unit;

    @Column(name = "qty_total", nullable = false)
    private Integer qtyTotal;

    @Column(name = "qty_available", nullable = false)
    private Integer qtyAvailable;

    @Column(name = "qty_reserved", nullable = false)
    private Integer qtyReserved;

    @Column(name = "qty_damaged", nullable = false)
    private Integer qtyDamaged;

    @Column(name = "reorder_threshold", nullable = false)
    private Integer reorderThreshold;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, insertable = false)
    private Timestamp updatedAt;

    public Inventory() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Equipment getEquipment() { return equipment; }
    public void setEquipment(Equipment equipment) { this.equipment = equipment; }

    public Base getBase() { return base; }
    public void setBase(Base base) { this.base = base; }

    public Unit getUnit() { return unit; }
    public void setUnit(Unit unit) { this.unit = unit; }

    public Integer getQtyTotal() { return qtyTotal; }
    public void setQtyTotal(Integer qtyTotal) { this.qtyTotal = qtyTotal; }

    public Integer getQtyAvailable() { return qtyAvailable; }
    public void setQtyAvailable(Integer qtyAvailable) { this.qtyAvailable = qtyAvailable; }

    public Integer getQtyReserved() { return qtyReserved; }
    public void setQtyReserved(Integer qtyReserved) { this.qtyReserved = qtyReserved; }

    public Integer getQtyDamaged() { return qtyDamaged; }
    public void setQtyDamaged(Integer qtyDamaged) { this.qtyDamaged = qtyDamaged; }

    public Integer getReorderThreshold() { return reorderThreshold; }
    public void setReorderThreshold(Integer reorderThreshold) { this.reorderThreshold = reorderThreshold; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}
