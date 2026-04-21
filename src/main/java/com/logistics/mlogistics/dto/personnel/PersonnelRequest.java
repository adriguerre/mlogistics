package com.logistics.mlogistics.dto.personnel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.logistics.mlogistics.domain.enums.PersonnelStatus;
import java.util.UUID;

public class PersonnelRequest {

    @JsonProperty("service_id")
    private String serviceId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private UUID rankId;
    private UUID unitId;
    private UUID baseId;
    private PersonnelStatus status;

    public PersonnelRequest() {
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UUID getRankId() {
        return rankId;
    }

    public void setRankId(UUID rankId) {
        this.rankId = rankId;
    }

    public UUID getUnitId() {
        return unitId;
    }

    public void setUnitId(UUID unitId) {
        this.unitId = unitId;
    }

    public UUID getBaseId() {
        return baseId;
    }

    public void setBaseId(UUID baseId) {
        this.baseId = baseId;
    }

    public PersonnelStatus getStatus() {
        return status;
    }

    public void setStatus(PersonnelStatus status) {
        this.status = status;
    }
}
