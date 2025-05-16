
package com.example.master.service;

import com.example.master.entity.LeaveType;
import com.example.master.repository.LeaveTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveTypeService {

    private final LeaveTypeRepository repository;

    public LeaveTypeService(LeaveTypeRepository repository) {
        this.repository = repository;
    }

    public List<LeaveType> getAllLeaveTypes() {
        return repository.findAll();
    }

    public LeaveType save(LeaveType leaveType) {
        return repository.save(leaveType);
    }

    public Optional<LeaveType> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
