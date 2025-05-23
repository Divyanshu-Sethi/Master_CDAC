package com.example.master.controller;

import com.example.master.entity.QualificationType;

import com.example.master.exception.DuplicateEntryException;
import com.example.master.service.QualificationTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class QualificationTypeController {

    private final QualificationTypeService service;

    @Autowired
    public QualificationTypeController(QualificationTypeService service) {
        this.service = service;
    }

    //View Controller for Thymeleaf
    @GetMapping("/qualification-type")
    public String viewQualifications(Model model) {
        model.addAttribute("qualification", new QualificationType());
        return "Qualification_Master";
    }

    //GET all Qualification Types
    @GetMapping("/qualification")
    @ResponseBody
    public ResponseEntity<List<QualificationType>> getAll() {
        return ResponseEntity.ok(service.getAllQualifications());
    }


    //GET a specific qualification type by ID
    @GetMapping("/qualification/{id}")
    @ResponseBody
    public ResponseEntity<QualificationType> getById(@PathVariable Long id) {
        return service.getQualificationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //CREATE a qualification type
    @PostMapping("/qualification")
    @ResponseBody
    public ResponseEntity<?> create(@Valid @RequestBody QualificationType qualificationType,
                                                   BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getFieldErrors());
        }
        try {
            QualificationType saved = service.save(qualificationType);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    //UPDATE a qualification type
    @PutMapping("/qualification/{id}")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody QualificationType qualificationType,
                                    BindingResult result) {
        if(result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getFieldErrors());
        }
        try{
            qualificationType.setId(id);
            QualificationType updated = service.save(qualificationType);
            return ResponseEntity.ok(updated);
        }catch(DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    //DELETE a qualification type
    @DeleteMapping("/qualification/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteQualification(id);
        return ResponseEntity.noContent().build();
    }
}
