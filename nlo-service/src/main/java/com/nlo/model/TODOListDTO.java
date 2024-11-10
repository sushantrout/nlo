package com.nlo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TODOListDTO extends BaseDTO {
    private String staticURL;
    private List<ReactionDTO> reactions;
}
