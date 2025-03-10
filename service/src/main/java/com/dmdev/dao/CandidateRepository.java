package com.dmdev.dao;

import com.dmdev.entity.Candidate;
import jakarta.persistence.EntityManager;

import java.util.UUID;

public class CandidateRepository extends RepositoryBase<UUID, Candidate> {

    public CandidateRepository(final EntityManager entityManager) {
        super(Candidate.class, entityManager);
    }
}
