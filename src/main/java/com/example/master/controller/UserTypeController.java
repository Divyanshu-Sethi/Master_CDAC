package com.example.master.controller;

import com.example.master.entity.UserType;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.service.UserTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class UserTypeController {

    private final UserTypeService service;

    @Autowired
    public UserTypeController(UserTypeService service) { this.service = service; }

    // View Controller for ThymeLeaf
    @GetMapping("/user-type")
    public String showUserTypePage(Model model) {
        model.addAttribute("userType", new UserType());
        return "UserType";
    }

    //GET all user types
    @GetMapping("/user")
    @ResponseBody
    public ResponseEntity<List<UserType>> getAll() { return  ResponseEntity.ok(service.getAllUserTypes()); }

    //GET a specific user type by ID
    @GetMapping("/user/{id}")
    @ResponseBody
    public ResponseEntity<UserType> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE a User Type
    @PostMapping("/user")
    @ResponseBody
    public ResponseEntity<?> create(@Valid @RequestBody UserType userType,
                                              BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getFieldErrors());
        }
        try {
            UserType saved = service.save(userType);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved); // Return saved entity as JSON
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // UPDATE a User Type
    @PutMapping("/user/{id}")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody UserType userType, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getFieldErrors());
        }
        try {
            userType.setId(id);
            UserType updated = service.save(userType);
            return ResponseEntity.ok(updated);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // DELETE a user type
    @DeleteMapping("/user/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
