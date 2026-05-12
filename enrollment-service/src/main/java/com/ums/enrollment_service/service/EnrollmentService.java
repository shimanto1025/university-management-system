package com.ums.enrollment_service.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.ums.enrollment_service.model.Enrollment;
import com.ums.enrollment_service.repository.EnrollmentRepository;

@Service
public class EnrollmentService {
    
    private final EnrollmentRepository enrollmentRepository;
    private final RestClient restClient;
    
    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.restClient = RestClient.builder().build();
    }
    
    public Enrollment createEnrollment(Long studentId, Long courseId) {
        // Check student exists
        try {
            restClient.get()
                .uri("http://student-service:8081/api/students/" + studentId)
                .retrieve()
                .toBodilessEntity();
        } catch (Exception e) {
            throw new RuntimeException("Student not found: " + studentId);
        }
        
        // Check course exists
        try {
            restClient.get()
                .uri("http://course-service:8082/api/courses/" + courseId)
                .retrieve()
                .toBodilessEntity();
        } catch (Exception e) {
            throw new RuntimeException("Course not found: " + courseId);
        }
        
        // Check duplicate
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
   
