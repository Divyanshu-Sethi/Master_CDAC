package com.example.master.controller;

import com.example.master.entity.QualificationType;
import com.example.master.service.QualificationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/qualifications")
public class QualificationTypeController {

    @Autowired
    private QualificationTypeService service;

    // Display the qualification form and list
    @GetMapping
    public String viewQualifications(Model model) {
        model.addAttribute("qualification", new QualificationType());
        model.addAttribute("qualificationList", service.getAllQualifications());
        return "Qualification_Master"; // Must match the actual HTML file name (without .html)
    }

    // Save or update a qualification
    @PostMapping("/save")
    public String saveQualification(@ModelAttribute("qualification") QualificationType qualification) {
        service.saveQualification(qualification);
        return "redirect:/qualifications";
    }

    // Load qualification into form for editing
    @GetMapping("/edit/{id}")
    public String editQualification(@PathVariable Long id, Model model) {
        QualificationType qualification = service.getQualificationById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid qualification ID: " + id));
        model.addAttribute("qualification", qualification);
        model.addAttribute("qualificationList", service.getAllQualifications());
        return "Qualification_Master";
    }

    // Delete a qualification
    @GetMapping("/delete/{id}")
    public String deleteQualification(@PathVariable Long id) {
        service.deleteQualification(id);
        return "redirect:/qualifications";
    }
}
