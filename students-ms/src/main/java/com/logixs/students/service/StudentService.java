package com.logixs.students.service;

import com.logixs.students.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    Student createStudent(Student student);

    Optional<Student> getStudentById(Long studentId);

    Student updateStudent(Long studentId, Student student);

    void deleteStudent(Long studentId);

    List<Student> listStudents();
}
