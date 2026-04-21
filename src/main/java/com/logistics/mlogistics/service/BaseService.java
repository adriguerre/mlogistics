package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.Base;
import com.logistics.mlogistics.repository.BaseRepository;
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
public class BaseService {

    private final BaseRepository baseRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public BaseService(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }

    public Page<Base> getAll(Pageable pageable) {
        return baseRepository.findAll(pageable);
    }

    public Optional<Base> getById(UUID id) {
        return baseRepository.findById(id);
    }

    @Transactional
    public Base create(Base base) {
        Base saved = baseRepository.saveAndFlush(base);
        entityManager.refresh(saved);
        return saved;
    }

    public Optional<Base> update(UUID id, Base updated) {
        return baseRepository.findById(id).map(existing -> {
            if (updated.getCode() != null) existing.setCode(updated.getCode());
            if (updated.getName() != null) existing.setName(updated.getName());
            if (updated.getCountry() != null) existing.setCountry(updated.getCountry());
            if (updated.getRegion() != null) existing.setRegion(updated.getRegion());
            if (updated.getLatitude() != null) existing.setLatitude(updated.getLatitude());
            if (updated.getLongitude() != null) existing.setLongitude(updated.getLongitude());
            if (updated.getStatus() != null) existing.setStatus(updated.getStatus());
            if (updated.getCommandingUnit() != null) existing.setCommandingUnit(updated.getCommandingUnit());
            if (updated.getEquipmentSlots() != null) existing.setEquipmentSlots(updated.getEquipmentSlots());
            if (updated.getFuelCapacityL() != null) existing.setFuelCapacityL(updated.getFuelCapacityL());
            if (updated.getUpdatedAt() != null) existing.setUpdatedAt(updated.getUpdatedAt());
            return baseRepository.save(existing);
        });
    }

    public boolean delete(UUID id) {
        if (!baseRepository.existsById(id)) return false;
        baseRepository.deleteById(id);
        return true;
    }
}
