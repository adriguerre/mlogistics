package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.Personnel;
import com.logistics.mlogistics.repository.PersonnelRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonnelService {

    private final PersonnelRepository personnelRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public PersonnelService(PersonnelRepository personnelRepository) {
        this.personnelRepository = personnelRepository;
    }

    public List<Personnel> getAll() {
        return personnelRepository.findAll();
    }

    public Optional<Personnel> getById(UUID id) {
        return personnelRepository.findById(id);
    }

    @Transactional
    public Personnel create(Personnel personnel) {
        Personnel saved = personnelRepository.saveAndFlush(personnel);
        entityManager.refresh(saved);
        return saved;
    }

    public Optional<Personnel> update(UUID id, Personnel updated) {
        return personnelRepository.findById(id).map(existing -> {
            if (updated.getServiceId() != null) existing.setServiceId(updated.getServiceId());
            if (updated.getFirstName() != null) existing.setFirstName(updated.getFirstName());
            if (updated.getLastName() != null) existing.setLastName(updated.getLastName());
            if (updated.getRank() != null) existing.setRank(updated.getRank());
            if (updated.getUnit() != null) existing.setUnit(updated.getUnit());
            if (updated.getBase() != null) existing.setBase(updated.getBase());
            if (updated.getStatus() != null) existing.setStatus(updated.getStatus());
            return personnelRepository.save(existing);
        });
    }

    public boolean delete(UUID id) {
        if (!personnelRepository.existsById(id)) return false;
        personnelRepository.deleteById(id);
        return true;
    }
}
