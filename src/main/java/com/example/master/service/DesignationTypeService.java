package com.example.master.service;

import com.example.master.entity.DesignationType;
import com.example.master.repository.DesignationTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DesignationTypeService {

    private final DesignationTypeRepository repository;

    public DesignationTypeService(DesignationTypeRepository repository) {
        this.repository = repository;
    }

    public List<DesignationType> getAllDesignations() {
        return repository.findAll();
    }

    public Optional<DesignationType> findById(Long id) {
        return repository.findById(id);
    }

    public void save(DesignationType designationType) {
        repository.save(designationType);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
