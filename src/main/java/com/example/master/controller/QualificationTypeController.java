package com.example.master.controller;

import com.example.master.entity.QualificationType;
import com.example.master.service.QualificationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/qualifications")
@CrossOrigin(origins = "http://localhost:3000")  // Allow React frontend to access this
public class QualificationTypeController {

    @Autowired
    private QualificationTypeService service;

    @GetMapping
    public List<QualificationType> getAllQualifications() {
        return service.getAllQualifications();
    }

    @PostMapping
    public QualificationType addQualification(@RequestBody QualificationType qualification) {
        return service.saveQualification(qualification);
    }

    @PutMapping("/{id}")
    public QualificationType updateQualification(@PathVariable Long id, @RequestBody QualificationType updatedQualification) {
        updatedQualification.setId(id);
        return service.saveQualification(updatedQualification);
    }

    @DeleteMapping("/{id}")
    public void deleteQualification(@PathVariable Long id) {
        service.deleteQualification(id);
    }
}
