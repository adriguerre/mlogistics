package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.ShipmentItem;
import com.logistics.mlogistics.repository.ShipmentItemRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShipmentItemService {

    private final ShipmentItemRepository shipmentItemRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ShipmentItemService(ShipmentItemRepository shipmentItemRepository) {
        this.shipmentItemRepository = shipmentItemRepository;
    }

    public List<ShipmentItem> getAll() {
        return shipmentItemRepository.findAll();
    }

    public Optional<ShipmentItem> getById(UUID id) {
        return shipmentItemRepository.findById(id);
    }

    @Transactional
    public ShipmentItem create(ShipmentItem entity) {
        ShipmentItem saved = shipmentItemRepository.saveAndFlush(entity);
        entityManager.refresh(saved);
        return saved;
    }

    public Optional<ShipmentItem> update(UUID id, ShipmentItem updated) {
        return shipmentItemRepository.findById(id).map(existing -> {
            if (updated.getShipment() != null) existing.setShipment(updated.getShipment());
            if (updated.getEquipment() != null) existing.setEquipment(updated.getEquipment());
            if (updated.getQuantity() != null) existing.setQuantity(updated.getQuantity());
            if (updated.getCondition() != null) existing.setCondition(updated.getCondition());
            return shipmentItemRepository.save(existing);
        });
    }

    public boolean delete(UUID id) {
        if (!shipmentItemRepository.existsById(id)) return false;
        shipmentItemRepository.deleteById(id);
        return true;
    }
}
