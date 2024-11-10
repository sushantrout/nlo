package com.nlo.entity;

import com.nlo.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table(name = "attachments")
@Entity
@EqualsAndHashCode(callSuper = true)
public class Attachment extends BaseEntity {
    private String fileName;
    private String url;
    private String contentType;
    private String extension;
    private String thumbnailUrl;
    private String thumbnaileMediaType;
    private String staticURL;
    private String description;
}