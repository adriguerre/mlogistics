package com.logistics.mlogistics.repository;

import com.logistics.mlogistics.domain.SupplyOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SupplyOrderRepository extends JpaRepository<SupplyOrder, UUID> {
}
