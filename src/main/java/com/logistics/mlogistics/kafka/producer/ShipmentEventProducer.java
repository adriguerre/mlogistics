package com.logistics.mlogistics.kafka.producer;

import com.logistics.mlogistics.kafka.config.KafkaTopicConfig;
import com.logistics.mlogistics.kafka.event.ShipmentDeliveredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ShipmentEventProducer {

    private static final Logger log = LoggerFactory.getLogger(ShipmentEventProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ShipmentEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendShipmentDelivered(ShipmentDeliveredEvent event) {
        kafkaTemplate.send(KafkaTopicConfig.SHIPMENT_DELIVERED, event.getShipmentId().toString(), event);
        log.info("[KAFKA-EVENT] Shipment delivered event sent — shipment={} destinationBase={}",
                event.getShipmentId(), event.getDestinationBaseId());
    }
}
