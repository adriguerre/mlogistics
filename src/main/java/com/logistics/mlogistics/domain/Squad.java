package com.logistics.mlogistics.domain;

import com.logistics.mlogistics.domain.enums.SquadType;
import jakarta.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;
import java.sql.Timestamp;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@DynamicInsert
@Table(name = "squad")
public class Squad {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, columnDefinition = "squad_type")
    private SquadType type;

    @Column(name = "unit_id")
    private UUID unitId;

    @Column(name = "personnel_count", nullable = false)
    private Integer personnelCount;

    @Column(name = "current_base_id")
    private UUID currentBaseId;

    @Column(name = "current_vehicle_id")
    private UUID currentVehicleId;

    @Generated(event = EventType.INSERT)
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, insertable = false)
    private Timestamp updatedAt;

    public Squad() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public SquadType getType() { return type; }
    public void setType(SquadType type) { this.type = type; }
    public UUID getUnitId() { return unitId; }
    public void setUnitId(UUID unitId) { this.unitId = unitId; }
    public Integer getPersonnelCount() { return personnelCount; }
    public void setPersonnelCount(Integer personnelCount) { this.personnelCount = personnelCount; }
    public UUID getCurrentBaseId() { return currentBaseId; }
    public void setCurrentBaseId(UUID currentBaseId) { this.currentBaseId = currentBaseId; }
    public UUID getCurrentVehicleId() { return currentVehicleId; }
    public void setCurrentVehicleId(UUID currentVehicleId) { this.currentVehicleId = currentVehicleId; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}
