package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.Inventory;
import com.logistics.mlogistics.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InventoryService {

    private final InventoryRepository repository;

    @Autowired
    public InventoryService(InventoryRepository repository) {
        this.repository = repository;
    }

    public List<Inventory> getAll() {
        return repository.findAll();
    }

    public Optional<Inventory> getById(UUID id) {
        return repository.findById(id);
    }

    public Inventory create(Inventory entity) {
        return repository.save(entity);
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
            return repository.save(existing);
        });
    }

    public boolean delete(UUID id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
