package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.KafkaEvent;
import com.logistics.mlogistics.repository.KafkaEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaEventService {

    private KafkaEventRepository kafkaEventRepository;

    @Autowired
    public KafkaEventService(KafkaEventRepository kafkaEventRepository) {
        this.kafkaEventRepository = kafkaEventRepository;
    }

    public List<KafkaEvent> getAllKafkaEvents(){
        return kafkaEventRepository.findAll();
    }

    public List<KafkaEvent> getFiveLastestEvents(){
        return kafkaEventRepository.findTop5ByOrderByDateDesc();
    }

    public Boolean createKafkaEvent(KafkaEvent entity){
        if(entity != null){
            kafkaEventRepository.save(entity);
            return true;
        }

        return false;
    }

}
