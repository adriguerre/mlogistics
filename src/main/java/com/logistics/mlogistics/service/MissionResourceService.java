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

    private final MissionResourceRepository missionResourceRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public MissionResourceService(MissionResourceRepository missionResourceRepository) {
        this.missionResourceRepository = missionResourceRepository;
    }

    public List<MissionResource> getAll() {
        return missionResourceRepository.findAll();
    }

    public Optional<MissionResource> getById(UUID id) {
        return missionResourceRepository.findById(id);
    }

    @Transactional
    public MissionResource create(MissionResource entity) {
        MissionResource saved = missionResourceRepository.saveAndFlush(entity);
        entityManager.refresh(saved);
        return saved;
    }

    public Optional<MissionResource> update(UUID id, MissionResource updated) {
        return missionResourceRepository.findById(id).map(existing -> {
            if (updated.getMission() != null) existing.setMission(updated.getMission());
            if (updated.getUnit() != null) existing.setUnit(updated.getUnit());
            if (updated.getEquipment() != null) existing.setEquipment(updated.getEquipment());
            if (updated.getVehicle() != null) existing.setVehicle(updated.getVehicle());
            if (updated.getQuantity() != null) existing.setQuantity(updated.getQuantity());
            if (updated.getStatus() != null) existing.setStatus(updated.getStatus());
            return missionResourceRepository.save(existing);
        });
    }

    public boolean delete(UUID id) {
        if (!missionResourceRepository.existsById(id)) return false;
        missionResourceRepository.deleteById(id);
        return true;
    }
}
