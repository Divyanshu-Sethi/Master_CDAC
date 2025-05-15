package com.example.master.entity;

import jakarta.persistence.*;

@Entity
public class LeaveType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // e.g., Sick Leave, Gazetted Holiday

    // Constructors
    public LeaveType() {}

    public LeaveType(String name) {
        this.name = name;
    }

    // Getters & Setters

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
