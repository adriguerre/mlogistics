package com.logistics.mlogistics.repository;

import com.logistics.mlogistics.domain.KafkaEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface KafkaEventRepository extends JpaRepository<KafkaEvent, UUID> {

    List<KafkaEvent> findTop5ByOrderByDateDesc();

}
