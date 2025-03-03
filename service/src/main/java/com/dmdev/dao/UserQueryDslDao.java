package com.dmdev.dao;

import com.dmdev.entity.Status;
import com.dmdev.entity.User;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Session;

import java.util.List;

import static com.dmdev.entity.QCandidate.candidate;
import static com.dmdev.entity.QUser.user;

public class UserQueryDslDao {

    private static final UserQueryDslDao INSTANCE = new UserQueryDslDao();

    public static UserQueryDslDao getInstance() {
        return INSTANCE;
    }

    /*
     * Получить всех пользователей.
     */
    public List<User> getAllUsers(final Session session) {
        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .fetch();
    }

    /*
     * Получить всех пользователей, у которых firstName равен заданному значению.
     */
    public List<User> getUserByFirstName(final Session session, final String firstName) {
        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .where(user.firstName.eq(firstName))
                .fetch();
    }

    /*
     * Получить первых {limit} пользователей, упорядоченных по дате найма
     */
    public List<User> getLimitedUsersOrderByHireDate(final Session session, final int limit) {
        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .orderBy(user.hireDate.asc())
                .limit(limit)
                .fetch();
    }

    /*
     * Получит hrs и их кандидатов, у которых есть кандидаты со статусом OPEN
     */
    public List<User> getHrsWithCandidateStatusOpen(final Session session) {
        return new JPAQuery<User>(session)
                .select(user)
                .distinct()
                .from(user)
                .join(user.candidates, candidate)
                .where(candidate.status.eq(Status.OPEN))
                .fetch();
    }
}
