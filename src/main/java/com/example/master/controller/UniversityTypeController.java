package com.example.master.controller;

import com.example.master.entity.UniversityType;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.service.UniversityTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/university")
public class UniversityTypeController {

    private final UniversityTypeService service;

    public UniversityTypeController(UniversityTypeService service) {
        this.service = service;
    }

    @GetMapping
    public String showUniversityPage(Model model) {
        model.addAttribute("universityType", new UniversityType());
        model.addAttribute("universityList", service.getAllUniversityTypes());
        return "University_Master";
    }

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<?> saveUniversityAjax(@Valid @ModelAttribute UniversityType universityType,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldErrors());
        }

        try {
            UniversityType saved = service.save(universityType);
            return ResponseEntity.ok(saved);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/edit/{id}")
    public String editUniversity(@PathVariable Long id, Model model) {
        UniversityType universityType = service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid university ID: " + id));
        model.addAttribute("universityType", universityType);
        model.addAttribute("universityList", service.getAllUniversityTypes());
        return "University_Master";
    }

    @GetMapping("/delete/{id}")
    public String deleteUniversity(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/university";
    }

    @GetMapping("/list")
    @ResponseBody
    public List<UniversityType> getUniversityList() {
        return service.getAllUniversityTypes();
    }
}
