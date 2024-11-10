package com.nlo.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
public class BaseEntity {
    @Id
    @Tsid
    @Column(length = 50)
    private String id;

    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_on", updatable = false)
    private OffsetDateTime createdOn;

    @Column(name = "updated_on")
    private OffsetDateTime updatedOn;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "deleted")
    private Boolean deleted;

    @PrePersist
    public void prePersist() {
        this.setCreatedOn(OffsetDateTime.now());
        this.setActive(true);
        this.setDeleted(false);
    }

    @PreUpdate
    public void preUpdate() {
        this.setUpdatedOn(OffsetDateTime.now());
    }

    public BaseEntity(String id) {
        this.id = id;
    }

}