package com.logistics.mlogistics.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Timestamp;

@Entity
public class Base {

    @Id
    @JsonProperty("base_id")
    @Column(name = "base_id")
    private Integer baseId;

    private String code;
    private String name;
    private String country;
    private String region;
    @JsonProperty("lat")
    @Column(name = "lat")
    private Double latitude;
    @JsonProperty("long")
    @Column(name = "long")
    private Double longitude;
    private String status;
    private Integer commanding_unit_id;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Base() {
    }

    public Integer getBaseId() {
        return baseId;
    }

    public void setBaseId(Integer baseId) {
        this.baseId = baseId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCommanding_unit_id() {
        return commanding_unit_id;
    }

    public void setCommanding_unit_id(Integer commanding_unit_id) {
        this.commanding_unit_id = commanding_unit_id;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
