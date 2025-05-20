package com.example.master.repository;

import com.example.master.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserTypeRepository extends JpaRepository<UserType, Long> {
}