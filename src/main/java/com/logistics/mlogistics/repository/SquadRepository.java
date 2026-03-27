package com.logistics.mlogistics.repository;

import com.logistics.mlogistics.domain.Squad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SquadRepository extends JpaRepository<Squad, UUID> {
}
