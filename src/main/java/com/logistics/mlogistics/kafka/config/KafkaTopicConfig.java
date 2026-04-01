package com.logistics.mlogistics.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public static final String INVENTORY_LOW_STOCK = "inventory.low-stock";
    public static final String SHIPMENT_DISPATCHED = "shipment.dispatched";
    public static final String SHIPMENT_DELIVERED = "shipment.delivered";
    public static final String SUPPLY_ORDER_APPROVED = "supply-orders.approved";

    @Bean
    public NewTopic inventoryLowStockTopic() {
        return TopicBuilder.name(INVENTORY_LOW_STOCK)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic supplyOrderApprovedTopic(){
        return TopicBuilder.name(SUPPLY_ORDER_APPROVED)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic shipmentDispatchedTopic() {
        return TopicBuilder.name(SHIPMENT_DISPATCHED).partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic shipmentDeliveredTopic() {
        return TopicBuilder.name(SHIPMENT_DELIVERED).partitions(1).replicas(1).build();
    }
}
