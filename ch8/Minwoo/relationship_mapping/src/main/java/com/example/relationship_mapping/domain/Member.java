package com.example.relationship_mapping.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter
@ToString
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    @OneToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;
//    @ManyToMany
//    @JoinColumn(name = "team_id")
//    private Team team;
}
