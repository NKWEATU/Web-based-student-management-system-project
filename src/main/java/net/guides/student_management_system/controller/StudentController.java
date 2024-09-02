package net.guides.student_management_system.controller;

import jakarta.validation.Valid;
import net.guides.student_management_system.dto.StudentDto;
import net.guides.student_management_system.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //handler method to handle list students request
    // http://localhost:8080/students
    @GetMapping("/students")
    public String listStudent(Model model) {
        List<StudentDto> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "students";
    }

    // handler method to handle new Student request
    // http://localhost:8080/students/new
    @GetMapping("/students/new")
    public String newStudent(Model model) {
        //student model object to store student form data
        StudentDto studentDto = new StudentDto();
        model.addAttribute("student", studentDto);
        return "create_student";
    }

    //handler method to handle save student form submit request
    @PostMapping("/students")
    public String saveStudent(@Valid @ModelAttribute("student") StudentDto student,
                              BindingResult result,
                              Model model) {

        if (result.hasErrors()) {
            model.addAttribute("student", student);
            return "create_student";
        }
        studentService.createStudent(student);
        return "redirect:/students";
    }

    // handler method to handle edit student request
    @GetMapping("/students/{studentId}/edit")
    public String editStudent(@PathVariable("studentId") Long studentId,
                              Model model) {
        StudentDto student = studentService.getStudentById(studentId);
        model.addAttribute("student", student);
        return "edit_student";
    }

    //handler method to handle edit student form submit request
    @PostMapping("/students/{studentId}")
    public String updateStudent(@PathVariable("studentId") Long studentId,
                                @Valid @ModelAttribute("student") StudentDto studentDto,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("student", studentDto);
            return "edit_student";
        }
        studentDto.setId(studentId);
        studentService.updateStudent(studentDto);
        return "redirect:/students";
    }

    //handler method to handle delete student request
    @GetMapping("/students/{studentId}/delete")
    public String deleteStudent(@PathVariable("studentId") Long studentId){
        studentService.deleteStudent(studentId);
        return "redirect:/students";
    }

    //handler method to handle student request
    @GetMapping("/students/{studentId}/view")
    public String viewStudent(@PathVariable("studentId") Long studentId,
                              Model model){
        StudentDto studentDto = studentService.getStudentById(studentId);
        model.addAttribute("student", studentDto);
        return "view_student";

    }
}

