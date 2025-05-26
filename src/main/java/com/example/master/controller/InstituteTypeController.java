package com.example.master.controller;

import com.example.master.entity.InstituteType;
import com.example.master.entity.UserType;
import com.example.master.exception.DuplicateEntryException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import com.example.master.service.InstituteTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class InstituteTypeController {

    private final InstituteTypeService service;


    @Autowired
    public InstituteTypeController(InstituteTypeService service) {
        this.service = service;
    }

    //View Controller  for Thymeleaf
    @GetMapping("/institute-type")
    public String instituteType(Model model) {
        model.addAttribute("userType", new UserType());
        return "Institute_Master";
    }

    //GET all institute types
    @GetMapping("/institute")
    @ResponseBody
    public ResponseEntity<List<InstituteType>> getAll() {
        return ResponseEntity.ok(service.getAllInstituteTypes());
    }

    //GET a specific user type by ID
    @GetMapping("institute/{id}")
    @ResponseBody
    public ResponseEntity<InstituteType> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //CREATE a institute Type
    @PostMapping("/institute")
    @ResponseBody
    public ResponseEntity<?> create(@Valid @RequestBody InstituteType instituteType,
                                    BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getFieldErrors());
        }
        try {
            InstituteType saved = service.save(instituteType);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // UPDATE a institute Type
    @PutMapping("/institute/{id}")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody InstituteType instituteType, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getFieldErrors());
        }
        try {
            instituteType.setId(id);
            InstituteType updated = service.save(instituteType);
            return ResponseEntity.ok(updated);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // DELETE a Institute type
    @DeleteMapping("/institute/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
