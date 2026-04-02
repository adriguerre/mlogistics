package com.logistics.mlogistics.scheduler;

import com.logistics.mlogistics.service.ShipmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ShipmentScheduler {

    private static final Logger log = LoggerFactory.getLogger(ShipmentScheduler.class);
    private final ShipmentService shipmentService;

    public ShipmentScheduler(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @Scheduled(fixedDelay = 60000)
    public void detectDelayedShipments() {
        String timestamp = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        int updated = shipmentService.markOverdueAsDelayed();
        if (updated > 0) {
            log.info("[SCHEDULER] [{}] {} shipment(s) marked as DELAYED", timestamp, updated);
        } else {
            log.info("[SCHEDULER] [{}] There are no shipments to be delayed", timestamp);
        }
    }
}
