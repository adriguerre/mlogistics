package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.MissionResource;
import com.logistics.mlogistics.repository.MissionResourceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MissionResourceService {

    private final MissionResourceRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public MissionResourceService(MissionResourceRepository repository) {
        this.repository = repository;
    }

    public List<MissionResource> getAll() {
        return repository.findAll();
    }

    public Optional<MissionResource> getById(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public MissionResource create(MissionResource entity) {
        MissionResource saved = repository.saveAndFlush(entity);
        entityManager.refresh(saved);
        return saved;
    }

    public Optional<MissionResource> update(UUID id, MissionResource updated) {
        return repository.findById(id).map(existing -> {
            if (updated.getMission() != null) existing.setMission(updated.getMission());
            if (updated.getUnit() != null) existing.setUnit(updated.getUnit());
            if (updated.getEquipment() != null) existing.setEquipment(updated.getEquipment());
            if (updated.getVehicle() != null) existing.setVehicle(updated.getVehicle());
            if (updated.getQuantity() != null) existing.setQuantity(updated.getQuantity());
            if (updated.getStatus() != null) existing.setStatus(updated.getStatus());
            return repository.save(existing);
        });
    }

    public boolean delete(UUID id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
