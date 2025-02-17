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

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "candidate")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID hrId;

    private UUID userId;

    private String firstName;

    private String lastName;

    private String middleName;

    private String email;

    private String phone;

    private String skills;

    private String status;

    private String anotherContact;

    private String desiredPosition;

    private Instant birthDate;

    private Instant createdAt;

    private Instant updatedAt;
}
