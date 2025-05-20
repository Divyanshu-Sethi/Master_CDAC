
package com.example.master.service;

import com.example.master.entity.UserType;
import com.example.master.repository.UserTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserTypeService {

    private final UserTypeRepository repository;

    public UserTypeService(UserTypeRepository repository) {
        this.repository = repository;
    }

    public List<UserType> getAllUserTypes() {
        return repository.findAll();
    }

    public UserType save(UserType userType) {
        return repository.save(userType);
    }

    public Optional<UserType> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
