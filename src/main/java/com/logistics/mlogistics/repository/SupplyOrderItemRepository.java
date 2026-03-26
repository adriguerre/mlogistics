package com.logistics.mlogistics.repository;

import com.logistics.mlogistics.domain.SupplyOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SupplyOrderItemRepository extends JpaRepository<SupplyOrderItem, UUID> {
}
