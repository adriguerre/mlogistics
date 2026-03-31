package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.Equipment;
import com.logistics.mlogistics.repository.EquipmentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EquipmentService {

    private final EquipmentRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public EquipmentService(EquipmentRepository repository) {
        this.repository = repository;
    }

    public List<Equipment> getAll() {
        return repository.findAll();
    }

    public Optional<Equipment> getById(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public Equipment create(Equipment entity) {
        Equipment saved = repository.saveAndFlush(entity);
        entityManager.refresh(saved);
        return saved;
    }

    public Optional<Equipment> update(UUID id, Equipment updated) {
        return repository.findById(id).map(existing -> {
            if (updated.getSku() != null) existing.setSku(updated.getSku());
            if (updated.getName() != null) existing.setName(updated.getName());
            if (updated.getModel() != null) existing.setModel(updated.getModel());
            if (updated.getManufacturer() != null) existing.setManufacturer(updated.getManufacturer());
            if (updated.getCategory() != null) existing.setCategory(updated.getCategory());
            if (updated.getUnitWeightKg() != null) existing.setUnitWeightKg(updated.getUnitWeightKg());
            if (updated.getUnitCostUsd() != null) existing.setUnitCostUsd(updated.getUnitCostUsd());
            if (updated.getClassification() != null) existing.setClassification(updated.getClassification());
            if (updated.getRequiresMaintenance() != null) existing.setRequiresMaintenance(updated.getRequiresMaintenance());
            if (updated.getMaintenanceIntervalDays() != null) existing.setMaintenanceIntervalDays(updated.getMaintenanceIntervalDays());
            return repository.save(existing);
        });
    }

    public boolean delete(UUID id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
