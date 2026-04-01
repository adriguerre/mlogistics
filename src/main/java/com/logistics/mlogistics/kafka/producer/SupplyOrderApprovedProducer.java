package com.logistics.mlogistics.kafka.producer;

import com.logistics.mlogistics.kafka.config.KafkaTopicConfig;
import com.logistics.mlogistics.kafka.event.SupplyOrderApprovedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SupplyOrderApprovedProducer {

    private static final Logger log = LoggerFactory.getLogger(SupplyOrderApprovedProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public SupplyOrderApprovedProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendSupplyOrderApprovedAlert(SupplyOrderApprovedEvent event){
        kafkaTemplate.send(KafkaTopicConfig.SUPPLY_ORDER_APPROVED, event.getOrderId().toString(), event);
        log.info("[KAFKA-EVENT-PRODUCER] Supply Order REQUESTED - ID:[{}] approved, creating shipment", event.getOrderId());
    }
}
