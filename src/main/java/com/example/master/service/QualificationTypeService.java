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
    private QualificationTypeRepository repository;

    public List<QualificationType> getAllQualifications() {
        return repository.findAll();
    }

    public QualificationType saveQualification(QualificationType qualification) {
        return repository.save(qualification);
    }

    public Optional<QualificationType> getQualificationById(Long id) {
        return repository.findById(id);
    }

    public void deleteQualification(Long id) {
        repository.deleteById(id);
    }
}
