package com.dmdev.entity;

import com.dmdev.utils.HibernateTestUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;

public class CandidateIT {

    private final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
    private Session session;

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
    void candidateCreateCheck() {
        Candidate candidate = buildCandidate();
        session.persist(candidate);

        Candidate retrieved = session.get(Candidate.class, candidate.getId());
        assertThat(retrieved).isEqualTo(candidate);
    }

    @Test
    void candidateUpdateCheck() {
        Candidate candidate = buildCandidate();

        session.persist(candidate);
        candidate.setEmail("updatedCandidate@example.com");
        session.merge(candidate);
        session.getTransaction().commit();
        session.clear();

        Candidate updated = session.get(Candidate.class, candidate.getId());
        assertThat(updated.getEmail()).isEqualTo("updatedCandidate@example.com");

    }

    @Test
    void candidateRemoveCheck() {
        Candidate candidate = buildCandidate();

        session.persist(candidate);
        session.remove(candidate);
        session.getTransaction().commit();
        session.clear();

        Candidate removed = session.get(Candidate.class, candidate.getId());
        assertNull(removed);

    }

    @Test
    void candidateWithHrCheck() {
        User hr = User.builder()
                .email("hr@example.com")
                .password("pass")
                .firstName("HR")
                .lastName("Manager")
                .role(Role.HR)
                .build();

        Candidate candidate = buildCandidate();
        candidate.setHr(hr);

        session.persist(hr);
        session.persist(candidate);
        session.getTransaction().commit();
        session.clear();

        Candidate retrieved = session.get(Candidate.class, candidate.getId());
        assertThat(retrieved.getHr()).isNotNull();
        assertThat(retrieved.getHr().getEmail()).isEqualTo("hr@example.com");
    }

    private Candidate buildCandidate() {
        return Candidate.builder()
                .firstName("John")
                .lastName("Doe")
                .middleName("M")
                .email("candidate@example.com")
                .phone("+123456789")
                .skills("Java, Spring")
                .status(Status.OPEN)
                .anotherContact("other@example.com")
                .desiredPosition("Developer")
                .build();
    }
}
