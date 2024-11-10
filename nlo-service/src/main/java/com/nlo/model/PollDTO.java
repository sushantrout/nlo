package com.nlo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PollDTO extends BaseDTO {
    private String title;
    List<OptionDTO> options;
}
