package com.example.master.controller;

import com.example.master.entity.DesignationType;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.service.DesignationTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/designations")
public class DesignationTypeController {

    private final DesignationTypeService service;

    public DesignationTypeController(DesignationTypeService service) {
        this.service = service;
    }

    @GetMapping
    public String showDesignationPage(Model model) {
        model.addAttribute("designationType", new DesignationType());
        model.addAttribute("designationList", service.getAllDesignations());
        return "designation_master"; // your Thymeleaf page
    }

    // AJAX save (create or update)
    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<?> saveDesignationAjax(@Valid @ModelAttribute DesignationType designationType,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldErrors());
        }
        try {
            DesignationType savedDesignation = service.save(designationType);
            return ResponseEntity.ok(savedDesignation); // return saved entity as JSON
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // AJAX-friendly edit form load
    @GetMapping("/edit/{id}")
    public String editDesignation(@PathVariable Long id, Model model) {
        DesignationType designationType = service.findById(id).orElseThrow(() ->
                        new IllegalArgumentException("Invalid designation ID: " + id)
                );
        model.addAttribute("designationType", designationType);
        model.addAttribute("designationList", service.getAllDesignations());
        return "designation_master";
    }

    // Standard delete + redirect
    @GetMapping("/delete/{id}")
    public String deleteDesignation(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/designations";
    }

    // Get updated list via AJAX
    @GetMapping("/list")
    @ResponseBody
    public List<DesignationType> getDesignationList() {
        return service.getAllDesignations();
    }
}
