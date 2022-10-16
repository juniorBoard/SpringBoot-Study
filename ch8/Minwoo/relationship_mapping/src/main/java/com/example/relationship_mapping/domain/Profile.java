package com.example.relationship_mapping.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter
@ToString
@NoArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    @OneToOne(mappedBy = "profile")
    @ToString.Exclude
    @JsonIgnore
    private Member member;

    public void setMember(Member member) {
        member.setProfile(this);
        this.member = member;
    }

    public Profile(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
