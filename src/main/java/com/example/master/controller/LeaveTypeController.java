
package com.example.master.controller;

import com.example.master.entity.LeaveType;
import com.example.master.service.LeaveTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/leaves")
public class LeaveTypeController {

    private final LeaveTypeService service;

    public LeaveTypeController(LeaveTypeService service) {
        this.service = service;
    }


    @GetMapping
    public String showLeavePage(Model model) {
        model.addAttribute("leaveType", new LeaveType());  // for the form
        model.addAttribute("leaveList", service.getAllLeaveTypes());  // for the table
        return "leave_master";  // this will be your unified HTML
    }


    @PostMapping("/save")
    public String saveLeave(@ModelAttribute LeaveType leaveType) {
        service.save(leaveType);
        return "redirect:/leaves";  // reload with updated list
    }


    @GetMapping("/edit/{id}")
    public String editLeave(@PathVariable Long id, Model model) {
        LeaveType leaveType = service.findById(id).orElseThrow();
        model.addAttribute("leaveType", leaveType);
        model.addAttribute("leaveList", service.getAllLeaveTypes());
        return "leave_master";  // same page for edit
    }


    @GetMapping("/delete/{id}")
    public String deleteLeave(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/leaves";
    }
}
