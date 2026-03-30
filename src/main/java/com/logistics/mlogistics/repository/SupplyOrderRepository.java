package com.logistics.mlogistics.repository;

import com.logistics.mlogistics.domain.SupplyOrder;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SupplyOrderRepository extends JpaRepository<SupplyOrder, UUID> {

    @EntityGraph(attributePaths = {"requestingUnit", "requestingBase", "supplier", "approvedBy"})
    List<SupplyOrder> findAll();

    @EntityGraph(attributePaths = {"requestingUnit", "requestingBase", "supplier", "approvedBy"})
    Optional<SupplyOrder> findById(UUID id);
}
