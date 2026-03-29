package com.logistics.mlogistics.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "equipment_id", nullable = false)
    private UUID equipmentId;

    @Column(name = "base_id")
    private UUID baseId;

    @Column(name = "unit_id")
    private UUID unitId;

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

    public UUID getEquipmentId() { return equipmentId; }
    public void setEquipmentId(UUID equipmentId) { this.equipmentId = equipmentId; }

    public UUID getBaseId() { return baseId; }
    public void setBaseId(UUID baseId) { this.baseId = baseId; }

    public UUID getUnitId() { return unitId; }
    public void setUnitId(UUID unitId) { this.unitId = unitId; }

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
