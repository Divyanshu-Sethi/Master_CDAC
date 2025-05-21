package com.example.master.service;

import com.example.master.entity.UserType;
import com.example.master.exception.DuplicateEntryException;
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

    public Optional<UserType> findById(Long id) {
        return repository.findById(id);
    }

    public UserType save(UserType userType) throws DuplicateEntryException {
        boolean exists;
        if (userType.getId() == null) {
            // New record: check if exact name exists
            exists = repository.findByName(userType.getName()).isPresent();
        } else {
            // Editing existing record, check if name exists on other records
            exists = repository.findByName(userType.getName())
                    .filter(u -> !u.getId().equals(userType.getId()))
                    .isPresent();
        }
        if (exists) {
            throw new DuplicateEntryException("User Type name already exists: " + userType.getName());
        }
        return repository.save(userType);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
