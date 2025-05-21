package com.example.master.service;

import com.example.master.entity.QualificationType;
import com.example.master.repository.QualificationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class QualificationTypeService {

    @Autowired
    private final QualificationTypeRepository repository;

    public QualificationTypeService(QualificationTypeRepository repository) {
        this.repository = repository;
    }

    public List<QualificationType> getAllQualifications() {
        return repository.findAll();
    }

    public Optional<QualificationType> getQualificationById(Long id) {
        return repository.findById(id);
    }

    public void saveQualification(QualificationType qualificationType) {
        repository.save(qualificationType);
    }

    public void deleteQualification(Long id) {
        repository.deleteById(id);
    }

    public boolean isDuplicateName(String name) { return repository.findByNameIgnoreCase(name).isPresent(); }

}
