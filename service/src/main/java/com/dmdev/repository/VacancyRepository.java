package com.dmdev.repository;

import com.dmdev.entity.Vacancy;
import jakarta.persistence.EntityManager;

import java.util.UUID;

public class VacancyRepository extends RepositoryBase<UUID, Vacancy> {

    public VacancyRepository(EntityManager entityManager) {
        super(Vacancy.class, entityManager);
    }
}
