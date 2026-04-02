package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.MaintenanceRecord;
import com.logistics.mlogistics.repository.MaintenanceRecordRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MaintenanceRecordService {

    private final MaintenanceRecordRepository maintenanceRecordRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public MaintenanceRecordService(MaintenanceRecordRepository maintenanceRecordRepository) {
        this.maintenanceRecordRepository = maintenanceRecordRepository;
    }

    public List<MaintenanceRecord> getAll() {
        return maintenanceRecordRepository.findAll();
    }

    public Optional<MaintenanceRecord> getById(UUID id) {
        return maintenanceRecordRepository.findById(id);
    }

    @Transactional
    public MaintenanceRecord create(MaintenanceRecord entity) {
        MaintenanceRecord saved = maintenanceRecordRepository.saveAndFlush(entity);
        entityManager.refresh(saved);
        return saved;
    }

    public Optional<MaintenanceRecord> update(UUID id, MaintenanceRecord updated) {
        return maintenanceRecordRepository.findById(id).map(existing -> {
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
            return maintenanceRecordRepository.save(existing);
        });
    }

    public boolean delete(UUID id) {
        if (!maintenanceRecordRepository.existsById(id)) return false;
        maintenanceRecordRepository.deleteById(id);
        return true;
    }
}
