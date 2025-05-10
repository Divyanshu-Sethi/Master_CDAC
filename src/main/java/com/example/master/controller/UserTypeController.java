package com.example.master.controller;

import com.example.master.entity.UserType;
import com.example.master.service.UserTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usertypes")
public class UserTypeController {

    private final UserTypeService service;

    public UserTypeController(UserTypeService service) {
        this.service = service;
    }

    @GetMapping
    public String showUserTypePage(Model model) {
        model.addAttribute("userType", new UserType());
        model.addAttribute("userTypeList", service.getAllUserTypes());
        return "UserType";
    }

    @PostMapping("/save")
    public String saveUserType(@ModelAttribute UserType userType) {
        service.save(userType);
        return "redirect:/usertypes";
    }

    @GetMapping("/edit/{id}")
    public String editUserType(@PathVariable Long id, Model model) {
        UserType userType = service.findById(id).orElseThrow();
        model.addAttribute("userType", userType);
        model.addAttribute("userTypeList", service.getAllUserTypes());
        return "UserType";
    }

    @GetMapping("/delete/{id}")
    public String deleteUserType(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/usertypes";
    }
}