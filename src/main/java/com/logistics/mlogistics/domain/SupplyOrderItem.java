package com.logistics.mlogistics.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnoreProperties({"requesting_unit", "requesting_base", "supplier", "approved_by"})
    private SupplyOrder order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false)
    @JsonIgnoreProperties({"category"})
    private Equipment equipment;

    @Column(name = "qty_requested", nullable = false)
    private Integer qtyRequested;

    @Column(name = "qty_approved")
    private Integer qtyApproved;

    @Column(name = "unit_price_usd")
    private BigDecimal unitPriceUsd;

    public SupplyOrderItem() {}

    public SupplyOrderItem(UUID id, SupplyOrder order, Equipment equipment, Integer qtyRequested, BigDecimal unitPriceUsd, Integer qtyApproved) {
        this.id = id;
        this.order = order;
        this.equipment = equipment;
        this.qtyRequested = qtyRequested;
        this.unitPriceUsd = unitPriceUsd;
        this.qtyApproved = qtyApproved;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public SupplyOrder getOrder() { return order; }
    public void setOrder(SupplyOrder order) { this.order = order; }
    public Equipment getEquipment() { return equipment; }
    public void setEquipment(Equipment equipment) { this.equipment = equipment; }
    public Integer getQtyRequested() { return qtyRequested; }
    public void setQtyRequested(Integer qtyRequested) { this.qtyRequested = qtyRequested; }
    public Integer getQtyApproved() { return qtyApproved; }
    public void setQtyApproved(Integer qtyApproved) { this.qtyApproved = qtyApproved; }
    public BigDecimal getUnitPriceUsd() { return unitPriceUsd; }
    public void setUnitPriceUsd(BigDecimal unitPriceUsd) { this.unitPriceUsd = unitPriceUsd; }
}
