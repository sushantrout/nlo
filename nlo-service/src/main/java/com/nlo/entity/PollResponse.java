package com.nlo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PollResponse extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="poll_id")
    public Poll poll;

    @ManyToOne
    @JoinColumn(name = "option_id")
    public Option option;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;
}
