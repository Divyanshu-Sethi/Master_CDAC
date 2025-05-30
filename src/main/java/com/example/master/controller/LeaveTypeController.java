package com.example.master.controller;

import com.example.master.entity.LeaveType;
import com.example.master.exception.DuplicateEntryException;
import com.example.master.service.LeaveTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:5173")
public class LeaveTypeController {

    private final LeaveTypeService service;

    @Autowired
    public LeaveTypeController(LeaveTypeService service) {
        this.service = service;
    }

    // View Controller For Thymeleaf
    @GetMapping("/leave-type")
    public String showLeavePage(Model model) {
        model.addAttribute("leaveType", new LeaveType());
        return "leave_master";
    }


    // GET all leave types
    @GetMapping("/leave")
    @ResponseBody
    public ResponseEntity<List<LeaveType>> getall() {
        return ResponseEntity.ok(service.getAllLeaveTypes());
    }

    //GET a specific leave type by ID
    @GetMapping("/leave/{id}")
    @ResponseBody
    public ResponseEntity<LeaveType> getbyId(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE a Leave Type
    @PostMapping("/leave")
    @ResponseBody
    public ResponseEntity<?> create(@Valid @RequestBody LeaveType leaveType,
                                    BindingResult result) {
        if(result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getFieldErrors());
        }
        try {
            LeaveType saved = service.save(leaveType);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (DuplicateEntryException e) {
            return  ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // UPDATE a Leave Type
    @PutMapping("/leave/{id}")
    @ResponseBody
    public  ResponseEntity<?> update(@PathVariable long id,
                                     @Valid @RequestBody LeaveType leaveType,
                                     BindingResult result) {
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(result.getFieldErrors());
        }
        try{
         leaveType.setId(id);
         LeaveType updated = service.save(leaveType);
         return ResponseEntity.ok(updated);
        } catch (DuplicateEntryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // DELETE a Leave Type
    @DeleteMapping("/leave/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}























