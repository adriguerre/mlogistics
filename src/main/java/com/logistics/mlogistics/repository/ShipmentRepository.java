package com.logistics.mlogistics.repository;

import com.logistics.mlogistics.domain.Shipment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, UUID> {

    @EntityGraph(attributePaths = {"order", "originBase", "destinationBase", "vehicle", "driver"})
    List<Shipment> findAll();

    @EntityGraph(attributePaths = {"order", "originBase", "destinationBase", "vehicle", "driver"})
    Optional<Shipment> findById(UUID id);
}
