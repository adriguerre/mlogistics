package com.logistics.mlogistics.repository;

import com.logistics.mlogistics.domain.Shipment;
import com.logistics.mlogistics.domain.enums.ShipmentStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, UUID> {

    @EntityGraph(attributePaths = {"order", "originBase", "destinationBase", "vehicle", "driver"})
    List<Shipment> findAll();

    @EntityGraph(attributePaths = {"order", "originBase", "destinationBase", "vehicle", "driver"})
    Optional<Shipment> findById(UUID id);

    List<Shipment> findByStatusAndEstimatedArrivalAtBefore(ShipmentStatus status, Timestamp now);

    @Modifying
    @Transactional
    @Query("UPDATE Shipment s SET s.status = :newStatus WHERE s.status = :currentStatus AND s.estimatedArrivalAt < :now")
    int markOverdueShipmentsAsDelayed(@Param("currentStatus") ShipmentStatus currentStatus,
                                      @Param("newStatus") ShipmentStatus newStatus,
                                      @Param("now") Timestamp now);
}
