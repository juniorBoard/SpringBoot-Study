package com.example.mybatisguide.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Company {
    private Long id;
    private String name;
    private String ceo;

    public Company(String name, String ceo) {
        this.name = name;
        this.ceo = ceo;
    }
}
