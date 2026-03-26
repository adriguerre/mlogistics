package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.Personnel;
import com.logistics.mlogistics.repository.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonnelService {

    private final PersonnelRepository personnelRepository;

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

    public Personnel create(Personnel personnel) {
        return personnelRepository.save(personnel);
    }

    public Optional<Personnel> update(UUID id, Personnel updated) {
        return personnelRepository.findById(id).map(existing -> {
            if (updated.getServiceId() != null) existing.setServiceId(updated.getServiceId());
            if (updated.getFirstName() != null) existing.setFirstName(updated.getFirstName());
            if (updated.getLastName() != null) existing.setLastName(updated.getLastName());
            if (updated.getRankId() != null) existing.setRankId(updated.getRankId());
            if (updated.getUnitId() != null) existing.setUnitId(updated.getUnitId());
            if (updated.getBaseId() != null) existing.setBaseId(updated.getBaseId());
            if (updated.getStatus() != null) existing.setStatus(updated.getStatus());
            return personnelRepository.save(existing);
        });
    }

    public void delete(UUID id) {
        personnelRepository.deleteById(id);
    }
}
