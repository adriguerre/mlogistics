package com.logistics.mlogistics.repository;

import com.logistics.mlogistics.domain.Squad;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SquadRepository extends JpaRepository<Squad, UUID> {

    @EntityGraph(attributePaths = {"unit", "currentBase", "currentVehicle"})
    List<Squad> findAll();

    @EntityGraph(attributePaths = {"unit", "currentBase", "currentVehicle"})
    Optional<Squad> findById(UUID id);
}
