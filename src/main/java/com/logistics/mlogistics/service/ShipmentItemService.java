package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.ShipmentItem;
import com.logistics.mlogistics.repository.ShipmentItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShipmentItemService {

    private final ShipmentItemRepository repository;

    @Autowired
    public ShipmentItemService(ShipmentItemRepository repository) {
        this.repository = repository;
    }

    public List<ShipmentItem> getAll() {
        return repository.findAll();
    }

    public Optional<ShipmentItem> getById(UUID id) {
        return repository.findById(id);
    }

    public ShipmentItem create(ShipmentItem entity) {
        return repository.save(entity);
    }

    public Optional<ShipmentItem> update(UUID id, ShipmentItem updated) {
        return repository.findById(id).map(existing -> {
            if (updated.getShipmentId() != null) existing.setShipmentId(updated.getShipmentId());
            if (updated.getEquipmentId() != null) existing.setEquipmentId(updated.getEquipmentId());
            if (updated.getQuantity() != null) existing.setQuantity(updated.getQuantity());
            if (updated.getCondition() != null) existing.setCondition(updated.getCondition());
            return repository.save(existing);
        });
    }

    public boolean delete(UUID id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
