package com.example.master.service;

import com.example.master.entity.QualificationType;
import com.example.master.entity.UniversityType;
import com.example.master.repository.UniversityTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UniversityTypeService {

    private final UniversityTypeRepository repository;

    public UniversityTypeService(UniversityTypeRepository repository) { this.repository = repository; }

    public List<UniversityType> getAllUniversity() { return repository.findAll(); }

    public Optional<UniversityType> findById(Long id) { return repository.findById(id); }

    public void save(UniversityType universityType) { repository.save(universityType); }

    public void deleteById(Long id){ repository.deleteById(id); }

    public boolean isDuplicateName(String name) { return repository.findByNameIgnoreCase(name).isPresent(); }

}
