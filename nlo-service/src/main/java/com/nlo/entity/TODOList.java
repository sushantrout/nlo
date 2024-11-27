package com.nlo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class TODOList  extends BaseEntity {
    private String name;
    @Column(length = 512)
    private String staticURL;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Reaction> reactions = new ArrayList<>();
}
