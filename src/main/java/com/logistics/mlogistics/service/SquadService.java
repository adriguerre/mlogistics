package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.Squad;
import com.logistics.mlogistics.repository.SquadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SquadService {

    private final SquadRepository squadRepository;

    @Autowired
    public SquadService(SquadRepository squadRepository) {
        this.squadRepository = squadRepository;
    }

    public List<Squad> getAll() {
        return squadRepository.findAll();
    }

    public Optional<Squad> getById(UUID id) {
        return squadRepository.findById(id);
    }

    public Squad create(Squad squad) {
        return squadRepository.save(squad);
    }

    public Optional<Squad> update(UUID id, Squad updated) {
        return squadRepository.findById(id).map(existing -> {
            if (updated.getName() != null) existing.setName(updated.getName());
            if (updated.getType() != null) existing.setType(updated.getType());
            if (updated.getUnitId() != null) existing.setUnitId(updated.getUnitId());
            if (updated.getPersonnelCount() != null) existing.setPersonnelCount(updated.getPersonnelCount());
            if (updated.getCurrentBaseId() != null) existing.setCurrentBaseId(updated.getCurrentBaseId());
            if (updated.getCurrentVehicleId() != null) existing.setCurrentVehicleId(updated.getCurrentVehicleId());
            return squadRepository.save(existing);
        });
    }

    public boolean delete(UUID id) {
        if (!squadRepository.existsById(id)) return false;
        squadRepository.deleteById(id);
        return true;
    }
}
