package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.EquipmentCategory;
import com.logistics.mlogistics.repository.EquipmentCategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EquipmentCategoryService {

    private final EquipmentCategoryRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

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

    @Transactional
    public EquipmentCategory create(EquipmentCategory entity) {
        EquipmentCategory saved = repository.saveAndFlush(entity);
        entityManager.refresh(saved);
        return saved;
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
