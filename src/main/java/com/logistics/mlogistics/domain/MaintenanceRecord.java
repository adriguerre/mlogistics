package com.logistics.mlogistics.domain;

import com.logistics.mlogistics.domain.enums.MaintenanceStatus;
import com.logistics.mlogistics.domain.enums.MaintenanceType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "maintenance_record")
public class MaintenanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "equipment_id")
    private UUID equipmentId;

    @Column(name = "vehicle_id")
    private UUID vehicleId;

    @Column(name = "performed_by")
    private UUID performedBy;

    @Column(name = "base_id")
    private UUID baseId;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, columnDefinition = "maintenance_type")
    private MaintenanceType type;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "maintenance_status")
    private MaintenanceStatus status;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "cost_usd")
    private BigDecimal costUsd;

    @Column(name = "outcome", columnDefinition = "TEXT")
    private String outcome;

    @Column(name = "scheduled_at", nullable = false)
    private Timestamp scheduledAt;

    @Column(name = "completed_at")
    private Timestamp completedAt;

    public MaintenanceRecord() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getEquipmentId() { return equipmentId; }
    public void setEquipmentId(UUID equipmentId) { this.equipmentId = equipmentId; }

    public UUID getVehicleId() { return vehicleId; }
    public void setVehicleId(UUID vehicleId) { this.vehicleId = vehicleId; }

    public UUID getPerformedBy() { return performedBy; }
    public void setPerformedBy(UUID performedBy) { this.performedBy = performedBy; }

    public UUID getBaseId() { return baseId; }
    public void setBaseId(UUID baseId) { this.baseId = baseId; }

    public MaintenanceType getType() { return type; }
    public void setType(MaintenanceType type) { this.type = type; }

    public MaintenanceStatus getStatus() { return status; }
    public void setStatus(MaintenanceStatus status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getCostUsd() { return costUsd; }
    public void setCostUsd(BigDecimal costUsd) { this.costUsd = costUsd; }

    public String getOutcome() { return outcome; }
    public void setOutcome(String outcome) { this.outcome = outcome; }

    public Timestamp getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(Timestamp scheduledAt) { this.scheduledAt = scheduledAt; }

    public Timestamp getCompletedAt() { return completedAt; }
    public void setCompletedAt(Timestamp completedAt) { this.completedAt = completedAt; }
}
