package com.logistics.mlogistics.repository;

import com.logistics.mlogistics.domain.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RankRepository extends JpaRepository<Rank, UUID> {
}
