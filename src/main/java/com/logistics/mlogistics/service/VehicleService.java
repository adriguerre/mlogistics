package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.Vehicle;
import com.logistics.mlogistics.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> getAll() {
        return vehicleRepository.findAll();
    }

    public Optional<Vehicle> getById(UUID id) {
        return vehicleRepository.findById(id);
    }

    public Vehicle create(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Optional<Vehicle> update(UUID id, Vehicle updated) {
        return vehicleRepository.findById(id).map(existing -> {
            if (updated.getPlate() != null) existing.setPlate(updated.getPlate());
            if (updated.getModel() != null) existing.setModel(updated.getModel());
            if (updated.getType() != null) existing.setType(updated.getType());
            if (updated.getBaseId() != null) existing.setBaseId(updated.getBaseId());
            if (updated.getUnitId() != null) existing.setUnitId(updated.getUnitId());
            if (updated.getCurrentBaseId() != null) existing.setCurrentBaseId(updated.getCurrentBaseId());
            if (updated.getMaxPayloadKg() != null) existing.setMaxPayloadKg(updated.getMaxPayloadKg());
            if (updated.getStatus() != null) existing.setStatus(updated.getStatus());
            return vehicleRepository.save(existing);
        });
    }

    public void delete(UUID id) {
        vehicleRepository.deleteById(id);
    }
}
