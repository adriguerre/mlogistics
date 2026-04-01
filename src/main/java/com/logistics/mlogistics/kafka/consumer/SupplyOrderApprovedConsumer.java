package com.logistics.mlogistics.kafka.consumer;

import com.logistics.mlogistics.kafka.config.KafkaTopicConfig;
import com.logistics.mlogistics.kafka.event.SupplyOrderApprovedEvent;
import com.logistics.mlogistics.service.ShipmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class SupplyOrderApprovedConsumer {
    private static final Logger log = LoggerFactory.getLogger(SupplyOrderApprovedConsumer.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ShipmentService shipmentService;
    public SupplyOrderApprovedConsumer(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @KafkaListener(topics = KafkaTopicConfig.SUPPLY_ORDER_APPROVED, groupId = "supply-order-approved-group")
    public void supplyOrderApproved(String message){
      try{
          SupplyOrderApprovedEvent event = objectMapper.readValue(message, SupplyOrderApprovedEvent.class);
          log.warn("[ALERT KAFKA-EVENT Supply Order APPROVED - ORDER ID: {} - DESTINATION: {}", event.getOrderId(), event.getDestinationBase());
          shipmentService.createShipmentFromSupplyOrderApproval(event);
      }catch(Exception e){
          log.error("[KAFKA-EVENT-CONSUMER] Failed to deserialize supplyOrderApprovedEvent: {}", message, e);
      }
    }
}
