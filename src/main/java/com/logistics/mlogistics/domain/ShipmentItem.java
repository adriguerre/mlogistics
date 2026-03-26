package com.logistics.mlogistics.domain;

import com.logistics.mlogistics.domain.enums.ItemCondition;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "shipment_item")
public class ShipmentItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "shipment_id", nullable = false)
    private UUID shipmentId;

    @Column(name = "equipment_id", nullable = false)
    private UUID equipmentId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition", nullable = false)
    private ItemCondition condition;

    public ShipmentItem() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getShipmentId() { return shipmentId; }
    public void setShipmentId(UUID shipmentId) { this.shipmentId = shipmentId; }
    public UUID getEquipmentId() { return equipmentId; }
    public void setEquipmentId(UUID equipmentId) { this.equipmentId = equipmentId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public ItemCondition getCondition() { return condition; }
    public void setCondition(ItemCondition condition) { this.condition = condition; }
}
