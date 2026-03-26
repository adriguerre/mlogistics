package com.logistics.mlogistics.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "supply_order_item")
public class SupplyOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "equipment_id", nullable = false)
    private UUID equipmentId;

    @Column(name = "qty_requested", nullable = false)
    private Integer qtyRequested;

    @Column(name = "qty_approved")
    private Integer qtyApproved;

    @Column(name = "unit_price_usd")
    private BigDecimal unitPriceUsd;

    public SupplyOrderItem() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }
    public UUID getEquipmentId() { return equipmentId; }
    public void setEquipmentId(UUID equipmentId) { this.equipmentId = equipmentId; }
    public Integer getQtyRequested() { return qtyRequested; }
    public void setQtyRequested(Integer qtyRequested) { this.qtyRequested = qtyRequested; }
    public Integer getQtyApproved() { return qtyApproved; }
    public void setQtyApproved(Integer qtyApproved) { this.qtyApproved = qtyApproved; }
    public BigDecimal getUnitPriceUsd() { return unitPriceUsd; }
    public void setUnitPriceUsd(BigDecimal unitPriceUsd) { this.unitPriceUsd = unitPriceUsd; }
}
