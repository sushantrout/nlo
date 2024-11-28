package com.nlo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class EBook extends BaseEntity {
    private String title;

    @Column(length = 2048)
    private String description;

    @Column(length = 512)
    private String staticURL;

    @OneToOne(fetch = FetchType.EAGER)
    private Attachment attachment;
}
