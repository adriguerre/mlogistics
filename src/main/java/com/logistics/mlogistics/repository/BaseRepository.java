package com.logistics.mlogistics.repository;

import com.logistics.mlogistics.domain.Base;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BaseRepository extends JpaRepository<Base, UUID> {

    @EntityGraph(attributePaths = {"units", "units.commanderId", "commandingUnit", "commandingUnit.commanderId"})
    List<Base> findAll();

    @EntityGraph(attributePaths = {"units", "units.commanderId", "commandingUnit", "commandingUnit.commanderId"})
    Optional<Base> findById(UUID id);
}
