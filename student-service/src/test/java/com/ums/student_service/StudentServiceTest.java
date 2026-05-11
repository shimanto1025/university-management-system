package com.ums.student_service;

import com.ums.student_service.model.Student;
import com.ums.student_service.repository.StudentRepository;
import com.ums.student_service.service.StudentService;
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
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    public void setUp() {
        student = new Student();
        student.setId(1L);
        student.setStudentCode("STU001");
        student.setFullName("John Doe");
        student.setEmail("john@example.com");
        student.setPhone("1234567890");
        student.setDateOfBirth("2000-01-15");
    }

    @Test
    void testCreateStudent_Success() {
        when(studentRepository.existsByStudentCode("STU001")).thenReturn(false);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student created = studentService.createStudent(student);

        assertNotNull(created);
        assertEquals("STU001", created.getStudentCode());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void testCreateStudent_DuplicateCode() {
        when(studentRepository.existsByStudentCode("STU001")).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> studentService.createStudent(student));
        assertNotNull(exception);
        verify(studentRepository, never()).save(any());
    }

    @Test
    void testGetStudentById_Success() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Student found = studentService.getStudentById(1L);

        assertNotNull(found);
        assertEquals("John Doe", found.getFullName());
    }

    @Test
    void testGetStudentById_NotFound() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> studentService.getStudentById(99L));
        assertNotNull(exception);
    }

    @Test
    void testDeleteStudent_Success() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).delete(student);

        studentService.deleteStudent(1L);

        verify(studentRepository, times(1)).delete(student);
    }
}