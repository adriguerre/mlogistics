package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.Squad;
import com.logistics.mlogistics.repository.SquadRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import java.util.UUID;

@Service
public class SquadService {

    private final SquadRepository squadRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public SquadService(SquadRepository squadRepository) {
        this.squadRepository = squadRepository;
    }

    public Page<Squad> getAll(Pageable pageable) {
        return squadRepository.findAll(pageable);
    }

    public Optional<Squad> getById(UUID id) {
        return squadRepository.findById(id);
    }

    @Transactional
    public Squad create(Squad squad) {
        Squad saved = squadRepository.saveAndFlush(squad);
        entityManager.refresh(saved);
        return saved;
    }

    public Optional<Squad> update(UUID id, Squad updated) {
        return squadRepository.findById(id).map(existing -> {
            if (updated.getName() != null) existing.setName(updated.getName());
            if (updated.getType() != null) existing.setType(updated.getType());
            if (updated.getUnit() != null) existing.setUnit(updated.getUnit());
            if (updated.getPersonnelCount() != null) existing.setPersonnelCount(updated.getPersonnelCount());
            if (updated.getCurrentBase() != null) existing.setCurrentBase(updated.getCurrentBase());
            if (updated.getCurrentVehicle() != null) existing.setCurrentVehicle(updated.getCurrentVehicle());
            return squadRepository.save(existing);
        });
    }

    public boolean delete(UUID id) {
        if (!squadRepository.existsById(id)) return false;
        squadRepository.deleteById(id);
        return true;
    }
}
