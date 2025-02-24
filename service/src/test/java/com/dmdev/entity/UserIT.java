package com.dmdev.entity;

import com.dmdev.utils.HibernateTestUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;

public class UserIT {

    @Test
    public void hrCreateCheck() {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            User user = buildHr();
            session.beginTransaction();
            session.persist(user);

            assertThat(session.get(User.class, user.getId())).isEqualTo(user);
        }
    }

    @Test
    public void hrUpdateCheck() {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            User user = buildHr();
            session.beginTransaction();
            session.persist(user);
            user.setEmail("testEmail.com");
            session.merge(user);

            assertThat(session.get(User.class, user.getId()).getEmail()).isEqualTo("testEmail.com");
        }
    }

    @Test
    public void hrRemoveCheck() {
        Configuration configuration = new Configuration();
        configuration.configure();
        User user = buildHr();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            session.persist(user);
            session.remove(user);

            assertNull(session.get(User.class, user.getId()));
        }
    }

    @Test
    public void hrWithDepartmentCheck() {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            Department department = Department.builder()
                    .name("IT Department")
                    .description("Handles IT solutions")
                    .build();

            User hr = buildHr();
            hr.setDepartment(department);

            session.persist(department);
            session.beginTransaction();
            session.persist(hr);

            User retrievedHr = session.get(User.class, hr.getId());
            assertThat(retrievedHr.getDepartment()).isNotNull();
            assertThat(retrievedHr.getDepartment().getName()).isEqualTo("IT Department");

        }
    }

    @Test
    public void hrWithCandidates() {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            Candidate candidateOne = Candidate.builder()
                    .firstName("Petr")
                    .lastName("Petrov")
                    .email("petr@com")
                    .status(Status.OPEN)
                    .desiredPosition("Developer")
                    .build();

            Candidate candidateTwo = Candidate.builder()
                    .firstName("Ivan")
                    .lastName("Ivanov")
                    .email("ivan@com")
                    .status(Status.HIRED)
                    .desiredPosition("Developer")
                    .build();

            User hr = buildHr();
            final List<Candidate> candidates = Arrays.asList(candidateOne, candidateTwo);
            hr.setCandidates(candidates);

            session.beginTransaction();
            session.persist(candidateOne);
            session.persist(candidateTwo);
            session.persist(hr);
            session.getTransaction().commit();

            User retrievedHr = session.get(User.class, hr.getId());
            assertThat(retrievedHr.getCandidates()).isNotNull();
            assertThat(retrievedHr.getCandidates().size()).isEqualTo(2);
            assertThat(retrievedHr.getCandidates()).containsExactlyElementsOf(candidates);

        }
    }

    private User buildHr() {
        return User.builder()
                .email("User@gmail1.com")
                .password("12345")
                .firstName("Melani")
                .lastName("Trump")
                .role(Role.HR)
                .build();
    }
}