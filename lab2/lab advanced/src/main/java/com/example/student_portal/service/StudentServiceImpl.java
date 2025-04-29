package com.example.student_portal.service;

import com.example.student_portal.model.Student;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class StudentServiceImpl implements StudentService {

    // In-memory database using a Map
    private final Map<Long, Student> studentDb = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    public StudentServiceImpl() {
        // Add some sample data
        Student student1 = new Student(idCounter.incrementAndGet(), "John Doe", 21,
                "john@example.com", "Computer Science", "CS123456");
        Student student2 = new Student(idCounter.incrementAndGet(), "Jane Smith", 22,
                "jane@example.com", "Mathematics", "MT789012");
        Student student3 = new Student(idCounter.incrementAndGet(), "Bob Johnson", 20,
                "bob@example.com", "Physics", "PH345678");

        studentDb.put(student1.getId(), student1);
        studentDb.put(student2.getId(), student2);
        studentDb.put(student3.getId(), student3);
    }

    @Override
    public List<Student> getAllStudents() {
        return new ArrayList<>(studentDb.values());
    }

    @Override
    public Optional<Student> getStudentById(Long id) {
        return Optional.ofNullable(studentDb.get(id));
    }

    @Override
    public Student saveStudent(Student student) {
        if (student.getId() == null) {
            // New student
            student.setId(idCounter.incrementAndGet());
        }
        studentDb.put(student.getId(), student);
        return student;
    }

    @Override
    public void deleteStudent(Long id) {
        studentDb.remove(id);
    }
    @Override
    public List<Student> searchStudents(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllStudents();
        }

        keyword = keyword.toLowerCase();
        List<Student> result = new ArrayList<>();

        for (Student student : studentDb.values()) {
            if (student.getName().toLowerCase().contains(keyword) ||
                    student.getEmail().toLowerCase().contains(keyword) ||
                    student.getCourse().toLowerCase().contains(keyword) ||
                    student.getStudentId().toLowerCase().contains(keyword)) {
                result.add(student);
            }
        }

        return result;
    }
    @Override
    public List<Student> getAllStudentsSorted(String sortBy, String direction) {
        List<Student> students = getAllStudents();

        Comparator<Student> comparator = null;

        switch (sortBy) {
            case "name":
                comparator = Comparator.comparing(Student::getName);
                break;
            case "age":
                comparator = Comparator.comparing(Student::getAge);
                break;
            case "email":
                comparator = Comparator.comparing(Student::getEmail);
                break;
            case "course":
                comparator = Comparator.comparing(Student::getCourse);
                break;
            case "studentId":
                comparator = Comparator.comparing(Student::getStudentId);
                break;
            default:
                comparator = Comparator.comparing(Student::getId);
        }

        if ("desc".equals(direction)) {
            comparator = comparator.reversed();
        }

        students.sort(comparator);
        return students;
    }
}