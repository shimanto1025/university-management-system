package com.ums.course_service;

import com.ums.course_service.model.Course;
import com.ums.course_service.repository.CourseRepository;
import com.ums.course_service.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    private Course course;

    @BeforeEach
    public void setUp() {
        course = new Course();
        course.setId(1L);
        course.setCourseCode("CS101");
        course.setTitle("Software Engineering");
        course.setCredits(3);
        course.setMaxCapacity(30);
        course.setTeacherName("Dr. Smith");
    }

    @Test
    void testCreateCourse_Success() {
        when(courseRepository.existsByCourseCode("CS101")).thenReturn(false);
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course created = courseService.createCourse(course);

        assertNotNull(created);
        assertEquals("CS101", created.getCourseCode());
    }

    @Test
    void testCreateCourse_DuplicateCode() {
        when(courseRepository.existsByCourseCode("CS101")).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> courseService.createCourse(course));
        assertNotNull(exception);
    }

    @Test
    void testGetCourseById_Success() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        Course found = courseService.getCourseById(1L);

        assertNotNull(found);
        assertEquals("Software Engineering", found.getTitle());
    }

    @Test
    void testGetCourseById_NotFound() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> courseService.getCourseById(99L));
        assertNotNull(exception);
    }
}