package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.Base;
import com.logistics.mlogistics.domain.Personnel;
import com.logistics.mlogistics.domain.Rank;
import com.logistics.mlogistics.domain.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.logistics.mlogistics.dto.personnel.PersonnelRequest;
import com.logistics.mlogistics.dto.personnel.PersonnelResponse;
import com.logistics.mlogistics.repository.PersonnelRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonnelService {

    private final PersonnelRepository personnelRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public PersonnelService(PersonnelRepository personnelRepository) {
        this.personnelRepository = personnelRepository;
    }

    public Page<PersonnelResponse> getAll(Pageable pageable) {
        return personnelRepository.findAll(pageable).map(this::toResponse);
    }

    public Optional<PersonnelResponse> getById(UUID id) {
        return personnelRepository.findById(id).map(this::toResponse);
    }

    @Transactional
    public PersonnelResponse create(PersonnelRequest request) {
        Personnel saved = personnelRepository.saveAndFlush(toEntity(request));
        entityManager.refresh(saved);
        return toResponse(saved);
    }

    public Optional<PersonnelResponse> update(UUID id, PersonnelRequest request) {
        return personnelRepository.findById(id).map(existing -> {
            if (request.getServiceId() != null) existing.setServiceId(request.getServiceId());
            if (request.getFirstName() != null) existing.setFirstName(request.getFirstName());
            if (request.getLastName() != null) existing.setLastName(request.getLastName());
            if (request.getRankId() != null) existing.setRank(entityManager.getReference(Rank.class, request.getRankId()));
            if (request.getUnitId() != null) existing.setUnit(entityManager.getReference(Unit.class, request.getUnitId()));
            if (request.getBaseId() != null) existing.setBase(entityManager.getReference(Base.class, request.getBaseId()));
            if (request.getStatus() != null) existing.setStatus(request.getStatus());
            return toResponse(personnelRepository.save(existing));
        });
    }

    public boolean delete(UUID id) {
        if (!personnelRepository.existsById(id)) return false;
        personnelRepository.deleteById(id);
        return true;
    }

    private PersonnelResponse toResponse(Personnel p) {
        PersonnelResponse r = new PersonnelResponse();
        r.setId(p.getId());
        r.setServiceId(p.getServiceId());
        r.setFirstName(p.getFirstName());
        r.setLastName(p.getLastName());
        r.setStatus(p.getStatus() != null ? p.getStatus().name() : null);
        r.setCreatedAt(p.getCreatedAt());
        if (p.getRank() != null) r.setRankTitle(p.getRank().getTitle());
        if (p.getUnit() != null) r.setUnitName(p.getUnit().getName());
        if (p.getBase() != null) r.setBaseName(p.getBase().getName());
        return r;
    }

    private Personnel toEntity(PersonnelRequest req) {
        Personnel p = new Personnel();
        p.setServiceId(req.getServiceId());
        p.setFirstName(req.getFirstName());
        p.setLastName(req.getLastName());
        if (req.getRankId() != null) p.setRank(entityManager.getReference(Rank.class, req.getRankId()));
        if (req.getUnitId() != null) p.setUnit(entityManager.getReference(Unit.class, req.getUnitId()));
        if (req.getBaseId() != null) p.setBase(entityManager.getReference(Base.class, req.getBaseId()));
        if (req.getStatus() != null) p.setStatus(req.getStatus());
        return p;
    }
}
