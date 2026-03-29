package com.logistics.mlogistics.domain;

import com.logistics.mlogistics.domain.enums.BranchType;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "rank")
public class Rank {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "code", nullable = false, unique = true, length = 20)
    private String code;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    @Column(name = "branch", nullable = false, columnDefinition = "branch_type")
    private BranchType branch;

    @Column(name = "precedence", nullable = false)
    private Short precedence;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private Timestamp createdAt;

    public Rank() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public BranchType getBranch() { return branch; }
    public void setBranch(BranchType branch) { this.branch = branch; }
    public Short getPrecedence() { return precedence; }
    public void setPrecedence(Short precedence) { this.precedence = precedence; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
