package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.Unit;
import com.logistics.mlogistics.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UnitService {

    private final UnitRepository unitRepository;

    @Autowired
    public UnitService(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public List<Unit> getAll() {
        return unitRepository.findAll();
    }

    public Optional<Unit> getById(UUID id) {
        return unitRepository.findById(id);
    }

    public Unit create(Unit unit) {
        return unitRepository.save(unit);
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
