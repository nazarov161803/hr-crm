package com.dmdev.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID departmentId;

    private String firstName;

    private String lastName;

    private String middleName;

    private String login;

    private String password;

    private String email;

    private Enum role;

    private String position;

    private BigDecimal salary;

    private String phone;

    private Instant hireDate;

    private Instant createdAt;

    private Instant updatedAt;
}
