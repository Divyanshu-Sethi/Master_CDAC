package com.example.master.entity;


import jakarta.persistence.Entity;

@Entity
public class UniversityType {

    private Long id;

    private String name;

    public UniversityType() {}

    public UniversityType(String name) {
        this.name = name;
    }

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
