package com.nlo.repository.dbdto;

import com.nlo.constant.ReactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReactionDBDTO {
    private String reactionId;
    private String currentUser;
    private String dataId;
    private ReactionType reactionType;
}
