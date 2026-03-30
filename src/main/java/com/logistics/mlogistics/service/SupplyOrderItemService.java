package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.SupplyOrderItem;
import com.logistics.mlogistics.repository.SupplyOrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SupplyOrderItemService {

    private final SupplyOrderItemRepository repository;

    @Autowired
    public SupplyOrderItemService(SupplyOrderItemRepository repository) {
        this.repository = repository;
    }

    public List<SupplyOrderItem> getAll() {
        return repository.findAll();
    }

    public Optional<SupplyOrderItem> getById(UUID id) {
        return repository.findById(id);
    }

    public SupplyOrderItem create(SupplyOrderItem entity) {
        return repository.save(entity);
    }

    public Optional<SupplyOrderItem> update(UUID id, SupplyOrderItem updated) {
        return repository.findById(id).map(existing -> {
            if (updated.getOrder() != null) existing.setOrder(updated.getOrder());
            if (updated.getEquipment() != null) existing.setEquipment(updated.getEquipment());
            if (updated.getQtyRequested() != null) existing.setQtyRequested(updated.getQtyRequested());
            if (updated.getQtyApproved() != null) existing.setQtyApproved(updated.getQtyApproved());
            if (updated.getUnitPriceUsd() != null) existing.setUnitPriceUsd(updated.getUnitPriceUsd());
            return repository.save(existing);
        });
    }

    public boolean delete(UUID id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
