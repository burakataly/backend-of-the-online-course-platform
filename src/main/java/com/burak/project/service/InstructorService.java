package com.burak.project.service;

import com.burak.project.model.Instructor;
import com.burak.project.repository.ICourseRepository;
import com.burak.project.repository.IInstructorRepository;
import jakarta.persistence.EntityNotFoundException;

public class InstructorService {
    private final IInstructorRepository instructorRepository;

    public InstructorService(IInstructorRepository instructorRepository){
        this.instructorRepository = instructorRepository;
    }

    public Instructor getInstructorById(Long instructorId) {
        return instructorRepository.findById(instructorId).orElseThrow(() -> new EntityNotFoundException("Invalid instructorId"));
    }
}
