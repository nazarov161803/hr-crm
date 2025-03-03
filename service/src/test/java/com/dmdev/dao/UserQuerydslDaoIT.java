package com.dmdev.dao;

import com.dmdev.entity.Candidate;
import com.dmdev.entity.Role;
import com.dmdev.entity.Status;
import com.dmdev.entity.User;
import com.dmdev.utils.HibernateTestUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class UserQuerydslDaoIT {
    private Session session;
    private final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
    private final UserQueryDslDao dao = UserQueryDslDao.getInstance();

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
    void getAllUsers() {
        User user1 = buildHr("John", "john@example.com", null);
        User user2 = buildHr("Alice", "alice@example.com", null);
        session.persist(user1);
        session.persist(user2);
        session.getTransaction().commit();
        session.clear();

        List<User> users = dao.getAllUsers(session);

        assertThat(users).isNotEmpty()
                .extracting(User::getEmail)
                .contains(user1.getEmail(), user2.getEmail());
    }

    @Test
    void getLimitedUsersOrderByHireDate() {
        User hr1 = buildHr("HR1", "hr1@example.com", Instant.parse("2023-01-01T00:00:00Z"));
        User hr2 = buildHr("HR2", "hr2@example.com", Instant.parse("2023-02-01T00:00:00Z"));
        User hr3 = buildHr("HR3", "hr3@example.com", Instant.parse("2023-03-01T00:00:00Z"));

        session.persist(hr1);
        session.persist(hr2);
        session.persist(hr3);
        session.getTransaction().commit();
        session.clear();

        List<User> limitedUsers = dao.getLimitedUsersOrderByHireDate(session, 2);
        assertThat(limitedUsers).hasSize(2);
        assertThat(limitedUsers.get(0).getHireDate()).isBeforeOrEqualTo(limitedUsers.get(1).getHireDate());
    }

    @Test
    void getHrsWithCandidateStatusOpen() {
        User hr = buildHr("HR1", "hr1@example.com", Instant.parse("2023-01-01T00:00:00Z"));

        Candidate candidateOpen = buildCandidate("Petr", Status.OPEN);
        Candidate candidateOther = buildCandidate("Ivan", Status.HIRED);
        candidateOpen.setHr(hr);
        candidateOther.setHr(hr);
        hr.setCandidates(Arrays.asList(candidateOpen, candidateOther));

        session.persist(hr);
        session.persist(candidateOpen);
        session.persist(candidateOther);
        session.getTransaction().commit();
        session.clear();

        List<User> hrs = dao.getHrsWithCandidateStatusOpen(session);

        //TODO тут почему-то возвращается кандидаты со статусом не только open
        assertThat(hrs).isNotEmpty();
        User retrievedHr = hrs.get(0);
        assertThat(retrievedHr.getRole()).isEqualTo(Role.HR);
        assertThat(retrievedHr.getCandidates())
                .extracting(Candidate::getStatus)
                .allMatch(status -> status.equals(Status.OPEN));
    }

    @Test
    void getUserByFirstName() {
        User user1 = buildHr("John", "john@example.com", null);
        User user2 = buildHr("Alice", "alice@example.com", null);
        session.persist(user1);
        session.persist(user2);
        session.getTransaction().commit();
        session.clear();

        List<User> users = dao.getUserByFirstName(session, user1.getFirstName());

        assertThat(users).isNotEmpty()
                .contains(user1)
                .size().isEqualTo(1);
    }

    private User buildHr(final String firstName, final String email, final Instant hireDate) {
        return User.builder()
                .firstName(firstName)
                .email(email)
                .hireDate(hireDate)
                .password("12345")
                .lastName("lastName")
                .role(Role.HR)
                .build();
    }

    private Candidate buildCandidate(final String firstName, final Status status) {
        return Candidate.builder()
                .firstName(firstName)
                .lastName("familyName")
                .email("candidate@example.com")
                .phone("+123456789")
                .skills("Java, Spring")
                .status(status)
                .anotherContact("other@example.com")
                .desiredPosition("Developer")
                .build();
    }
}