package com.misbah.student_service.controller;

import com.misbah.student_service.model.Student;
import com.misbah.student_service.services.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @GetMapping("/{id}")
    public ResponseEntity<?> fetchStudentById(@PathVariable String id, HttpServletRequest httpServletRequest){
        return studentService.fetchStudentById(id,httpServletRequest);
    }
    @GetMapping
    public ResponseEntity<?> fetchStudents(){
        return studentService.fetchStudents();
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student student){
        return studentService.createStudent(student);
    }

}
