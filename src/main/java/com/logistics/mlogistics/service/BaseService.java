package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.Base;
import com.logistics.mlogistics.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BaseService {

    private final BaseRepository baseRepository;

    @Autowired
    public BaseService(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }

    public List<Base> getAll() {
        return baseRepository.findAll();
    }

    public Optional<Base> getById(UUID id) {
        return baseRepository.findById(id);
    }

    public Base create(Base base) {
        return baseRepository.save(base);
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
            if (updated.getCommandingUnitId() != null) existing.setCommandingUnitId(updated.getCommandingUnitId());
            if (updated.getEquipmentSlots() != null) existing.setEquipmentSlots(updated.getEquipmentSlots());
            if (updated.getFuelCapacityL() != null) existing.setFuelCapacityL(updated.getFuelCapacityL());
            if (updated.getUpdatedAt() != null) existing.setUpdatedAt(updated.getUpdatedAt());
            return baseRepository.save(existing);
        });
    }

    public void delete(UUID id) {
        baseRepository.deleteById(id);
    }
}
