package com.logistics.mlogistics.domain;

import com.logistics.mlogistics.domain.enums.MissionClassification;
import com.logistics.mlogistics.domain.enums.MissionStatus;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "mission")
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "codename", nullable = false, unique = true, length = 100)
    private String codename;

    @Column(name = "commanding_unit_id")
    private UUID commandingUnitId;

    @Column(name = "base_id")
    private UUID baseId;

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

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private Timestamp createdAt;

    public Mission() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getCodename() { return codename; }
    public void setCodename(String codename) { this.codename = codename; }

    public UUID getCommandingUnitId() { return commandingUnitId; }
    public void setCommandingUnitId(UUID commandingUnitId) { this.commandingUnitId = commandingUnitId; }

    public UUID getBaseId() { return baseId; }
    public void setBaseId(UUID baseId) { this.baseId = baseId; }

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
