package com.nlo.entity;

import com.nlo.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "stories")
public class Stories extends BaseEntity {
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "header_image_id")
    private Attachment headerImage;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Attachment> attachments;

    @Column(name = "is_published")
    private Boolean isPublished;
}
