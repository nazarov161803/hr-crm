package com.dmdev.dao;

import com.dmdev.dto.CandidateFilter;
import com.dmdev.entity.Candidate;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Session;

import java.util.List;

import static com.dmdev.entity.QCandidate.candidate;
import static com.dmdev.entity.QUser.user;

public class CandidateQueryDslDao {

    private static final CandidateQueryDslDao INSTANCE = new CandidateQueryDslDao();

    public static CandidateQueryDslDao getInstance() {
        return INSTANCE;}


    /*
     * Получить кандидатов, у которых hr с указанным email
     */
    public List<Candidate> getCandidateWithConnectedHrEmail(Session session, String hrEmail) {
        return new JPAQuery<Candidate>(session)
                .select(candidate)
                .from(candidate)
                .join(candidate.hr, user)
                .where(user.email.eq(hrEmail))
                .fetch();
    }

    /*
     * Получить кандидатов, отсортированных по lastName в порядке убывания.
     */
    public List<Candidate> getCandidatesOrderByLastName(Session session) {
        return new JPAQuery<Candidate>(session)
                .select(candidate)
                .from(candidate)
                .orderBy(candidate.lastName.asc())
                .fetch();

    }
}
