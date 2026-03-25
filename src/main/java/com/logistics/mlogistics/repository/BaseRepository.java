package com.logistics.mlogistics.repository;

import com.logistics.mlogistics.domain.Base;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseRepository extends JpaRepository<Base, Integer> {
}
