package com.logistics.mlogistics.repository;

import com.logistics.mlogistics.domain.Inventory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, UUID> {

    @EntityGraph(attributePaths = {"equipment", "base", "unit"})
    List<Inventory> findAll();

    @EntityGraph(attributePaths = {"equipment", "base", "unit"})
    Optional<Inventory> findById(UUID id);

    Optional<Inventory> findByEquipmentIdAndBaseId(UUID equipmentId, UUID baseId);
}
