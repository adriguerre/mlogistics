package com.logistics.mlogistics.domain;

import com.logistics.mlogistics.domain.enums.UnitType;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "unit")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "code", nullable = false, unique = true, length = 30)
    private String code;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private UnitType type;

    @Column(name = "size_headcount")
    private Integer sizeHeadcount;

    @Column(name = "parent_unit_id")
    private UUID parentUnitId;

    @Column(name = "home_base_id", nullable = false)
    private UUID homeBaseId;

    @Column(name = "commander_id")
    private UUID commanderId;

    @Column(name = "created_at", nullable = false, updatable = false)
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
    public UUID getHomeBaseId() { return homeBaseId; }
    public void setHomeBaseId(UUID homeBaseId) { this.homeBaseId = homeBaseId; }
    public UUID getCommanderId() { return commanderId; }
    public void setCommanderId(UUID commanderId) { this.commanderId = commanderId; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
