package com.logistics.mlogistics.repository;

import com.logistics.mlogistics.domain.Mission;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MissionRepository extends JpaRepository<Mission, UUID> {

    @EntityGraph(attributePaths = {"commandingUnit", "base"})
    List<Mission> findAll();

    @EntityGraph(attributePaths = {"commandingUnit", "base"})
    Optional<Mission> findById(UUID id);
}
