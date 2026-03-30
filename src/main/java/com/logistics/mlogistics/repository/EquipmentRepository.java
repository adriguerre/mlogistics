package com.logistics.mlogistics.repository;

import com.logistics.mlogistics.domain.Equipment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, UUID> {

    @EntityGraph(attributePaths = {"category"})
    List<Equipment> findAll();

    @EntityGraph(attributePaths = {"category"})
    Optional<Equipment> findById(UUID id);
}
