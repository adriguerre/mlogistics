package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.Mission;
import com.logistics.mlogistics.repository.MissionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MissionService {

    private final MissionRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public MissionService(MissionRepository repository) {
        this.repository = repository;
    }

    public List<Mission> getAll() {
        return repository.findAll();
    }

    public Optional<Mission> getById(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public Mission create(Mission entity) {
        Mission saved = repository.saveAndFlush(entity);
        entityManager.refresh(saved);
        return saved;
    }

    public Optional<Mission> update(UUID id, Mission updated) {
        return repository.findById(id).map(existing -> {
            if (updated.getCodename() != null) existing.setCodename(updated.getCodename());
            if (updated.getCommandingUnit() != null) existing.setCommandingUnit(updated.getCommandingUnit());
            if (updated.getBase() != null) existing.setBase(updated.getBase());
            if (updated.getClassification() != null) existing.setClassification(updated.getClassification());
            if (updated.getStatus() != null) existing.setStatus(updated.getStatus());
            if (updated.getTargetLatitude() != null) existing.setTargetLatitude(updated.getTargetLatitude());
            if (updated.getTargetLongitude() != null) existing.setTargetLongitude(updated.getTargetLongitude());
            if (updated.getStartAt() != null) existing.setStartAt(updated.getStartAt());
            if (updated.getEndAt() != null) existing.setEndAt(updated.getEndAt());
            if (updated.getObjective() != null) existing.setObjective(updated.getObjective());
            return repository.save(existing);
        });
    }

    public boolean delete(UUID id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
