package com.nlo.entity;

import com.nlo.constant.ReactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Reaction extends BaseEntity {
    private String userId;  // User who reacted

    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;

    private LocalDateTime createdDate;
}