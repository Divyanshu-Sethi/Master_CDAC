package com.example.master.controller;

import com.example.master.entity.QualificationType;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.service.QualificationTypeService;
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
@RequestMapping("/qualifications")
public class QualificationTypeController {

    @Autowired
    private final QualificationTypeService service;

    public QualificationTypeController(QualificationTypeService service) {
        this.service = service;
    }

    @GetMapping
    public String viewQualifications(Model model) {
        model.addAttribute("qualification", new QualificationType());
        model.addAttribute("qualificationList", service.getAllQualifications());
        return "Qualification_Master";
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveQualificationAjax(@Valid @ModelAttribute QualificationType qualification,
                                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldErrors());
        }

        try {
            QualificationType saved = service.save(qualification);
            return ResponseEntity.ok(saved);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
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

    @GetMapping("/list")
    @ResponseBody
    public List<QualificationType> getQualification() {
        return  service.getAllQualifications();
    }
}
