package com.dmdev.dao;

import com.dmdev.entity.Interview;
import jakarta.persistence.EntityManager;

import java.util.UUID;

public class InterviewRepository extends RepositoryBase<UUID, Interview> {

    public InterviewRepository(final EntityManager entityManager) {
        super(Interview.class, entityManager);
    }
}
