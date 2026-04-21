package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.Unit;
import com.logistics.mlogistics.repository.UnitRepository;
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
public class UnitService {

    private final UnitRepository unitRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UnitService(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public Page<Unit> getAll(Pageable pageable) {
        return unitRepository.findAll(pageable);
    }

    public Optional<Unit> getById(UUID id) {
        return unitRepository.findById(id);
    }

    @Transactional
    public Unit create(Unit unit) {
        Unit saved = unitRepository.saveAndFlush(unit);
        entityManager.refresh(saved);
        return saved;
    }

    public Optional<Unit> update(UUID id, Unit updated) {
        return unitRepository.findById(id).map(existing -> {
            if (updated.getCode() != null) existing.setCode(updated.getCode());
            if (updated.getName() != null) existing.setName(updated.getName());
            if (updated.getType() != null) existing.setType(updated.getType());
            if (updated.getSizeHeadcount() != null) existing.setSizeHeadcount(updated.getSizeHeadcount());
            if (updated.getParentUnitId() != null) existing.setParentUnitId(updated.getParentUnitId());
            if (updated.getHomeBase() != null) existing.setHomeBase(updated.getHomeBase());
            if (updated.getCommanderId() != null) existing.setCommanderId(updated.getCommanderId());
            return unitRepository.save(existing);
        });
    }

    public boolean delete(UUID id) {
        if (!unitRepository.existsById(id)) return false;
        unitRepository.deleteById(id);
        return true;
    }
}
