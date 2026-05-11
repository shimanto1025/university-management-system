package com.ums.course_service.service;

import com.ums.course_service.model.Course;
import com.ums.course_service.repository.CourseRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseService {
    
    private final CourseRepository courseRepository;
    
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    
    public Course createCourse(Course course) {
        if (courseRepository.existsByCourseCode(course.getCourseCode())) {
            throw new RuntimeException("Course code exists: " + course.getCourseCode());
        }
        return courseRepository.save(course);
    }
    
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Course not found: " + id));
    }
    
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    
    public Course updateCourse(Long id, Course details) {
        Course course = getCourseById(id);
        course.setTitle(details.getTitle());
        course.setCredits(details.getCredits());
        course.setMaxCapacity(details.getMaxCapacity());
        course.setCurrentEnrolled(details.getCurrentEnrolled());
        course.setTeacherName(details.getTeacherName());
        return courseRepository.save(course);
    }
    
    public void deleteCourse(Long id) {
        courseRepository.delete(getCourseById(id));
    }
}