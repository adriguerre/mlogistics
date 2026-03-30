package com.logistics.mlogistics.repository;

import com.logistics.mlogistics.domain.MissionResource;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MissionResourceRepository extends JpaRepository<MissionResource, UUID> {

    @EntityGraph(attributePaths = {"mission", "unit", "equipment", "vehicle"})
    List<MissionResource> findAll();

    @EntityGraph(attributePaths = {"mission", "unit", "equipment", "vehicle"})
    Optional<MissionResource> findById(UUID id);
}
