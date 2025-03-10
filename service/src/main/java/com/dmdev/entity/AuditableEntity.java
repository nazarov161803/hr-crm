package com.dmdev.entity;

import com.dmdev.listener.AuditListener;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditListener.class)
public abstract class AuditableEntity<T extends Serializable> implements BaseEntity<UUID> {

    private Instant createdAt;

    private String createdBy;

    private Instant updatedAt;

    private String updatedBy;

}
