package com.logistics.mlogistics.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.logistics.mlogistics.domain.enums.UnitType;
import jakarta.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;
import org.hibernate.annotations.DynamicInsert;
import java.sql.Timestamp;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@DynamicInsert
@Table(name = "unit")
@JsonPropertyOrder({"id", "code", "name", "type", "size_headcount", "created_at"})
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "code", nullable = false, unique = true, length = 30)
    private String code;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, columnDefinition = "unit_type")
    private UnitType type;

    @Column(name = "size_headcount")
    private Integer sizeHeadcount;

    @Column(name = "parent_unit_id")
    private UUID parentUnitId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "home_base_id", nullable = false)
    @JsonIgnoreProperties({"units", "commanding_unit"})
    @JsonProperty("home_base")
    private Base homeBase;

    @OneToOne
    @JoinColumn(name = "commander_id")
    @JsonProperty("commander")
    @JsonIgnoreProperties({"unit"})
    private Personnel commanderId;

    @Generated(event = EventType.INSERT)
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private Timestamp createdAt;

    public Unit() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public UnitType getType() { return type; }
    public void setType(UnitType type) { this.type = type; }
    public Integer getSizeHeadcount() { return sizeHeadcount; }
    public void setSizeHeadcount(Integer sizeHeadcount) { this.sizeHeadcount = sizeHeadcount; }
    public UUID getParentUnitId() { return parentUnitId; }
    public void setParentUnitId(UUID parentUnitId) { this.parentUnitId = parentUnitId; }
    public Base getHomeBase() { return homeBase; }
    public void setHomeBase(Base homeBase) { this.homeBase = homeBase; }

    public Personnel getCommanderId() {
        return commanderId;
    }

    public void setCommanderId(Personnel commanderId) {
        this.commanderId = commanderId;
    }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
