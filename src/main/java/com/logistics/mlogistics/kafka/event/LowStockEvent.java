package com.logistics.mlogistics.kafka.event;

import java.util.UUID;

public class LowStockEvent {

    private UUID inventoryId;
    private UUID equipmentId;
    private String equipmentName;
    private UUID baseId;
    private String baseName;
    private int qtyAvailable;
    private int reorderThreshold;

    public LowStockEvent() {}

    public LowStockEvent(UUID inventoryId, UUID equipmentId, String equipmentName,
                         UUID baseId, String baseName, int qtyAvailable, int reorderThreshold) {
        this.inventoryId = inventoryId;
        this.equipmentId = equipmentId;
        this.equipmentName = equipmentName;
        this.baseId = baseId;
        this.baseName = baseName;
        this.qtyAvailable = qtyAvailable;
        this.reorderThreshold = reorderThreshold;
    }

    public UUID getInventoryId() { return inventoryId; }
    public void setInventoryId(UUID inventoryId) { this.inventoryId = inventoryId; }
    public UUID getEquipmentId() { return equipmentId; }
    public void setEquipmentId(UUID equipmentId) { this.equipmentId = equipmentId; }
    public String getEquipmentName() { return equipmentName; }
    public void setEquipmentName(String equipmentName) { this.equipmentName = equipmentName; }
    public UUID getBaseId() { return baseId; }
    public void setBaseId(UUID baseId) { this.baseId = baseId; }
    public String getBaseName() { return baseName; }
    public void setBaseName(String baseName) { this.baseName = baseName; }
    public int getQtyAvailable() { return qtyAvailable; }
    public void setQtyAvailable(int qtyAvailable) { this.qtyAvailable = qtyAvailable; }
    public int getReorderThreshold() { return reorderThreshold; }
    public void setReorderThreshold(int reorderThreshold) { this.reorderThreshold = reorderThreshold; }
}
