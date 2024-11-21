package com.nlo.model;

import com.nlo.constant.ReactionType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InfographicsDTO extends BaseDTO{
    private String url;
    private List<ReactionDTO> reactions;
    private ReactionType currentUserReaction;
}
