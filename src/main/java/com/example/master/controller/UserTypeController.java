package com.example.master.controller;

import com.example.master.entity.UserType;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.service.UserTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usertype")
public class UserTypeController {

    private final UserTypeService service;

    public UserTypeController(UserTypeService service) {
        this.service = service;
    }

    // Show page with form and full list
    @GetMapping
    public String showUserTypePage(Model model) {
        model.addAttribute("userType", new UserType());
        model.addAttribute("userTypeList", service.getAllUserTypes());
        return "UserType";
    }

    // AJAX endpoint to save new or edited UserType
    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<?> saveUserTypeAjax(@Valid @ModelAttribute UserType userType,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Send validation errors back as response body
            return ResponseEntity.badRequest().body(bindingResult.getFieldErrors());
        }

        try {
            UserType savedUserType = service.save(userType);
            return ResponseEntity.ok(savedUserType); // Return saved entity as JSON
        } catch (DuplicateEntryException e) {
            // Return HTTP 409 Conflict with error message
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Edit page, loads the entity to form and list
    @GetMapping("/edit/{id}")
    public String editUserType(@PathVariable Long id, Model model) {
        UserType userType = service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid UserType ID: " + id));
        model.addAttribute("userType", userType);
        model.addAttribute("userTypeList", service.getAllUserTypes());
        return "UserType";
    }

    // Delete and redirect back to list page
    @GetMapping("/delete/{id}")
    public String deleteUserType(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/usertype";
    }

    // Additional endpoint to get updated list for AJAX refresh if needed
    @GetMapping("/list")
    @ResponseBody
    public List<UserType> getUserTypeList() {
        return service.getAllUserTypes();
    }
}
