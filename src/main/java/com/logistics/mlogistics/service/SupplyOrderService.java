package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.SupplyOrder;
import com.logistics.mlogistics.repository.SupplyOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SupplyOrderService {

    private final SupplyOrderRepository repository;

    @Autowired
    public SupplyOrderService(SupplyOrderRepository repository) {
        this.repository = repository;
    }

    public List<SupplyOrder> getAll() {
        return repository.findAll();
    }

    public Optional<SupplyOrder> getById(UUID id) {
        return repository.findById(id);
    }

    public SupplyOrder create(SupplyOrder entity) {
        return repository.save(entity);
    }

    public Optional<SupplyOrder> update(UUID id, SupplyOrder updated) {
        return repository.findById(id).map(existing -> {
            if (updated.getOrderNumber() != null) existing.setOrderNumber(updated.getOrderNumber());
            if (updated.getRequestingUnitId() != null) existing.setRequestingUnitId(updated.getRequestingUnitId());
            if (updated.getRequestingBaseId() != null) existing.setRequestingBaseId(updated.getRequestingBaseId());
            if (updated.getSupplierId() != null) existing.setSupplierId(updated.getSupplierId());
            if (updated.getApprovedBy() != null) existing.setApprovedBy(updated.getApprovedBy());
            if (updated.getStatus() != null) existing.setStatus(updated.getStatus());
            if (updated.getPriority() != null) existing.setPriority(updated.getPriority());
            if (updated.getRequiredBy() != null) existing.setRequiredBy(updated.getRequiredBy());
            if (updated.getTotalCostUsd() != null) existing.setTotalCostUsd(updated.getTotalCostUsd());
            if (updated.getNotes() != null) existing.setNotes(updated.getNotes());
            return repository.save(existing);
        });
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
