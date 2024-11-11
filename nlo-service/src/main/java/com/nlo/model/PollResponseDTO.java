package com.nlo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PollResponseDTO extends BaseDTO {
    private String pollId;
    private String answerId;
    private String userId;
    private String constituencyName;
}
