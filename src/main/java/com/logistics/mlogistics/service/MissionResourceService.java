package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.MissionResource;
import com.logistics.mlogistics.repository.MissionResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MissionResourceService {

    private final MissionResourceRepository repository;

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

    public MissionResource create(MissionResource entity) {
        return repository.save(entity);
    }

    public Optional<MissionResource> update(UUID id, MissionResource updated) {
        return repository.findById(id).map(existing -> {
            if (updated.getMissionId() != null) existing.setMissionId(updated.getMissionId());
            if (updated.getUnitId() != null) existing.setUnitId(updated.getUnitId());
            if (updated.getEquipmentId() != null) existing.setEquipmentId(updated.getEquipmentId());
            if (updated.getVehicleId() != null) existing.setVehicleId(updated.getVehicleId());
            if (updated.getQuantity() != null) existing.setQuantity(updated.getQuantity());
            if (updated.getStatus() != null) existing.setStatus(updated.getStatus());
            return repository.save(existing);
        });
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
