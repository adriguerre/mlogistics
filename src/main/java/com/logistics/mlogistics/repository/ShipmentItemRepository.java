package com.logistics.mlogistics.repository;

import com.logistics.mlogistics.domain.ShipmentItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShipmentItemRepository extends JpaRepository<ShipmentItem, UUID> {

    @EntityGraph(attributePaths = {"shipment", "equipment"})
    List<ShipmentItem> findAll();

    @EntityGraph(attributePaths = {"shipment", "equipment"})
    Optional<ShipmentItem> findById(UUID id);

    @EntityGraph(attributePaths = {"shipment", "equipment"})
    List<ShipmentItem> findByShipmentId(UUID shipmentId);
}
