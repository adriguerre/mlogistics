package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.Inventory;
import com.logistics.mlogistics.domain.ShipmentItem;
import com.logistics.mlogistics.kafka.event.LowStockEvent;
import com.logistics.mlogistics.kafka.event.ShipmentDeliveredEvent;
import com.logistics.mlogistics.kafka.producer.InventoryEventProducer;
import com.logistics.mlogistics.repository.InventoryRepository;
import com.logistics.mlogistics.repository.ShipmentItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InventoryService {

    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);
    private final InventoryRepository inventoryRepository;
    private final InventoryEventProducer eventProducer;
    private final ShipmentItemRepository shipmentItemRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository, InventoryEventProducer eventProducer,
                            ShipmentItemRepository shipmentItemRepository) {
        this.inventoryRepository = inventoryRepository;
        this.eventProducer = eventProducer;
        this.shipmentItemRepository = shipmentItemRepository;
    }

    public Page<Inventory> getAll(Pageable pageable) {
        return inventoryRepository.findAll(pageable);
    }

    public Optional<Inventory> getById(UUID id) {
        return inventoryRepository.findById(id);
    }

    @Transactional
    public Inventory create(Inventory entity) {
        Inventory saved = inventoryRepository.saveAndFlush(entity);
        entityManager.refresh(saved);
        return saved;
    }

    public Optional<Inventory> update(UUID id, Inventory updated) {
        return inventoryRepository.findById(id).map(existing -> {
            if (updated.getEquipment() != null) existing.setEquipment(updated.getEquipment());
            if (updated.getBase() != null) existing.setBase(updated.getBase());
            if (updated.getUnit() != null) existing.setUnit(updated.getUnit());
            if (updated.getQtyTotal() != null) existing.setQtyTotal(updated.getQtyTotal());
            if (updated.getQtyAvailable() != null) existing.setQtyAvailable(updated.getQtyAvailable());
            if (updated.getQtyReserved() != null) existing.setQtyReserved(updated.getQtyReserved());
            if (updated.getQtyDamaged() != null) existing.setQtyDamaged(updated.getQtyDamaged());
            if (updated.getReorderThreshold() != null) existing.setReorderThreshold(updated.getReorderThreshold());
            if (updated.getUpdatedAt() != null) existing.setUpdatedAt(updated.getUpdatedAt());
            Inventory saved = inventoryRepository.save(existing);

            if (saved.getQtyAvailable() < saved.getReorderThreshold()) {
                LowStockEvent event = new LowStockEvent(
                        saved.getId(),
                        saved.getEquipment() != null ? saved.getEquipment().getId() : null,
                        saved.getEquipment() != null ? saved.getEquipment().getName() : null,
                        saved.getBase() != null ? saved.getBase().getId() : null,
                        saved.getBase() != null ? saved.getBase().getName() : null,
                        saved.getQtyAvailable(),
                        saved.getReorderThreshold()
                );
                eventProducer.sendLowStockAlert(event);
            }

            return saved;
        });
    }

    public boolean delete(UUID id) {
        if (!inventoryRepository.existsById(id)) return false;
        inventoryRepository.deleteById(id);
        return true;
    }

    @Transactional
    public void updateStockOnDelivery(ShipmentDeliveredEvent event) {
        List<ShipmentItem> items = shipmentItemRepository.findByShipmentId(event.getShipmentId());
        for (ShipmentItem item : items) {
            UUID equipmentId = item.getEquipment().getId();
            inventoryRepository.findByEquipmentIdAndBaseId(equipmentId, event.getDestinationBaseId()).ifPresent(inventory -> {
                inventory.setQtyAvailable(inventory.getQtyAvailable() + item.getQuantity());
                inventory.setQtyTotal(inventory.getQtyTotal() + item.getQuantity());
                inventoryRepository.save(inventory);
                log.info("[KAFKA-EVENT] Stock updated on delivery — equipment={} base={} +{}",
                        equipmentId, event.getDestinationBaseId(), item.getQuantity());
            });
        }
    }
}
