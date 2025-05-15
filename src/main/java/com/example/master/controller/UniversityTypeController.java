package com.example.master.controller;

import com.example.master.entity.UniversityType;
import com.example.master.service.UniversityTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/university")
public class UniversityTypeController {

    private final UniversityTypeService service;

    public UniversityTypeController(UniversityTypeService service) {
        this.service = service;
    }

    @GetMapping
    public String showUniversityPage(Model model) {
        model.addAttribute("UniversityType", new UniversityType());
        model.addAttribute("UniversityList", service.getAllUniversity());
        return "University_Master";
    }

    @PostMapping("/save")
    public String saveUniversity(@ModelAttribute UniversityType universityType) {
        service.save(universityType);
        return "redirect:/university";
    }

    @GetMapping("/edit/{id}")
    public String editUniversity(@PathVariable Long id, Model model) {
        UniversityType universityType = service.findById(id).orElseThrow();
        model.addAttribute("universityType", universityType);
        model.addAttribute("universityList", service.getAllUniversity());
        return "university_master";
    }

    @GetMapping("/delete/{id}")
    public String deleteUniversity(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:university";
    }
}
