package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.Supplier;
import com.logistics.mlogistics.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SupplierService {

    private final SupplierRepository repository;

    @Autowired
    public SupplierService(SupplierRepository repository) {
        this.repository = repository;
    }

    public List<Supplier> getAll() {
        return repository.findAll();
    }

    public Optional<Supplier> getById(UUID id) {
        return repository.findById(id);
    }

    public Supplier create(Supplier entity) {
        return repository.save(entity);
    }

    public Optional<Supplier> update(UUID id, Supplier updated) {
        return repository.findById(id).map(existing -> {
            if (updated.getCode() != null) existing.setCode(updated.getCode());
            if (updated.getName() != null) existing.setName(updated.getName());
            if (updated.getType() != null) existing.setType(updated.getType());
            if (updated.getCountry() != null) existing.setCountry(updated.getCountry());
            if (updated.getIsApproved() != null) existing.setIsApproved(updated.getIsApproved());
            return repository.save(existing);
        });
    }

    public boolean delete(UUID id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
