package com.logistics.mlogistics.domain;

import com.logistics.mlogistics.domain.enums.KafkaEventType;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "kafka_events")
public class KafkaEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public UUID id;

    @Column(name = "event_type")
    @Enumerated(EnumType.STRING)
    public KafkaEventType eventType;

    @Column(name = "message")
    public String message;

    @Column(name = "date")
    public Timestamp date;

    public KafkaEvent() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public KafkaEventType getEventType() {
        return eventType;
    }

    public void setEventType(KafkaEventType eventType) {
        this.eventType = eventType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
