package com.example.student.service;

import com.example.student.entity.Student;
import com.example.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private static final Logger log = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentRepository studentRepository;

    public Student saveStudent(Student student) {
        log.info("StudentService::saveStudent START with details: {}", student);
        Student savedStudent = studentRepository.save(student);
        log.info("StudentService::saveStudent END");
        return savedStudent;
    }

    public List<Student> getAllStudents() {
        log.info("StudentService::getAllStudents START");
        List<Student> students = studentRepository.findAll();
        log.info("StudentService::getAllStudents END");
        return students;
    }

    public Optional<Student> getStudentById(Long id) {
        log.info("StudentService::getStudentById START with id: {}", id);
        Optional<Student> student = studentRepository.findById(id);
        log.info("StudentService::getStudentById END");
        return student;
    }

    public Student updateStudent(Long id, Student studentDetails) {
        log.info("StudentService::updateStudent START with id: {}", id);
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            Student existingStudent = studentOptional.get();
            existingStudent.setDob(studentDetails.getDob());
            existingStudent.setStudentClass(studentDetails.getStudentClass());
            existingStudent.setDivision(studentDetails.getDivision());
            existingStudent.setAddress(studentDetails.getAddress());
            Student updatedStudent = studentRepository.save(existingStudent);
            log.info("StudentService::updateStudent END");
            return updatedStudent;
        }
        log.info("StudentService::updateStudent END (Not Found)");
        return null;
    }

    public void deleteStudent(Long id) {
        log.info("StudentService::deleteStudent START with id: {}", id);
        studentRepository.deleteById(id);
        log.info("StudentService::deleteStudent END");
    }
}
