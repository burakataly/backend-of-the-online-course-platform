package com.burak.project.service;

import com.burak.project.model.Student;
import com.burak.project.repository.IStudentRepository;
import com.burak.project.request.StudentRequest;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final IStudentRepository studentRepository;

    public StudentService(IStudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId).orElseThrow(
                () -> new EntityNotFoundException("Invalid studentId"));
    }

    public Student createStudent(StudentRequest studentRequest) {
        Student student = studentRepository.findByUsername(studentRequest.getUsername());
        if(student != null) throw new EntityExistsException(
                "There is already a student with this username.");
        if(studentRequest.getBalance() < 0.0) throw new EntityNotFoundException("Balance cannot be less than 0");
        student = Student.builder().
                username(studentRequest.getUsername()).
                password(studentRequest.getPassword()).
                balance(studentRequest.getBalance()).
                build();
        return studentRepository.save(student);
    }

    public Student updateStudent(Long studentId, StudentRequest studentRequest) {
        if(studentRequest.getUsername() == null || studentRequest.getPassword() == null) throw
                new EntityNotFoundException("username or password cannot be null");
        Optional<Student> temp = studentRepository.findById(studentId);
        if(temp.isPresent()){
            Student foundStudent = temp.get();
            Student student = studentRepository.findByUsername(studentRequest.getUsername());
            if(student != null && !studentId.equals(student.getId())) throw
                    new EntityExistsException("There is already a student with this username.");

            foundStudent.setUsername(studentRequest.getUsername());
            foundStudent.setPassword(studentRequest.getPassword());
            foundStudent.setBalance(studentRequest.getBalance());
            return studentRepository.save(foundStudent);
        }
        else throw new EntityNotFoundException("Invalid studentId");
    }

    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }
}
