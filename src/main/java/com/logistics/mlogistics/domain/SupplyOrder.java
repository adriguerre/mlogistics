package com.logistics.mlogistics.domain;

import com.logistics.mlogistics.domain.enums.OrderPriority;
import com.logistics.mlogistics.domain.enums.OrderStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;
import org.hibernate.annotations.DynamicInsert;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@DynamicInsert
@Table(name = "supply_order")
public class SupplyOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "order_number", nullable = false, unique = true, length = 60)
    private String orderNumber;

    @Column(name = "requesting_unit_id")
    private UUID requestingUnitId;

    @Column(name = "requesting_base_id")
    private UUID requestingBaseId;

    @Column(name = "supplier_id", nullable = false)
    private UUID supplierId;

    @Column(name = "approved_by")
    private UUID approvedBy;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "order_status")
    private OrderStatus status;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, columnDefinition = "order_priority")
    private OrderPriority priority;

    @Column(name = "required_by")
    private Date requiredBy;

    @Column(name = "total_cost_usd")
    private BigDecimal totalCostUsd;

    @Column(name = "notes")
    private String notes;

    @Generated(event = EventType.INSERT)
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private Timestamp createdAt;

    public SupplyOrder() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
    public UUID getRequestingUnitId() { return requestingUnitId; }
    public void setRequestingUnitId(UUID requestingUnitId) { this.requestingUnitId = requestingUnitId; }
    public UUID getRequestingBaseId() { return requestingBaseId; }
    public void setRequestingBaseId(UUID requestingBaseId) { this.requestingBaseId = requestingBaseId; }
    public UUID getSupplierId() { return supplierId; }
    public void setSupplierId(UUID supplierId) { this.supplierId = supplierId; }
    public UUID getApprovedBy() { return approvedBy; }
    public void setApprovedBy(UUID approvedBy) { this.approvedBy = approvedBy; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public OrderPriority getPriority() { return priority; }
    public void setPriority(OrderPriority priority) { this.priority = priority; }
    public Date getRequiredBy() { return requiredBy; }
    public void setRequiredBy(Date requiredBy) { this.requiredBy = requiredBy; }
    public BigDecimal getTotalCostUsd() { return totalCostUsd; }
    public void setTotalCostUsd(BigDecimal totalCostUsd) { this.totalCostUsd = totalCostUsd; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
