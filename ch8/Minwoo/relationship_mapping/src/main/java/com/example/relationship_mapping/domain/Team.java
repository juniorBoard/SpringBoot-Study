package com.example.relationship_mapping.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany
    @JsonIgnore
    private List<Member> members = new ArrayList<>();

    public void add(Member member) {
//        member.setTeam(this);
        this.members.add(member);
    }
}
