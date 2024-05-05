package com.burak.project.repository;

import com.burak.project.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEnrollmentRepository extends JpaRepository<Enrollment, Long> {
}
