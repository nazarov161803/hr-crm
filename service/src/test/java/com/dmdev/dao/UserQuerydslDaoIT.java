package com.dmdev.dao;

import com.dmdev.entity.Role;
import com.dmdev.entity.User;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class UserQuerydslDaoIT extends AbstractDao {

    private final UserQueryDslDao dao = UserQueryDslDao.getInstance();

    @Test
    void getAllUsers() {
        User user1 = buildHr("John", "john@example.com", null);
        User user2 = buildHr("Alice", "alice@example.com", null);
        session.persist(user1);
        session.persist(user2);
        session.flush();
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
        session.flush();
        session.clear();

        List<User> limitedUsers = dao.getLimitedUsersOrderByHireDate(session, 2);
        assertThat(limitedUsers).hasSize(2);
        assertThat(limitedUsers.get(0).getHireDate()).isBeforeOrEqualTo(limitedUsers.get(1).getHireDate());
    }

    @Test
    void getUserByFirstName() {
        User user1 = buildHr("John", "john@example.com", null);
        User user2 = buildHr("Alice", "alice@example.com", null);
        session.persist(user1);
        session.persist(user2);
        session.flush();
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
}