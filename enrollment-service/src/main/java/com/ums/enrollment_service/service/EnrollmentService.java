package com.ums.enrollment_service.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ums.enrollment_service.model.Enrollment;
import com.ums.enrollment_service.repository.EnrollmentRepository;

@Service
public class EnrollmentService {
    
    private final EnrollmentRepository enrollmentRepository;
    private final RestTemplate restTemplate;
    
    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.restTemplate = new RestTemplate();
    }
    
    public Enrollment createEnrollment(Long studentId, Long courseId) {
        // Check if student exists
        try {
            restTemplate.getForObject(
                "http://localhost:8081/api/students/" + studentId, Object.class);
        } catch (Exception e) {
            throw new RuntimeException("Student not found: " + studentId);
        }
        
        // Check if course exists
        try {
            restTemplate.getForObject(
                "http://localhost:8082/api/courses/" + courseId, Object.class);
        } catch (Exception e) {
            throw new RuntimeException("Course not found: " + courseId);
        }
        
        // Check if already enrolled
        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new RuntimeException("Student already enrolled in this course");
        }
        
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(studentId);
        enrollment.setCourseId(courseId);
        
        return enrollmentRepository.save(enrollment);
    }
    
    public List<Enrollment> getEnrollmentsByStudent(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }
    
    public List<Enrollment> getEnrollmentsByCourse(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }
    
    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }
}