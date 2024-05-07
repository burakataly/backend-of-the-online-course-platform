package com.burak.project.repository;

import com.burak.project.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStudentRepository extends JpaRepository<Student, Long> {
    Student findByUsername(String username);
}
