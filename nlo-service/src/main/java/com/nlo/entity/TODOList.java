package com.nlo.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class TODOList  extends BaseEntity {
    private String name;
    private String staticURL;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Reaction> reactions = new ArrayList<>();
}
