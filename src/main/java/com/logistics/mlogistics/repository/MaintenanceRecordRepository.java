package com.logistics.mlogistics.repository;

import com.logistics.mlogistics.domain.MaintenanceRecord;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MaintenanceRecordRepository extends JpaRepository<MaintenanceRecord, UUID> {

    @EntityGraph(attributePaths = {"equipment", "vehicle", "performer", "base"})
    List<MaintenanceRecord> findAll();

    @EntityGraph(attributePaths = {"equipment", "vehicle", "performer", "base"})
    Optional<MaintenanceRecord> findById(UUID id);
}
