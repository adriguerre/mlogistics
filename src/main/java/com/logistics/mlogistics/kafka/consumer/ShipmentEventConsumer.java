package com.logistics.mlogistics.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logistics.mlogistics.kafka.config.KafkaTopicConfig;
import com.logistics.mlogistics.kafka.event.ShipmentDeliveredEvent;
import com.logistics.mlogistics.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ShipmentEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(ShipmentEventConsumer.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final InventoryService inventoryService;

    public ShipmentEventConsumer(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(topics = KafkaTopicConfig.SHIPMENT_DELIVERED, groupId = "mlogistics-group")
    public void onShipmentDelivered(String message) {
        try {
            ShipmentDeliveredEvent event = objectMapper.readValue(message, ShipmentDeliveredEvent.class);
            log.info("[KAFKA-EVENT] Shipment delivered received — shipment={} destinationBase={}",
                    event.getShipmentId(), event.getDestinationBaseId());
            inventoryService.updateStockOnDelivery(event);
        } catch (Exception e) {
            log.error("[KAFKA] Failed to process ShipmentDeliveredEvent: {}", message, e);
        }
    }

    @KafkaListener(topics = KafkaTopicConfig.SHIPMENT_DELIVERED + "-dlt", groupId = "mlogistics-dlt-group")
    public void handleDeadLetter(String message) {
        log.error("[KAFKA-EVENT-DLQ] Message could not be processed after retries: {}", message);
    }
}
