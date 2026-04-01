package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.Base;
import com.logistics.mlogistics.domain.Shipment;
import com.logistics.mlogistics.domain.SupplyOrder;
import com.logistics.mlogistics.domain.enums.ShipmentStatus;
import com.logistics.mlogistics.kafka.event.SupplyOrderApprovedEvent;
import com.logistics.mlogistics.repository.ShipmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShipmentService {

    private static final Logger log = LoggerFactory.getLogger(ShipmentService.class);
    private final ShipmentRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

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

    @Transactional
    public Shipment create(Shipment entity) {
        Shipment saved = repository.saveAndFlush(entity);
        entityManager.refresh(saved);
        return saved;
    }

    public Optional<Shipment> update(UUID id, Shipment updated) {
        return repository.findById(id).map(existing -> {
            if (updated.getTrackingCode() != null) existing.setTrackingCode(updated.getTrackingCode());
            if (updated.getOrder() != null) existing.setOrder(updated.getOrder());
            if (updated.getOriginBase() != null) existing.setOriginBase(updated.getOriginBase());
            if (updated.getDestinationBase() != null) existing.setDestinationBase(updated.getDestinationBase());
            if (updated.getVehicle() != null) existing.setVehicle(updated.getVehicle());
            if (updated.getDriver() != null) existing.setDriver(updated.getDriver());
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

    @Transactional
    public void createShipmentFromSupplyOrderApproval(SupplyOrderApprovedEvent event) {
        SupplyOrder order = new SupplyOrder();
        order.setId(event.getOrderId());

        Base destinationBase = new Base();
        destinationBase.setId(event.getDestinationBase());

        Shipment shipment = new Shipment();
        shipment.setTrackingCode("SHIP-" + event.getOrderId().toString().substring(0, 8).toUpperCase());
        shipment.setOrder(order);
        shipment.setDestinationBase(destinationBase);
        shipment.setStatus(ShipmentStatus.PREPARING);
        if (event.getEstimated_arrival_at() != null) {
            shipment.setEstimatedArrivalAt(new java.sql.Timestamp(event.getEstimated_arrival_at().getTime()));
        }

        create(shipment);
        log.info("[KAFKA-EVENT] Shipment created from approved supply order — tracking={} order={}", event.getTrackingCode(), event.getOrderId());
    }
}
