package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.Vehicle;
import com.logistics.mlogistics.repository.VehicleRepository;
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
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Page<Vehicle> getAll(Pageable pageable) {
        return vehicleRepository.findAll(pageable);
    }

    public Optional<Vehicle> getById(UUID id) {
        return vehicleRepository.findById(id);
    }

    @Transactional
    public Vehicle create(Vehicle vehicle) {
        Vehicle saved = vehicleRepository.saveAndFlush(vehicle);
        entityManager.refresh(saved);
        return saved;
    }

    public Optional<Vehicle> update(UUID id, Vehicle updated) {
        return vehicleRepository.findById(id).map(existing -> {
            if (updated.getPlate() != null) existing.setPlate(updated.getPlate());
            if (updated.getModel() != null) existing.setModel(updated.getModel());
            if (updated.getType() != null) existing.setType(updated.getType());
            if (updated.getBase() != null) existing.setBase(updated.getBase());
            if (updated.getUnit() != null) existing.setUnit(updated.getUnit());
            if (updated.getCurrentBase() != null) existing.setCurrentBase(updated.getCurrentBase());
            if (updated.getMaxPayloadKg() != null) existing.setMaxPayloadKg(updated.getMaxPayloadKg());
            if (updated.getStatus() != null) existing.setStatus(updated.getStatus());
            if (updated.getPassengerCapacity() != null) existing.setPassengerCapacity(updated.getPassengerCapacity());
            return vehicleRepository.save(existing);
        });
    }

    public boolean delete(UUID id) {
        if (!vehicleRepository.existsById(id)) return false;
        vehicleRepository.deleteById(id);
        return true;
    }
}
