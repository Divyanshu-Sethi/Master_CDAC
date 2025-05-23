package com.example.master.repository;

import com.example.master.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
    Optional<UserType> findByName(String name);
}