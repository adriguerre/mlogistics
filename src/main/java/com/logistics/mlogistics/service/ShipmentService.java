package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.Base;
import com.logistics.mlogistics.domain.Equipment;
import com.logistics.mlogistics.domain.Shipment;
import com.logistics.mlogistics.domain.ShipmentItem;
import com.logistics.mlogistics.domain.SupplyOrder;
import com.logistics.mlogistics.domain.enums.ItemCondition;
import com.logistics.mlogistics.domain.enums.ShipmentStatus;
import com.logistics.mlogistics.repository.ShipmentItemRepository;
import com.logistics.mlogistics.kafka.event.ShipmentDeliveredEvent;
import com.logistics.mlogistics.kafka.event.SupplyOrderApprovedEvent;
import com.logistics.mlogistics.kafka.producer.ShipmentEventProducer;
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
    private final ShipmentItemRepository shipmentItemRepository;
    private final ShipmentEventProducer eventProducer;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ShipmentService(ShipmentRepository repository, ShipmentItemRepository shipmentItemRepository,
                           ShipmentEventProducer eventProducer) {
        this.repository = repository;
        this.shipmentItemRepository = shipmentItemRepository;
        this.eventProducer = eventProducer;
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
            Shipment saved = repository.save(existing);

            if (ShipmentStatus.DELIVERED.equals(saved.getStatus()) && saved.getDestinationBase() != null) {
                eventProducer.sendShipmentDelivered(new ShipmentDeliveredEvent(
                        saved.getId(),
                        saved.getDestinationBase().getId()
                ));
            }

            return saved;
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
        shipment.setTrackingCode("SHIP-" + event.getOrderId().toString().substring(30).toUpperCase());
        shipment.setOrder(order);
        shipment.setDestinationBase(destinationBase);
        shipment.setStatus(ShipmentStatus.PREPARING);
        if (event.getEstimated_arrival_at() != null) {
            shipment.setEstimatedArrivalAt(new java.sql.Timestamp(event.getEstimated_arrival_at().getTime()));
        }

        Shipment saved = create(shipment);

        if (event.getEquipmentId() != null && event.getQtyNeeded() != null) {
            Equipment equipment = new Equipment();
            equipment.setId(event.getEquipmentId());

            ShipmentItem item = new ShipmentItem();
            item.setShipment(saved);
            item.setEquipment(equipment);
            item.setQuantity(event.getQtyNeeded());
            item.setCondition(ItemCondition.NEW);
            shipmentItemRepository.save(item);
        }

        log.info("[KAFKA-EVENT] Shipment created from approved supply order — tracking={} order={}", saved.getTrackingCode(), event.getOrderId());
    }
}
