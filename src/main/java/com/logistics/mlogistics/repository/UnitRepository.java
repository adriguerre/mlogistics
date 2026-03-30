package com.logistics.mlogistics.repository;

import com.logistics.mlogistics.domain.Unit;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UnitRepository extends JpaRepository<Unit, UUID> {

    @EntityGraph(attributePaths = {"homeBase", "commanderId"})
    List<Unit> findAll();

    @EntityGraph(attributePaths = {"homeBase", "commanderId"})
    Optional<Unit> findById(UUID id);
}
