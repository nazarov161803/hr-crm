package com.dmdev.dao;

import com.dmdev.entity.User;
import jakarta.persistence.EntityManager;

import java.util.UUID;

public class UserRepository extends RepositoryBase<UUID, User> {

    public UserRepository(final EntityManager entityManager) {
        super(User.class, entityManager);
    }
}
