package com.dmdev.repository;

import com.dmdev.utils.HibernateTestUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.lang.reflect.Proxy;

public abstract class AbstractRepository {

    static final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();

    static final Session session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
            (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));

    @BeforeEach
    void setUp() {
        session.beginTransaction();
    }

    @AfterEach
    void tearDown() {
        session.getTransaction().rollback();
        session.close();
    }
}
