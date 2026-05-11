package com.ums.student_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ums.student_service.model.Student;
import com.ums.student_service.repository.StudentRepository;

@Service
public class StudentService {
    
    private final StudentRepository studentRepository;
    
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    
    public Student createStudent(Student student) {
        if (studentRepository.existsByStudentCode(student.getStudentCode())) {
            throw new RuntimeException("Student code exists: " + student.getStudentCode());
        }
        return studentRepository.save(student);
    }
    
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found: " + id));
    }
    
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    public Student updateStudent(Long id, Student details) {
        Student student = getStudentById(id);
        student.setFullName(details.getFullName());
        student.setEmail(details.getEmail());
        student.setPhone(details.getPhone());
        student.setDateOfBirth(details.getDateOfBirth());
        return studentRepository.save(student);
    }
    
    public void deleteStudent(Long id) {
        studentRepository.delete(getStudentById(id));
    }
}