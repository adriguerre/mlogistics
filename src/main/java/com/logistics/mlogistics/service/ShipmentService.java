package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.Shipment;
import com.logistics.mlogistics.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShipmentService {

    private final ShipmentRepository repository;

    @Autowired
    public ShipmentService(ShipmentRepository repository) {
        this.repository = repository;
    }

    public List<Shipment> getAll() {
        return repository.findAll();
    }

    public Optional<Shipment> getById(UUID id) {
        return repository.findById(id);
    }

    public Shipment create(Shipment entity) {
        return repository.save(entity);
    }

    public Optional<Shipment> update(UUID id, Shipment updated) {
        return repository.findById(id).map(existing -> {
            if (updated.getTrackingCode() != null) existing.setTrackingCode(updated.getTrackingCode());
            if (updated.getOrderId() != null) existing.setOrderId(updated.getOrderId());
            if (updated.getOriginBaseId() != null) existing.setOriginBaseId(updated.getOriginBaseId());
            if (updated.getDestinationBaseId() != null) existing.setDestinationBaseId(updated.getDestinationBaseId());
            if (updated.getVehicleId() != null) existing.setVehicleId(updated.getVehicleId());
            if (updated.getDriverId() != null) existing.setDriverId(updated.getDriverId());
            if (updated.getStatus() != null) existing.setStatus(updated.getStatus());
            if (updated.getTotalWeightKg() != null) existing.setTotalWeightKg(updated.getTotalWeightKg());
            if (updated.getDispatchedAt() != null) existing.setDispatchedAt(updated.getDispatchedAt());
            if (updated.getEstimatedArrivalAt() != null) existing.setEstimatedArrivalAt(updated.getEstimatedArrivalAt());
            if (updated.getDeliveredAt() != null) existing.setDeliveredAt(updated.getDeliveredAt());
            return repository.save(existing);
        });
    }

    public boolean delete(UUID id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
