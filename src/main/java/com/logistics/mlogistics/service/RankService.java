package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.Rank;
import com.logistics.mlogistics.repository.RankRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import java.util.UUID;

@Service
public class RankService {

    private final RankRepository rankRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public RankService(RankRepository rankRepository) {
        this.rankRepository = rankRepository;
    }

    public Page<Rank> getAll(Pageable pageable) {
        return rankRepository.findAll(pageable);
    }

    public Optional<Rank> getById(UUID id) {
        return rankRepository.findById(id);
    }

    @Transactional
    public Rank create(Rank rank) {
        Rank saved = rankRepository.saveAndFlush(rank);
        entityManager.refresh(saved);
        return saved;
    }

    public Optional<Rank> update(UUID id, Rank updated) {
        return rankRepository.findById(id).map(existing -> {
            if (updated.getCode() != null) existing.setCode(updated.getCode());
            if (updated.getTitle() != null) existing.setTitle(updated.getTitle());
            if (updated.getBranch() != null) existing.setBranch(updated.getBranch());
            if (updated.getPrecedence() != null) existing.setPrecedence(updated.getPrecedence());
            return rankRepository.save(existing);
        });
    }

    public boolean delete(UUID id) {
        if (!rankRepository.existsById(id)) return false;
        rankRepository.deleteById(id);
        return true;
    }
}
