package com.logistics.mlogistics.repository;

import com.logistics.mlogistics.domain.Personnel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonnelRepository extends JpaRepository<Personnel, UUID> {

    @EntityGraph(attributePaths = {"rank", "unit", "base"})
    List<Personnel> findAll();

    @EntityGraph(attributePaths = {"rank", "unit", "base"})
    Optional<Personnel> findById(UUID id);
}
