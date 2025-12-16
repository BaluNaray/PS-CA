package com.example.student.controller;

import com.example.student.entity.Student;
import com.example.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        log.info("StudentController::createStudent START with details: {}", student);
        Student createdStudent = studentService.saveStudent(student);
        log.info("StudentController::createStudent END");
        return createdStudent;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        log.info("StudentController::getAllStudents START");
        List<Student> students = studentService.getAllStudents();
        log.info("StudentController::getAllStudents END");
        return students;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        log.info("StudentController::getStudentById START with id: {}", id);
        Optional<Student> student = studentService.getStudentById(id);
        log.info("StudentController::getStudentById END");
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        log.info("StudentController::updateStudent START with id: {}", id);
        Student updatedStudent = studentService.updateStudent(id, studentDetails);
        if (updatedStudent != null) {
            log.info("StudentController::updateStudent END");
            return ResponseEntity.ok(updatedStudent);
        }
        log.info("StudentController::updateStudent END (Not Found)");
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        log.info("StudentController::deleteStudent START with id: {}", id);
        studentService.deleteStudent(id);
        log.info("StudentController::deleteStudent END");
        return ResponseEntity.ok().build();
    }
}
