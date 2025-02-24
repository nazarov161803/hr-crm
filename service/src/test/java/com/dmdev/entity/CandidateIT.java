package com.dmdev.entity;

import com.dmdev.utils.HibernateTestUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;

public class CandidateIT {

    @Test
    public void candidateCreateCheck() {
        final Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             final Session session = sessionFactory.openSession()) {

            Candidate candidate = buildCandidate();
            session.beginTransaction();
            session.persist(candidate);

            Candidate retrieved = session.get(Candidate.class, candidate.getId());
            assertThat(retrieved).isEqualTo(candidate);
        }
    }

    @Test
    public void candidateUpdateCheck() {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            Candidate candidate = buildCandidate();
            session.beginTransaction();
            session.persist(candidate);
            session.getTransaction().commit();

            Candidate retrieved = session.get(Candidate.class, candidate.getId());
            retrieved.setEmail("updatedCandidate@example.com");
            session.merge(retrieved);
            Candidate updated = session.get(Candidate.class, candidate.getId());
            assertThat(updated.getEmail()).isEqualTo("updatedCandidate@example.com");

        }
    }

    @Test
    public void candidateRemoveCheck() {
        Configuration configuration = new Configuration();
        configuration.configure();
        Candidate candidate = buildCandidate();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            session.persist(candidate);
            session.remove(candidate);

            Candidate removed = session.get(Candidate.class, candidate.getId());
            assertNull(removed);

        }
    }

    @Test
    public void candidateWithHrCheck() {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            User hr = User.builder()
                    .email("hr@example.com")
                    .password("pass")
                    .firstName("HR")
                    .lastName("Manager")
                    .role(Role.HR)
                    .build();

            Candidate candidate = buildCandidate();
            candidate.setHr(hr);

            session.beginTransaction();
            session.persist(hr);
            session.persist(candidate);
            session.getTransaction().commit();

            Candidate retrieved = session.get(Candidate.class, candidate.getId());
            assertThat(retrieved.getHr()).isNotNull();
            assertThat(retrieved.getHr().getEmail()).isEqualTo("hr@example.com");
        }
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
