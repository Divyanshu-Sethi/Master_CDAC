package com.example.master.controller;

import com.example.master.entity.DesignationType;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.service.DesignationTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class DesignationTypeController {

    private final DesignationTypeService service;

    @Autowired
    public DesignationTypeController(DesignationTypeService service) {
        this.service = service;
    }

    //View Controller for Thymeleaf
    @GetMapping("/designation-type")
    public String showDesignationPage(Model model) {
        model.addAttribute("designationType", new DesignationType());
        return "Designation_master";
    }

    //GET All Designation type
    @GetMapping("/designation")
    @ResponseBody
    public ResponseEntity<List<DesignationType>> getAll() {
        return ResponseEntity.ok(service.getAllDesignations());
    }


    //GET a specific Designation type by ID
    @GetMapping("/designation/{id}")
    @ResponseBody
    public ResponseEntity<DesignationType> getById(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // CREATE a designation type
    @PostMapping("/designation")
    @ResponseBody
    public ResponseEntity<?> create(@Valid @RequestBody DesignationType designationType,
                                    BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getFieldErrors());
        }
        try {
            DesignationType saved = service.save(designationType);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // UPDATE a Designation Type
    @PutMapping("/designation/{id}")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody DesignationType designationType,
                                    BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getFieldErrors());
        }
        try {
            designationType.setId(id);
            DesignationType updated = service.save(designationType);
            return ResponseEntity.ok(updated);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    //DELETE a designation Type
    @DeleteMapping("/designation/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


