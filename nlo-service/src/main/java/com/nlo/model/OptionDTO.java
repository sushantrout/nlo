package com.nlo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionDTO extends BaseDTO{
    private String title;
    private Integer sortOrder;
    private Integer rate;
}
