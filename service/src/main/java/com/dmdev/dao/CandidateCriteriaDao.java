package com.dmdev.dao;

import com.dmdev.entity.Candidate;
import com.dmdev.entity.Candidate_;
import com.dmdev.entity.User_;
import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaJoin;
import org.hibernate.query.criteria.JpaRoot;

import java.util.List;

public class CandidateCriteriaDao {

    private static final CandidateCriteriaDao INSTANCE = new CandidateCriteriaDao();

    public static CandidateCriteriaDao getInstance() {
        return INSTANCE;
    }


    /*
     * Получить кандидатов, у которых hr с указанным email
     */
    public List<Candidate> getCandidateWithConnectedHrEmail(final Session session, final String hrEmail) {
        final HibernateCriteriaBuilder cb = session.getCriteriaBuilder();

        final JpaCriteriaQuery<Candidate> criteria = cb.createQuery(Candidate.class);
        final JpaRoot<Candidate> candidate = criteria.from(Candidate.class);
        final JpaJoin<Object, Object> hr = candidate.join(Candidate_.HR);

        criteria.select(candidate).where(
                cb.equal(hr.get(User_.EMAIL), hrEmail)
        );

        return session.createQuery(criteria)
                .getResultList();
    }

    /*
     * Получить кандидатов, отсортированных по lastName в порядке убывания.
     */
    public List<Candidate> getCandidatesOrderByLastName(final Session session) {
        final HibernateCriteriaBuilder cb = session.getCriteriaBuilder();

        final JpaCriteriaQuery<Candidate> criteria = cb.createQuery(Candidate.class);

        final JpaRoot<Candidate> user = criteria.from(Candidate.class);
        criteria.select(user).orderBy(
                cb.asc(user.get(Candidate_.lastName))
        );

        return session.createQuery(criteria)
                .getResultList();
    }
}
