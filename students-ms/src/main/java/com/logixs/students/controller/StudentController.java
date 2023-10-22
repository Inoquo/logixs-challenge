package com.logixs.students.controller;


import com.logixs.students.dto.StudentDTO;
import com.logixs.students.mapper.StudentMapper;
import com.logixs.students.model.Student;
import com.logixs.students.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Students")
public class StudentController {

    private final StudentService studentService;

    private final StudentMapper studentMapper;

    @Autowired
    public StudentController(StudentService studentService, StudentMapper studentMapper) {
        this.studentService = studentService;
        this.studentMapper = studentMapper;
    }

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        try {
            if (Optional.ofNullable(studentDTO).isEmpty()) return ResponseEntity.badRequest().build();
            Student newStudent = studentService.createStudent(studentMapper.toEntity(studentDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(studentMapper.toDTO(newStudent));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable Long studentId) {
        try {
            if (Optional.ofNullable(studentId).isEmpty()) return ResponseEntity.badRequest().build();
            Optional<Student> course = studentService.getStudentById(studentId);
            return course.map(value -> ResponseEntity.ok(studentMapper.toDTO(value)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long studentId, @RequestBody StudentDTO studentDTO) {
        try {
            if (Optional.ofNullable(studentId).isEmpty() || Optional.ofNullable(studentDTO).isEmpty())
                return ResponseEntity.badRequest().build();
            Student studentUpdated = studentService.updateStudent(studentId, studentMapper.toEntity(studentDTO));
            return ResponseEntity.ok(studentMapper.toDTO(studentUpdated));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {
        try {
            if (Optional.ofNullable(studentId).isEmpty()) return ResponseEntity.badRequest().build();
            studentService.deleteStudent(studentId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> listStudents() {
        try {
            List<Student> students = studentService.listStudents();
            if (students.isEmpty()) return ResponseEntity.noContent().build();
            return ResponseEntity.ok(students.parallelStream().map(studentMapper::toDTO).toList());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
