package com.dmdev.dao;

import com.dmdev.entity.Department;
import jakarta.persistence.EntityManager;

import java.util.UUID;

public class DepartmentRepository extends RepositoryBase<UUID, Department> {

    public DepartmentRepository(final EntityManager entityManager) {
        super(Department.class, entityManager);
    }
}
