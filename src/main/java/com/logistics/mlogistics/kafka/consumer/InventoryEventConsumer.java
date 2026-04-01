package com.logistics.mlogistics.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logistics.mlogistics.kafka.config.KafkaTopicConfig;
import com.logistics.mlogistics.kafka.event.LowStockEvent;
import com.logistics.mlogistics.service.SupplyOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(InventoryEventConsumer.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final SupplyOrderService supplyOrderService;

    //Inyected, but not needed as spring will inyect if there is only one constructor
    public InventoryEventConsumer(SupplyOrderService supplyOrderService) {
        this.supplyOrderService = supplyOrderService;
    }
    @KafkaListener(topics = KafkaTopicConfig.INVENTORY_LOW_STOCK, groupId = "mlogistics-group")
    public void onLowStock(String message) {
        try {
            LowStockEvent event = objectMapper.readValue(message, LowStockEvent.class);
            log.warn("[ALERT KAFKA-EVENT] Low stock detected — equipment='{}' at base='{}' | available={} / threshold={}",
                    event.getEquipmentName(),
                    event.getBaseName(),
                    event.getQtyAvailable(),
                    event.getReorderThreshold());

            supplyOrderService.createSupplyOrderFromLowStockEvent(event);
        } catch (Exception e) {
            log.error("[KAFKA] Failed to deserialize LowStockEvent: {}", message, e);
        }
    }
}
