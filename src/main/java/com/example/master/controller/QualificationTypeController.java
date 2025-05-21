package com.example.master.controller;

import com.example.master.entity.QualificationType;
import com.example.master.service.QualificationTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/qualifications")
public class QualificationTypeController {

    @Autowired
    private QualificationTypeService service;

    public QualificationTypeController(QualificationTypeService service) { this.service = service; }

    @GetMapping
    public String viewQualifications(Model model) {
        model.addAttribute("qualification", new QualificationType());
        model.addAttribute("qualificationList", service.getAllQualifications());
        return "Qualification_Master";
    }

    @PostMapping("/save")
    public String saveQualification(@Valid @ModelAttribute QualificationType qualification, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()){
            model.addAttribute("qualificationList", service.getAllQualifications());
            return "Qualification_Master";
        }

        //duplicate check...
        service.saveQualification(qualification);
        return "redirect:/qualifications";
    }

    @GetMapping("/edit/{id}")
    public String editQualification(@PathVariable Long id, Model model) {
        QualificationType qualification = service.getQualificationById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid qualification ID: " + id));
        model.addAttribute("qualification", qualification);
        model.addAttribute("qualificationList", service.getAllQualifications());
        return "Qualification_Master";
    }

    @GetMapping("/delete/{id}")
    public String deleteQualification(@PathVariable Long id) {
        service.deleteQualification(id);
        return "redirect:/qualifications";
    }
}
