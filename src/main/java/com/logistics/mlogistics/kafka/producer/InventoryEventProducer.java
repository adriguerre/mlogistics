package com.logistics.mlogistics.kafka.producer;

import com.logistics.mlogistics.kafka.config.KafkaTopicConfig;
import com.logistics.mlogistics.kafka.event.LowStockEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventProducer {

    private static final Logger log = LoggerFactory.getLogger(InventoryEventProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public InventoryEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendLowStockAlert(LowStockEvent event) {
        kafkaTemplate.send(KafkaTopicConfig.INVENTORY_LOW_STOCK, event.getInventoryId().toString(), event);
        log.info("[KAFKA-EVENT|] Low stock event sent — inventory={} equipment='{}' qty={} threshold={}",
                event.getInventoryId(), event.getEquipmentName(), event.getQtyAvailable(), event.getReorderThreshold());
    }
}
