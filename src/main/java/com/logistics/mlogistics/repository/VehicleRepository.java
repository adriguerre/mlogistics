package com.logistics.mlogistics.repository;

import com.logistics.mlogistics.domain.Vehicle;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {

    @EntityGraph(attributePaths = {"base", "unit", "currentBase"})
    List<Vehicle> findAll();

    @EntityGraph(attributePaths = {"base", "unit", "currentBase"})
    Optional<Vehicle> findById(UUID id);
}
