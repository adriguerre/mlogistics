package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.EquipmentCategory;
import com.logistics.mlogistics.repository.EquipmentCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EquipmentCategoryService {

    private final EquipmentCategoryRepository repository;

    @Autowired
    public EquipmentCategoryService(EquipmentCategoryRepository repository) {
        this.repository = repository;
    }

    public List<EquipmentCategory> getAll() {
        return repository.findAll();
    }

    public Optional<EquipmentCategory> getById(UUID id) {
        return repository.findById(id);
    }

    public EquipmentCategory create(EquipmentCategory entity) {
        return repository.save(entity);
    }

    public Optional<EquipmentCategory> update(UUID id, EquipmentCategory updated) {
        return repository.findById(id).map(existing -> {
            if (updated.getCode() != null) existing.setCode(updated.getCode());
            if (updated.getName() != null) existing.setName(updated.getName());
            if (updated.getDescription() != null) existing.setDescription(updated.getDescription());
            return repository.save(existing);
        });
    }

    public boolean delete(UUID id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
