package com.example.master.controller;

import com.example.master.entity.DesignationType;
import com.example.master.service.DesignationTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/designations")
public class DesignationTypeController {

    private final DesignationTypeService service;

    public DesignationTypeController(DesignationTypeService service) {
        this.service = service;
    }

    @GetMapping
    public String showDesignationPage(Model model) {
        model.addAttribute("designationType", new DesignationType());  // for the form
        model.addAttribute("designationList", service.getAllDesignations());  // for the table
        return "designation_master";  // your unified HTML view
    }

    @PostMapping("/save")
    public String saveDesignation(@ModelAttribute DesignationType designationType) {
        service.save(designationType);
        return "redirect:/designations";
    }

    @GetMapping("/edit/{id}")
    public String editDesignation(@PathVariable Long id, Model model) {
        DesignationType designationType = service.findById(id).orElseThrow();
        model.addAttribute("designationType", designationType);
        model.addAttribute("designationList", service.getAllDesignations());
        return "designation_master";
    }

    @GetMapping("/delete/{id}")
    public String deleteDesignation(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/designations";
    }
}
//        http://localhost:8080/designations