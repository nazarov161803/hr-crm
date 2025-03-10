package com.dmdev.dao;

import com.dmdev.entity.Vacancy;
import jakarta.persistence.EntityManager;

import java.util.UUID;

public class VacancyRepository extends RepositoryBase<UUID, Vacancy> {

    public VacancyRepository(final EntityManager entityManager) {
        super(Vacancy.class, entityManager);
    }
}
