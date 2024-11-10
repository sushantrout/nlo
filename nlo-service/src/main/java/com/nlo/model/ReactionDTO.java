package com.nlo.model;

import com.nlo.constant.ReactionType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReactionDTO extends BaseDTO{
    private String userId;  // User ID of the person reacting
    private ReactionType reactionType;  // Can be an enum or String based on your design
    private LocalDateTime createdDate;  // Timestamp of when the reaction was created
}
