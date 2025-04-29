package com.example.student_portal.controller;

import com.example.student_portal.model.Student;
import com.example.student_portal.service.StudentService;
import com.example.student_portal.util.PaginationUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "index";
    }

    @GetMapping("/students")
    public String listStudents(
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        List<Student> sortedStudents = studentService.getAllStudentsSorted(sort, direction);
        PaginationUtil<Student> pagination = new PaginationUtil<>(sortedStudents, size, page);

        model.addAttribute("students", pagination.getCurrentPageItems());
        model.addAttribute("currentPage", pagination.getCurrentPage());
        model.addAttribute("totalPages", pagination.getTotalPages());
        model.addAttribute("hasPrevious", pagination.hasPreviousPage());
        model.addAttribute("hasNext", pagination.hasNextPage());
        model.addAttribute("previousPage", pagination.getPreviousPage());
        model.addAttribute("nextPage", pagination.getNextPage());
        model.addAttribute("currentSort", sort);
        model.addAttribute("currentDirection", direction);
        model.addAttribute("pageSize", size);

        return "students/list";
    }

    @GetMapping("/students/{id}")
    public String viewStudent(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + id));
        model.addAttribute("student", student);
        return "students/view";
    }

    @GetMapping("/students/new")
    public String showNewStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "students/form";
    }

    @PostMapping("/students/save")
    public String saveStudent(@Valid @ModelAttribute("student") Student student,
                              BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "students/form";
        }

        studentService.saveStudent(student);
        redirectAttributes.addFlashAttribute("successMessage", "Student saved successfully!");
        return "redirect:/students";
    }

    @GetMapping("/students/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + id));
        model.addAttribute("student", student);
        return "students/form";
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        studentService.deleteStudent(id);
        redirectAttributes.addFlashAttribute("successMessage", "Student deleted successfully!");
        return "redirect:/students";
    }
    @GetMapping("/students/search")
    public String searchStudents(@RequestParam(required = false) String keyword, Model model) {
        List<Student> searchResults = studentService.searchStudents(keyword);
        model.addAttribute("students", searchResults);
        model.addAttribute("keyword", keyword);
        return "students/list";
    }
}