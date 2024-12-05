package com.nlo.entity;

import com.nlo.constant.GrievanceStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Grievance extends BaseEntity {
    private String title;
    private String subject;
    @Enumerated(EnumType.STRING)
    private GrievanceStatus status;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Attachment> attachments = new ArrayList<>();
}
