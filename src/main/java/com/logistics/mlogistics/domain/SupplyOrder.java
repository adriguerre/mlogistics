package com.logistics.mlogistics.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
@JsonPropertyOrder({"id", "order_number", "priority", "notes", "status", "total_cost_usd", "created_at", "required_by", "supplier", "requesting_base", "requesting_unit", "approved_by"})
public class SupplyOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "order_number", nullable = false, unique = true, length = 60)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requesting_unit_id")
    @JsonIgnoreProperties({"home_base", "commander", "units"})
    private Unit requestingUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requesting_base_id")
    @JsonIgnoreProperties({"commanding_unit", "units"})
    private Base requestingBase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by")
    @JsonIgnoreProperties({"unit", "base"})
    private Personnel approvedBy;

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

    public SupplyOrder(String orderNumber, OrderStatus status, OrderPriority priority, Date requiredBy, String notes,
                       Base baseToSupply) {
        this.orderNumber = orderNumber;
        this.status = status;
        this.priority = priority;
        this.requiredBy = requiredBy;
        this.notes = notes;
        this.requestingBase = baseToSupply;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
    public Unit getRequestingUnit() { return requestingUnit; }
    public void setRequestingUnit(Unit requestingUnit) { this.requestingUnit = requestingUnit; }
    public Base getRequestingBase() { return requestingBase; }
    public void setRequestingBase(Base requestingBase) { this.requestingBase = requestingBase; }
    public Supplier getSupplier() { return supplier; }
    public void setSupplier(Supplier supplier) { this.supplier = supplier; }
    public Personnel getApprovedBy() { return approvedBy; }
    public void setApprovedBy(Personnel approvedBy) { this.approvedBy = approvedBy; }
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
