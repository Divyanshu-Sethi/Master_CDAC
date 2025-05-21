package com.example.master.controller;

import com.example.master.entity.LeaveType;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.service.LeaveTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
        return "leave_master";
    }


    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<?> saveLeaveAjax(@Valid @ModelAttribute LeaveType leaveType,
                                            BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(bindingResult.getFieldErrors());
        }
        try{
            LeaveType savedLeave = service.save(leaveType);
            return ResponseEntity.ok(savedLeave);
        }catch(DuplicateEntryException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


    @GetMapping("/edit/{id}")
    public String editLeave(@PathVariable Long id, Model model) {
        LeaveType leaveType = service.findById(id).orElseThrow(()->
                new IllegalArgumentException("Invalid leave ID: " + id)
        );
        model.addAttribute("leaveType", leaveType);
        model.addAttribute("leaveList", service.getAllLeaveTypes());
        return "leave_master";
    }


    @GetMapping("/delete/{id}")
    public String deleteLeave(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/leaves";
    }

    @GetMapping("/list")
    @ResponseBody
    public List<LeaveType> getLeaveList() {
        return service.getAllLeaveTypes();
    }
}
