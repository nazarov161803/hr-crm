package com.dmdev.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "interview")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Interview {

    @Id
    private UUID id;

    @Column(name = "candidate_id")
    private UUID candidateId;

    @Column(name = "interviewer_id")
    private UUID interviewerId;

    @Column(name = "vacancy_id")
    private UUID vacancyId;

    private String result;

    @Column(name = "date_time")
    private LocalDate dateTime;

    private String notes;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;
}
