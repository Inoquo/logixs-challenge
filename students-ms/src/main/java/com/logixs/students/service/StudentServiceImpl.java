package com.logixs.students.service;

import com.logixs.students.model.Student;
import com.logixs.students.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student createStudent(Student student) {
        return studentRepository.save(student);

    }

    public Optional<Student> getStudentById(Long studentId) {
        return studentRepository.findById(studentId);
    }

    public Student updateStudent(Long studentId, Student student) {
        if (studentRepository.existsById(studentId)) {
            student.setId(studentId);
            return studentRepository.save(student);
        }
        return null;
    }

    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    public List<Student> listStudents() {
        return studentRepository.findAll();
    }
}
