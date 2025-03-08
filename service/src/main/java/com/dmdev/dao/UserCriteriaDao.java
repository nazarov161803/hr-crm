package com.dmdev.dao;

import com.dmdev.entity.Candidate;
import com.dmdev.entity.Candidate_;
import com.dmdev.entity.Status;
import com.dmdev.entity.User;
import com.dmdev.entity.User_;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaFetch;
import org.hibernate.query.criteria.JpaRoot;

import java.util.List;

public class UserCriteriaDao {

    private static final UserCriteriaDao INSTANCE = new UserCriteriaDao();

    public static UserCriteriaDao getInstance() {
        return INSTANCE;
    }

    /*
     * Получить всех пользователей.
     */
    public List<User> getAllUsers(Session session) {
        final HibernateCriteriaBuilder cb = session.getCriteriaBuilder(); // Здесь мы берём специальный объект (CriteriaBuilder), который помогает строить запросы в виде объектов Java. Это как инструмент, который позволяет "собирать" запрос из частей

        final JpaCriteriaQuery<User> criteria = cb.createQuery(User.class);  // Мы создаем новый запрос, который будет возвращать объекты типа User. Это как объявление: «Я хочу получить список пользователей».
        final JpaRoot<User> user = criteria.from(User.class);  // Здесь мы говорим, что основной источник данных для нашего запроса – таблица (или сущность) User. Это аналог команды SQL «FROM User».

        criteria.select(user); // Мы говорим: «Возьми все данные из этой таблицы». В итоге, запрос будет означать «выбрать всех пользователей».

        return session.createQuery(criteria)
                .getResultList();
    }

    /*
     * Получить всех пользователей, у которых firstName равен заданному значению.
     */
    public List<User> getUserByFirstName(Session session, String firstName) {
        final HibernateCriteriaBuilder cb = session.getCriteriaBuilder();

        final JpaCriteriaQuery<User> criteria = cb.createQuery(User.class);
        final JpaRoot<User> user = criteria.from(User.class);

        criteria.select(user).where(
                cb.equal(user.get(User_.FIRST_NAME), firstName)
        );

        return session.createQuery(criteria)
                .getResultList();
    }

    /*
     * Получить первых {limit} пользователей, упорядоченных по дате найма
     */
    public List<User> getLimitedUsersOrderByHireDate(Session session, int limit) {
        final HibernateCriteriaBuilder cb = session.getCriteriaBuilder();

        final JpaCriteriaQuery<User> criteria = cb.createQuery(User.class);
        final JpaRoot<User> user = criteria.from(User.class);

        criteria.select(user).orderBy(
                cb.asc(user.get(User_.hireDate))
        );

        return session.createQuery(criteria)
                .setMaxResults(limit)
                .getResultList();

    }

    /*
     * Получит hrs и их кандидатов, у которых есть кандидаты со статусом OPEN
     */
    public List<User> getHrsWithCandidateStatusOpen(Session session) {
        final HibernateCriteriaBuilder cb = session.getCriteriaBuilder();

        final JpaCriteriaQuery<User> criteria = cb.createQuery(User.class);
        final JpaRoot<User> hrs = criteria.from(User.class);

        // Выполняем fetch join для коллекции кандидатов
        final JpaFetch<User, Candidate> candidateFetch = hrs.fetch(User_.CANDIDATES, JoinType.INNER);
        // Чтобы можно было фильтровать по полям кандидата, приводим fetch к Join:
        Join<User, Candidate> candidateJoin = (Join<User, Candidate>) candidateFetch;

        criteria.select(hrs)
                .distinct(true)
                .where(cb.equal(candidateJoin.get(Candidate_.STATUS), Status.OPEN));

        return session.createQuery(criteria)
                .getResultList();
    }
}
