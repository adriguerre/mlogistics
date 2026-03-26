package com.logistics.mlogistics.repository;

import com.logistics.mlogistics.domain.EquipmentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EquipmentCategoryRepository extends JpaRepository<EquipmentCategory, UUID> {
}
