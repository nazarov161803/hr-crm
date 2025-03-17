package com.dmdev.repository;

import com.dmdev.dto.CandidateFilter;
import com.dmdev.entity.Candidate;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Session;

import java.util.List;

import static com.dmdev.entity.QCandidate.candidate;
import static com.dmdev.entity.QUser.user;

public class CandidateQueryDslFilter {
    /*
     * Получить кандидатов c фильтром
     */
    public List<Candidate> getCandidatesWithFilter(final Session session, final CandidateFilter filter) {
        Predicate predicate =  QPredicate.builder()
                .add(filter.getFirstName(), candidate.firstName::eq)
                .add(filter.getLastName(), candidate.lastName::eq)
                .buildOr();

        return new JPAQuery<Candidate>(session)
                .select(candidate)
                .from(candidate)
                .join(candidate.hr, user)
                .where(predicate)
                .fetch();
    }
}
