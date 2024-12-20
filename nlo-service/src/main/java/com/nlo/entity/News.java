package com.nlo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class News extends BaseEntity {
    private String title;

    @Lob
    private String content;

    private LocalDateTime publishedDate;

    @Column(length = 512)
    private String staticUrl;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Attachment> attachments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Reaction> reactions = new ArrayList<>();
    private Boolean hot;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ViewDetail> viewDetails = new ArrayList<>();

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<NewsShare> newsShares = new ArrayList<>();
}
