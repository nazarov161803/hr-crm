package com.dmdev.dao;

import com.dmdev.utils.HibernateTestUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class AbstractDao {

    Session session;
    static final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();

    @BeforeEach
    void setUp() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @AfterEach
    void tearDown() {
        if (session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }
        session.close();
    }
}
