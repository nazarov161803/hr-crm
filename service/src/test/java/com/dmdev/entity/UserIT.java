package com.dmdev.entity;

import com.dmdev.utils.HibernateTestUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;

public class UserIT {

    private Session session;
    private final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();

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

    @Test
    void hrCreateCheck() {
        User user = buildHr();

        session.persist(user);
        session.getTransaction().commit();
        session.clear();

        final User actual = session.get(User.class, user.getId());
        assertThat(actual).isEqualTo(user);
    }

    @Test
    void hrUpdateCheck() {
        User user = buildHr();

        session.persist(user);
        user.setEmail("testEmail.com");
        session.merge(user);
        session.getTransaction().commit();
        session.clear();

        final User actual = session.get(User.class, user.getId());
        assertThat(actual.getEmail()).isEqualTo("testEmail.com");

    }

    @Test
    void hrRemoveCheck() {
        User user = buildHr();

        session.persist(user);
        session.remove(user);
        session.getTransaction().commit();
        session.clear();

        assertNull(session.get(User.class, user.getId()));

    }

    @Test
    void hrWithDepartmentCheck() {

        Department department = Department.builder()
                .name("IT Department")
                .description("Handles IT solutions")
                .build();

        User hr = buildHr();
        hr.setDepartment(department);

        session.persist(department);
        session.persist(hr);
        session.getTransaction().commit();
        session.clear();

        User retrievedHr = session.get(User.class, hr.getId());
        assertThat(retrievedHr.getDepartment()).isNotNull();
        assertThat(retrievedHr.getDepartment().getName()).isEqualTo("IT Department");

    }

    @Test
    void hrWithCandidates() {
        User hr = buildHr();
        Candidate candidateOne = Candidate.builder()
                .firstName("Petr")
                .lastName("Petrov")
                .email("petr@com")
                .status(Status.OPEN)
                .hr(hr)
                .desiredPosition("Developer")
                .build();
        Candidate candidateTwo = Candidate.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .email("ivan@com")
                .status(Status.HIRED)
                .hr(hr)
                .desiredPosition("Developer")
                .build();
        final List<Candidate> candidates = Arrays.asList(candidateOne, candidateTwo);
        hr.setCandidates(candidates);

        session.persist(candidateOne);
        session.persist(candidateTwo);
        session.persist(hr);
        session.getTransaction().commit();
        session.clear();

        User retrievedHr = session.get(User.class, hr.getId());
        assertThat(retrievedHr.getCandidates()).isNotNull();
        assertThat(retrievedHr.getCandidates().size()).isEqualTo(2);
        assertThat(retrievedHr.getCandidates()).containsAll(candidates);

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