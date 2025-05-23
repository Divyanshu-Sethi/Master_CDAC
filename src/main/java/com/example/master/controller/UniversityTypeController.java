package com.example.master.controller;

import com.example.master.entity.UniversityType;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.service.UniversityTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UniversityTypeController {

    private final UniversityTypeService service;

    @Autowired
    public UniversityTypeController(UniversityTypeService service) {
        this.service = service;
    }

    // View Controller for Thymeleaf
    @GetMapping("/university-type")
    public String universityTypePage(Model model) {
        model.addAttribute("universityType", new UniversityType());
        return "University_Master";
    }

    // GET all university types
    @GetMapping("/university")
    @ResponseBody
    public ResponseEntity<List<UniversityType>> getAll() {
        return ResponseEntity.ok(service.getAllUniversityTypes());
    }

    // GET a specific university type by ID
    @GetMapping("/university/{id}")
    @ResponseBody
    public ResponseEntity<UniversityType> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE a university type
    @PostMapping("/university")
    @ResponseBody
    public ResponseEntity<?> create(@Valid @RequestBody UniversityType universityType,
                                    BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getFieldErrors());
        }

        try {
            UniversityType saved = service.save(universityType);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // UPDATE a university type
    @PutMapping("/university/{id}")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody UniversityType universityType,
                                    BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getFieldErrors());
        }

        try {
            universityType.setId(id);
            UniversityType updated = service.save(universityType);
            return ResponseEntity.ok(updated);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // DELETE a university type
    @DeleteMapping("/university/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
