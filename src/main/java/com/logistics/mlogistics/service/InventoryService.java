package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.Inventory;
import com.logistics.mlogistics.kafka.event.LowStockEvent;
import com.logistics.mlogistics.kafka.producer.InventoryEventProducer;
import com.logistics.mlogistics.repository.InventoryRepository;
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

    private final InventoryRepository repository;
    private final InventoryEventProducer eventProducer;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public InventoryService(InventoryRepository repository, InventoryEventProducer eventProducer) {
        this.repository = repository;
        this.eventProducer = eventProducer;
    }

    public List<Inventory> getAll() {
        return repository.findAll();
    }

    public Optional<Inventory> getById(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public Inventory create(Inventory entity) {
        Inventory saved = repository.saveAndFlush(entity);
        entityManager.refresh(saved);
        return saved;
    }

    public Optional<Inventory> update(UUID id, Inventory updated) {
        return repository.findById(id).map(existing -> {
            if (updated.getEquipment() != null) existing.setEquipment(updated.getEquipment());
            if (updated.getBase() != null) existing.setBase(updated.getBase());
            if (updated.getUnit() != null) existing.setUnit(updated.getUnit());
            if (updated.getQtyTotal() != null) existing.setQtyTotal(updated.getQtyTotal());
            if (updated.getQtyAvailable() != null) existing.setQtyAvailable(updated.getQtyAvailable());
            if (updated.getQtyReserved() != null) existing.setQtyReserved(updated.getQtyReserved());
            if (updated.getQtyDamaged() != null) existing.setQtyDamaged(updated.getQtyDamaged());
            if (updated.getReorderThreshold() != null) existing.setReorderThreshold(updated.getReorderThreshold());
            if (updated.getUpdatedAt() != null) existing.setUpdatedAt(updated.getUpdatedAt());
            Inventory saved = repository.save(existing);

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
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
