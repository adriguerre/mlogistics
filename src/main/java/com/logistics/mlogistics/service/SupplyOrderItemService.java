package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.Equipment;
import com.logistics.mlogistics.domain.SupplyOrder;
import com.logistics.mlogistics.domain.SupplyOrderItem;
import com.logistics.mlogistics.kafka.event.LowStockEvent;
import com.logistics.mlogistics.repository.SupplyOrderItemRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SupplyOrderItemService {

    private final SupplyOrderItemRepository supplyOrderItemRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public SupplyOrderItemService(SupplyOrderItemRepository supplyOrderItemRepository) {
        this.supplyOrderItemRepository = supplyOrderItemRepository;
    }

    public List<SupplyOrderItem> getAll() {
        return supplyOrderItemRepository.findAll();
    }

    public Optional<SupplyOrderItem> getById(UUID id) {
        return supplyOrderItemRepository.findById(id);
    }

    @Transactional
    public SupplyOrderItem create(SupplyOrderItem entity) {
        SupplyOrderItem saved = supplyOrderItemRepository.saveAndFlush(entity);
        entityManager.refresh(saved);
        return saved;
    }

    public Optional<SupplyOrderItem> update(UUID id, SupplyOrderItem updated) {
        return supplyOrderItemRepository.findById(id).map(existing -> {
            if (updated.getOrder() != null) existing.setOrder(updated.getOrder());
            if (updated.getEquipment() != null) existing.setEquipment(updated.getEquipment());
            if (updated.getQtyRequested() != null) existing.setQtyRequested(updated.getQtyRequested());
            if (updated.getQtyApproved() != null) existing.setQtyApproved(updated.getQtyApproved());
            if (updated.getUnitPriceUsd() != null) existing.setUnitPriceUsd(updated.getUnitPriceUsd());
            return supplyOrderItemRepository.save(existing);
        });
    }

    @Transactional
    public void createSupplyOrderItem(LowStockEvent event, UUID orderId){
        Equipment equipment = new Equipment();
        equipment.setId(event.getEquipmentId());

        SupplyOrder supplyOrder = new SupplyOrder();
        supplyOrder.setId(orderId);

        SupplyOrderItem orderItem = new SupplyOrderItem();

        orderItem.setEquipment(equipment);
        orderItem.setOrder(supplyOrder);
        orderItem.setQtyRequested(event.getReorderThreshold() - event.getQtyAvailable());

        create(orderItem);
    }

    public boolean delete(UUID id) {
        if (!supplyOrderItemRepository.existsById(id)) return false;
        supplyOrderItemRepository.deleteById(id);
        return true;
    }
}
