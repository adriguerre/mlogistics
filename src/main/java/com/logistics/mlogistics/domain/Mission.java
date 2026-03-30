package com.logistics.mlogistics.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.logistics.mlogistics.domain.enums.MissionClassification;
import com.logistics.mlogistics.domain.enums.MissionStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;
import org.hibernate.annotations.DynamicInsert;
import java.sql.Timestamp;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@DynamicInsert
@Table(name = "mission")
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "codename", nullable = false, unique = true, length = 100)
    private String codename;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commanding_unit_id")
    @JsonIgnoreProperties({"home_base", "commander", "units"})
    private Unit commandingUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_id")
    @JsonIgnoreProperties({"commanding_unit", "units"})
    private Base base;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    @Column(name = "classification", nullable = false, columnDefinition = "mission_classification")
    private MissionClassification classification;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "mission_status")
    private MissionStatus status;

    @Column(name = "target_latitude")
    private Double targetLatitude;

    @Column(name = "target_longitude")
    private Double targetLongitude;

    @Column(name = "start_at")
    private Timestamp startAt;

    @Column(name = "end_at")
    private Timestamp endAt;

    @Column(name = "objective", columnDefinition = "TEXT")
    private String objective;

    @Generated(event = EventType.INSERT)
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private Timestamp createdAt;

    public Mission() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getCodename() { return codename; }
    public void setCodename(String codename) { this.codename = codename; }

    public Unit getCommandingUnit() { return commandingUnit; }
    public void setCommandingUnit(Unit commandingUnit) { this.commandingUnit = commandingUnit; }

    public Base getBase() { return base; }
    public void setBase(Base base) { this.base = base; }

    public MissionClassification getClassification() { return classification; }
    public void setClassification(MissionClassification classification) { this.classification = classification; }

    public MissionStatus getStatus() { return status; }
    public void setStatus(MissionStatus status) { this.status = status; }

    public Double getTargetLatitude() { return targetLatitude; }
    public void setTargetLatitude(Double targetLatitude) { this.targetLatitude = targetLatitude; }

    public Double getTargetLongitude() { return targetLongitude; }
    public void setTargetLongitude(Double targetLongitude) { this.targetLongitude = targetLongitude; }

    public Timestamp getStartAt() { return startAt; }
    public void setStartAt(Timestamp startAt) { this.startAt = startAt; }

    public Timestamp getEndAt() { return endAt; }
    public void setEndAt(Timestamp endAt) { this.endAt = endAt; }

    public String getObjective() { return objective; }
    public void setObjective(String objective) { this.objective = objective; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
