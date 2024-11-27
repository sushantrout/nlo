package com.nlo.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MeetingDTO extends BaseDTO {
    private LocalDateTime meetingTime;
    private String description;
    private String communicationType;
    private ConstituencyDTO constituencyDTO;
}
