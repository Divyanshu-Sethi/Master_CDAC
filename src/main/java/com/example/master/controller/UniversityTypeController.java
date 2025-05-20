package com.example.master.controller;

import com.example.master.entity.UniversityType;
import com.example.master.service.UniversityTypeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
        model.addAttribute("universityType", new UniversityType()); // ✅ This must match HTML
        model.addAttribute("universityList", service.getAllUniversity());
        return "University_Master"; // ✅ Match filename and casing
    }

    @PostMapping("/save")
    public String saveUniversity(@Valid @ModelAttribute UniversityType universityType,
                                 BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("universityList", service.getAllUniversity());
            return "University_Master";
        }

        // duplicate check...
        service.save(universityType);
        return "redirect:/university";
    }


    @GetMapping("/edit/{id}")
    public String editUniversity(@PathVariable Long id, Model model) {
        UniversityType universityType = service.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid university ID: " + id)
        );
        model.addAttribute("universityType", universityType);
        model.addAttribute("universityList", service.getAllUniversity());
        return "University_Master";
    }

    @GetMapping("/delete/{id}")
    public String deleteUniversity(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/university";
    }
}
