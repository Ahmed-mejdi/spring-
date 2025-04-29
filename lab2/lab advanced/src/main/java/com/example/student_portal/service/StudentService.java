package com.example.student_portal.service;

import com.example.student_portal.model.Student;
import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> getAllStudents();
    Optional<Student> getStudentById(Long id);
    Student saveStudent(Student student);
    void deleteStudent(Long id);
    List<Student> searchStudents(String keyword);
    List<Student> getAllStudentsSorted(String sortBy, String direction);
}