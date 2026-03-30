package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.MaintenanceRecord;
import com.logistics.mlogistics.repository.MaintenanceRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MaintenanceRecordService {

    private final MaintenanceRecordRepository repository;

    @Autowired
    public MaintenanceRecordService(MaintenanceRecordRepository repository) {
        this.repository = repository;
    }

    public List<MaintenanceRecord> getAll() {
        return repository.findAll();
    }

    public Optional<MaintenanceRecord> getById(UUID id) {
        return repository.findById(id);
    }

    public MaintenanceRecord create(MaintenanceRecord entity) {
        return repository.save(entity);
    }

    public Optional<MaintenanceRecord> update(UUID id, MaintenanceRecord updated) {
        return repository.findById(id).map(existing -> {
            if (updated.getEquipment() != null) existing.setEquipment(updated.getEquipment());
            if (updated.getVehicle() != null) existing.setVehicle(updated.getVehicle());
            if (updated.getPerformer() != null) existing.setPerformer(updated.getPerformer());
            if (updated.getBase() != null) existing.setBase(updated.getBase());
            if (updated.getType() != null) existing.setType(updated.getType());
            if (updated.getStatus() != null) existing.setStatus(updated.getStatus());
            if (updated.getDescription() != null) existing.setDescription(updated.getDescription());
            if (updated.getCostUsd() != null) existing.setCostUsd(updated.getCostUsd());
            if (updated.getOutcome() != null) existing.setOutcome(updated.getOutcome());
            if (updated.getScheduledAt() != null) existing.setScheduledAt(updated.getScheduledAt());
            if (updated.getCompletedAt() != null) existing.setCompletedAt(updated.getCompletedAt());
            return repository.save(existing);
        });
    }

    public boolean delete(UUID id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
