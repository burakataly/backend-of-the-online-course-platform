package com.burak.project.service;

import com.burak.project.model.Instructor;
import com.burak.project.repository.IInstructorRepository;
import com.burak.project.request.InstructorRequest;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {
    private final IInstructorRepository instructorRepository;

    public InstructorService(IInstructorRepository instructorRepository){
        this.instructorRepository = instructorRepository;
    }

    public Instructor getInstructorById(Long instructorId) {
        return instructorRepository.findById(instructorId).orElseThrow(() -> new EntityNotFoundException("Invalid instructorId"));
    }

    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    public Instructor createInstructor(InstructorRequest instructorRequest) {
        Instructor instructor = instructorRepository.findByUsername(instructorRequest.getUsername());
        if(instructor != null) throw new EntityExistsException(
                "There is already an instructor with this username.");
        instructor = Instructor.builder().
                biography(instructorRequest.getBiography()).
                username(instructorRequest.getUsername()).
                password(instructorRequest.getPassword()).
                build();
        return instructorRepository.save(instructor);
    }

    public Instructor updateInstructor(Long instructorId, InstructorRequest instructorRequest) {
        if(instructorRequest.getUsername() == null || instructorRequest.getPassword() == null) throw
            new EntityNotFoundException("username or password cannot be null");
        Optional<Instructor> temp = instructorRepository.findById(instructorId);
        if(temp.isPresent()){
            Instructor foundInstructor = temp.get();
            Instructor instructor = instructorRepository.findByUsername(instructorRequest.getUsername());
            if(instructor != null && !instructorId.equals(instructor.getId())) throw
                    new EntityExistsException("There is already an instructor with this username.");

            foundInstructor.setUsername(instructorRequest.getUsername());
            foundInstructor.setPassword(instructorRequest.getPassword());
            foundInstructor.setBiography(instructorRequest.getBiography());
            return instructorRepository.save(foundInstructor);
        }
        else throw new EntityNotFoundException("Invalid instructorId");
    }

    public void deleteInstructor(Long instructorId) {
        instructorRepository.deleteById(instructorId);
    }

    public Instructor login(InstructorRequest instructorRequest) {
        Instructor instructor = instructorRepository.findByUsername(instructorRequest.getUsername());
        return (instructor == null || instructor.getPassword().equals(
                instructorRequest.getPassword())) ? instructor : null;
    }
}
