package com.logistics.mlogistics.repository;

import com.logistics.mlogistics.domain.SupplyOrderItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SupplyOrderItemRepository extends JpaRepository<SupplyOrderItem, UUID> {

    @EntityGraph(attributePaths = {"order", "equipment"})
    List<SupplyOrderItem> findAll();

    @EntityGraph(attributePaths = {"order", "equipment"})
    Optional<SupplyOrderItem> findById(UUID id);

    @EntityGraph(attributePaths = {"order", "equipment"})
    List<SupplyOrderItem> findByOrderId(UUID orderId);
}
