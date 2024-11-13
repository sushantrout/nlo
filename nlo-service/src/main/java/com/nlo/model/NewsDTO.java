package com.nlo.model;

import com.nlo.constant.ReactionType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class NewsDTO extends BaseDTO{
    private String id;
    private String title;
    private String content;
    private LocalDateTime publishedDate;
    private List<AttachmentDTO> attachments;
    private List<ReactionDTO> reactions;
    private String staticUrl;
    private ReactionType currentUserReaction;
    private Integer totalLike;
    private Boolean hot;
    private Number totalShare;
    private CategoryDTO category;
}
