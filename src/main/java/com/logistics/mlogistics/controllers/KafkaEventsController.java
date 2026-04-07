package com.logistics.mlogistics.controllers;

import com.logistics.mlogistics.domain.KafkaEvent;
import com.logistics.mlogistics.service.KafkaEventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kafka")
public class KafkaEventsController {

    private final KafkaEventService kafkaEventService;

    public KafkaEventsController(KafkaEventService kafkaEventService) {
        this.kafkaEventService = kafkaEventService;
    }


    @GetMapping
    public ResponseEntity<?> getRecentEvents(){
        List<KafkaEvent> kafkaEvents = kafkaEventService.getFiveLastestEvents();

        if(kafkaEvents.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        else
            return ResponseEntity.ok(kafkaEvents);
    }


    @PostMapping
    public ResponseEntity<?> createKafkaEvent(@RequestBody KafkaEvent kafkaEvent){
        Boolean created = kafkaEventService.createKafkaEvent(kafkaEvent);

        if(created)
            return ResponseEntity.status(HttpStatus.CREATED).build();
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
