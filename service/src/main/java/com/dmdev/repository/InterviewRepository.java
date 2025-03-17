package com.dmdev.repository;

import com.dmdev.entity.Interview;
import jakarta.persistence.EntityManager;

import java.util.UUID;

public class InterviewRepository extends RepositoryBase<UUID, Interview> {

    public InterviewRepository(EntityManager entityManager) {
        super(Interview.class, entityManager);
    }
}
