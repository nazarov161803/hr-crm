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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends AuditableEntity<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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
    @ToString.Exclude
    private List<Candidate> candidates;

    @OneToMany(mappedBy = "interviewer")
    @ToString.Exclude
    private List<Interview> interviews;

    @OneToMany(mappedBy = "hr")
    @ToString.Exclude
    private List<Vacancy> vacancies;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @ToString.Exclude
    private Department department;

    @OneToMany(mappedBy = "headOfDepartment")
    @ToString.Exclude
    private List<Department> departments;

}
