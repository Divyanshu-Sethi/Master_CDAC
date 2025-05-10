package com.example.master.entity;

import jakarta.persistence.*;

@Entity
public class UserType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // e.g., Permanent, Contractual

    public UserType() {
    }

    public UserType(String type) {
        this.type = type;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

