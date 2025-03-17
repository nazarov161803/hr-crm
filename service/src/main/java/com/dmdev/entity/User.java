package com.dmdev.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OptimisticLocking;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"candidates", "interviews", "vacancies", "department", "departments"})
@ToString(exclude = {"candidates", "interviews", "vacancies", "department", "departments"})
@OptimisticLocking()
public class User extends AuditableEntity<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Version
    private Long version;

    private String firstName;

    private String lastName;

    private String middleName;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String position;

    private BigDecimal salary;

    private String phone;

    private Instant hireDate;

    @OneToMany(mappedBy = "hr")
    private List<Candidate> candidates = new ArrayList<>();

    @OneToMany(mappedBy = "interviewer")
    private List<Interview> interviews = new ArrayList<>();

    @OneToMany(mappedBy = "hr")
    private List<Vacancy> vacancies = new ArrayList<>();

    @OneToMany(mappedBy = "headOfDepartment")
    private List<Department> departments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
}
