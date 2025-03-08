package com.dmdev.dao;

import com.dmdev.entity.Candidate;
import com.dmdev.entity.Role;
import com.dmdev.entity.Status;
import com.dmdev.entity.User;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class CandidateCriteriaApiDaoIT extends AbstractDao {

    private final CandidateCriteriaDao dao = CandidateCriteriaDao.getInstance();

    @Test
    void getCandidateWithConnectedHrEmail() {
        User hr = buildHr("HR", "hr@example.com", null);
        Candidate candidate1 = buildCandidate("Petr", "Petrov", Status.OPEN);
        Candidate candidate2 = buildCandidate("Ivan", "Ivanov", Status.HIRED);
        candidate1.setHr(hr);
        candidate2.setHr(hr);
        session.persist(hr);
        session.persist(candidate1);
        session.persist(candidate2);
        session.flush();
        session.clear();

        List<Candidate> candidates = dao.getCandidateWithConnectedHrEmail(session, "hr@example.com");

        assertThat(candidates)
                .isNotEmpty()
                .allMatch(c -> c.getHr() != null && "hr@example.com".equals(c.getHr().getEmail()));
    }

    @Test
    void getCandidatesOrderByLastName() {
        Candidate candidateAnna = buildCandidate("Zenden", "Zendovich", Status.HIRED);
        Candidate candidateZenden = buildCandidate("Anna", "Annova", Status.OPEN);
        session.persist(candidateZenden);
        session.persist(candidateAnna);
        session.flush();
        session.clear();

        List<Candidate> candidates = dao.getCandidatesOrderByLastName(session);

        assertThat(candidates)
                .isNotEmpty()
                .extracting(Candidate::getLastName)
                .containsExactly("Annova", "Zendovich");
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

    private Candidate buildCandidate(final String firstName, final String lastname, final Status status) {
        return Candidate.builder()
                .firstName(firstName)
                .lastName(lastname)
                .email("candidate@example.com")
                .phone("+123456789")
                .skills("Java, Spring")
                .status(status)
                .anotherContact("other@example.com")
                .desiredPosition("Developer")
                .build();
    }
}