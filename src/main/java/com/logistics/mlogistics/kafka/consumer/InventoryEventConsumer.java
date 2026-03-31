package com.logistics.mlogistics.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logistics.mlogistics.kafka.config.KafkaTopicConfig;
import com.logistics.mlogistics.kafka.event.LowStockEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(InventoryEventConsumer.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = KafkaTopicConfig.INVENTORY_LOW_STOCK, groupId = "mlogistics-group")
    public void onLowStock(String message) {
        try {
            LowStockEvent event = objectMapper.readValue(message, LowStockEvent.class);
            log.warn("[ALERT KAFKA-EVENT] Low stock detected — equipment='{}' at base='{}' | available={} / threshold={}",
                    event.getEquipmentName(),
                    event.getBaseName(),
                    event.getQtyAvailable(),
                    event.getReorderThreshold());
        } catch (Exception e) {
            log.error("[KAFKA] Failed to deserialize LowStockEvent: {}", message, e);
        }
    }
}
