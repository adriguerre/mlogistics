package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.Base;
import com.logistics.mlogistics.domain.SupplyOrder;
import com.logistics.mlogistics.domain.enums.OrderPriority;
import com.logistics.mlogistics.domain.enums.OrderStatus;
import com.logistics.mlogistics.kafka.consumer.InventoryEventConsumer;
import com.logistics.mlogistics.kafka.event.LowStockEvent;
import com.logistics.mlogistics.domain.Supplier;
import com.logistics.mlogistics.domain.Unit;
import com.logistics.mlogistics.repository.BaseRepository;
import com.logistics.mlogistics.repository.SupplyOrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SupplyOrderService {

    private final SupplyOrderRepository repository;
    private final BaseRepository baseRepository;
    private static final Logger log = LoggerFactory.getLogger(InventoryEventConsumer.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public SupplyOrderService(SupplyOrderRepository repository, BaseRepository baseRepository) {
        this.repository = repository;
        this.baseRepository = baseRepository;
    }

    public List<SupplyOrder> getAll() {
        return repository.findAll();
    }

    public Optional<SupplyOrder> getById(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public SupplyOrder create(SupplyOrder entity) {
        SupplyOrder saved = repository.saveAndFlush(entity);
        entityManager.refresh(saved);
        return saved;
    }

    public Optional<SupplyOrder> update(UUID id, SupplyOrder updated) {
        return repository.findById(id).map(existing -> {
            if (updated.getOrderNumber() != null) existing.setOrderNumber(updated.getOrderNumber());
            if (updated.getRequestingUnit() != null) existing.setRequestingUnit(updated.getRequestingUnit());
            if (updated.getRequestingBase() != null) existing.setRequestingBase(updated.getRequestingBase());
            if (updated.getSupplier() != null) existing.setSupplier(updated.getSupplier());
            if (updated.getApprovedBy() != null) existing.setApprovedBy(updated.getApprovedBy());
            if (updated.getStatus() != null) existing.setStatus(updated.getStatus());
            if (updated.getPriority() != null) existing.setPriority(updated.getPriority());
            if (updated.getRequiredBy() != null) existing.setRequiredBy(updated.getRequiredBy());
            if (updated.getTotalCostUsd() != null) existing.setTotalCostUsd(updated.getTotalCostUsd());
            if (updated.getNotes() != null) existing.setNotes(updated.getNotes());
            return repository.save(existing);
        });
    }

    public boolean delete(UUID id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

    @Transactional
    public void createSupplyOrderFromLowStockEvent(LowStockEvent event) {
        Base requestingBase = baseRepository.findById(event.getBaseId()).orElse(null);
        Unit requestingUnit = requestingBase != null ? requestingBase.getCommandingUnit() : null;

        Supplier defaultSupplier = new Supplier();
        defaultSupplier.setId(UUID.fromString("00000000-0000-0000-0000-000000000801"));

        SupplyOrder newSupplyOrder = new SupplyOrder("ORDER-LOW-STOCK", OrderStatus.DRAFT,
                OrderPriority.ROUTINE, new Date(2026, 4, 6),
                "Low Stock of " + event.getEquipmentName(), requestingBase);
        newSupplyOrder.setRequestingUnit(requestingUnit);
        newSupplyOrder.setSupplier(defaultSupplier);

        create(newSupplyOrder);
        log.info("New Supply Order Created [" + newSupplyOrder.getOrderNumber() + "]");
    }
}
