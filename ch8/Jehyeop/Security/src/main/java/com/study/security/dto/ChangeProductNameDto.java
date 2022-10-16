package com.study.security.dto;

public class ChangeProductNameDto {

    private Long id;

    private String name;

    public ChangeProductNameDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ChangeProductNameDto() {
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setNumber(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
