package com.logixs.students.controller;

import com.logixs.students.dto.StudentDTO;
import com.logixs.students.mapper.StudentMapper;
import com.logixs.students.model.Student;
import com.logixs.students.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private StudentController studentController;

    @MockBean
    private StudentService studentService;

    @MockBean
    private StudentMapper studentMapper;


    @Test
    public void test_createStudent_validData_returns201Created() {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("John");
        studentDTO.setSurname("Doe");
        studentDTO.setBirthDate(LocalDate.now());
        Set<Long> enrolledCourses = new HashSet<>();
        enrolledCourses.add(1L);
        enrolledCourses.add(2L);
        studentDTO.setEnrolledCourses(enrolledCourses);

        Student student = new Student();
        student.setName("John");
        student.setSurname("Doe");
        student.setBirthDate(LocalDate.now());
        student.setEnrolledCourses(enrolledCourses);

        when(studentMapper.toEntity(studentDTO)).thenReturn(student);

        when(studentService.createStudent(student)).thenReturn(student);

        when(studentMapper.toDTO(student)).thenReturn(studentDTO);

        ResponseEntity<StudentDTO> response = studentController.createStudent(studentDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(studentDTO.getName(), response.getBody().getName());
        assertEquals(studentDTO.getSurname(), response.getBody().getSurname());
        assertEquals(studentDTO.getBirthDate(), response.getBody().getBirthDate());
        assertEquals(studentDTO.getEnrolledCourses(), response.getBody().getEnrolledCourses());
    }

    @Test
    public void test_getStudent_existingId_returns200OkWithStudentData() {
        Long studentId = 1L;

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(studentId);
        studentDTO.setName("John");
        studentDTO.setSurname("Doe");
        studentDTO.setBirthDate(LocalDate.now());
        Set<Long> enrolledCourses = new HashSet<>();
        enrolledCourses.add(1L);
        enrolledCourses.add(2L);
        studentDTO.setEnrolledCourses(enrolledCourses);

        Student student = new Student();
        student.setId(studentId);
        student.setName("John");
        student.setSurname("Doe");
        student.setBirthDate(LocalDate.now());
        student.setEnrolledCourses(enrolledCourses);

        when(studentService.getStudentById(studentId)).thenReturn(Optional.of(student));

        when(studentMapper.toDTO(student)).thenReturn(studentDTO);

        ResponseEntity<StudentDTO> response = studentController.getStudent(studentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(studentId, response.getBody().getId());
    }

    @Test
    public void test_updateStudent_existingIdAndValidData_returns200OkWithUpdatedStudentData() {
        Long studentId = 1L;
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(studentId);
        studentDTO.setName("John");
        studentDTO.setSurname("Doe");
        studentDTO.setBirthDate(LocalDate.now());
        Set<Long> enrolledCourses = new HashSet<>();
        enrolledCourses.add(1L);
        enrolledCourses.add(2L);
        studentDTO.setEnrolledCourses(enrolledCourses);

        Student student = new Student();
        student.setId(studentId);
        student.setName("John");
        student.setSurname("Doe");
        student.setBirthDate(LocalDate.now());
        student.setEnrolledCourses(enrolledCourses);

        when(studentMapper.toDTO(student)).thenReturn(studentDTO);
        when(studentMapper.toEntity(studentDTO)).thenReturn(student);

        when(studentService.updateStudent(studentId, student)).thenReturn(student);

        ResponseEntity<StudentDTO> response = studentController.updateStudent(studentId, studentDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(studentId, response.getBody().getId());
        assertEquals(studentDTO.getName(), response.getBody().getName());
        assertEquals(studentDTO.getSurname(), response.getBody().getSurname());
        assertEquals(studentDTO.getBirthDate(), response.getBody().getBirthDate());
        assertEquals(studentDTO.getEnrolledCourses(), response.getBody().getEnrolledCourses());
    }

    @Test
    public void test_createStudent_invalidData_returns400BadRequest() {

        ResponseEntity<StudentDTO> response = studentController.createStudent(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void test_getStudent_nonExistingId_returns404NotFound() {
        Long studentId = 100L;

        when(studentService.getStudentById(studentId)).thenReturn(Optional.empty());

        ResponseEntity<StudentDTO> response = studentController.getStudent(studentId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    public void test_deleteExistingStudent() {
        Long studentId = 1L;

        ResponseEntity<Void> response = studentController.deleteStudent(studentId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void test_returnNoContentAfterDeletingStudent() {
        Long studentId = 1L;

        ResponseEntity<Void> response = studentController.deleteStudent(studentId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void test_attemptToDeleteNonExistingStudent() {
        Long studentId = 1L;

        Mockito.doThrow(new RuntimeException()).when(studentService).deleteStudent(studentId);

        ResponseEntity<Void> response = studentController.deleteStudent(studentId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void test_attemptToDeleteStudentWithNullId() {


        ResponseEntity<Void> response = studentController.deleteStudent(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());


    }

    @Test
    public void test_returns_list_of_studentDTO_objects_when_students_exist() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1L, "John", "Doe", LocalDate.now(), new HashSet<>()));
        students.add(new Student(2L, "Jane", "Smith", LocalDate.now(), new HashSet<>()));
        when(studentService.listStudents()).thenReturn(students);

        ResponseEntity<List<StudentDTO>> response = studentController.listStudents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }


    @Test
    public void test_no_students_in_database() {
        List<Student> students = new ArrayList<>();
        when(studentService.listStudents()).thenReturn(students);

        ResponseEntity<List<StudentDTO>> response = studentController.listStudents();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void test_internal_server_error_response() {
        when(studentService.listStudents()).thenThrow(new RuntimeException());

        ResponseEntity<List<StudentDTO>> response = studentController.listStudents();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


    @Test
    public void test_returns_badRequest_when_studentDTO_is_null() {
        StudentDTO studentDTO = null;

        ResponseEntity<StudentDTO> response = studentController.createStudent(studentDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void test_listStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1L, "John", "Doe", LocalDate.now(), new HashSet<>()));
        students.add(new Student(2L, "Jane", "Smith", LocalDate.now(), new HashSet<>()));
        when(studentService.listStudents()).thenReturn(students);

        ResponseEntity<List<StudentDTO>> response = studentController.listStudents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(students.size(), response.getBody().size());
    }

    @Test
    public void test_listStudents_returnsStudentDTOListInSameOrder() {
        List<Student> students = new ArrayList<>();
        Student student1 = new Student(1L, "John", "Doe", LocalDate.now(), new HashSet<>());
        Student student2 = new Student(2L, "Jane", "Smith", LocalDate.now(), new HashSet<>());
        students.add(student1);
        students.add(student2);

        when(studentService.listStudents()).thenReturn(students);

        StudentDTO studentDTO1 = new StudentDTO(1L, "John", "Doe", LocalDate.now(), new HashSet<>());
        StudentDTO studentDTO2 = new StudentDTO(2L, "Jane", "Smith", LocalDate.now(), new HashSet<>());

        when(studentMapper.toDTO(student1)).thenReturn(studentDTO1);
        when(studentMapper.toDTO(student2)).thenReturn(studentDTO2);

        ResponseEntity<List<StudentDTO>> response = studentController.listStudents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<StudentDTO> studentDTOS = response.getBody();
        assertNotNull(studentDTOS);
        assertEquals(2, studentDTOS.size());
        assertEquals(studentDTO1, studentDTOS.get(0));
        assertEquals(studentDTO2, studentDTOS.get(1));
    }

    @Test
    public void test_noContentResponseWhenListIsNull() {
        when(studentService.listStudents()).thenReturn(List.of());

        ResponseEntity<List<StudentDTO>> response = studentController.listStudents();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void test_noContentResponseWhenListIsEmpty() {
        List<Student> emptyList = new ArrayList<>();
        when(studentService.listStudents()).thenReturn(emptyList);

        ResponseEntity<List<StudentDTO>> response = studentController.listStudents();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void test_listStudents_returnsListOfStudentDTO() {
        List<Student> students = new ArrayList<>();
        Student student1 = new Student(1L, "John", "Doe", LocalDate.now(), new HashSet<>());
        Student student2 = new Student(2L, "Jane", "Smith", LocalDate.now(), new HashSet<>());
        students.add(student1);
        students.add(student2);

        when(studentService.listStudents()).thenReturn(students);

        StudentDTO studentDTO1 = new StudentDTO(1L, "John", "Doe", LocalDate.now(), new HashSet<>());
        StudentDTO studentDTO2 = new StudentDTO(2L, "Jane", "Smith", LocalDate.now(), new HashSet<>());
        List<StudentDTO> expected = Arrays.asList(studentDTO1, studentDTO2);

        when(studentMapper.toDTO(student1)).thenReturn(studentDTO1);
        when(studentMapper.toDTO(student2)).thenReturn(studentDTO2);

        ResponseEntity<List<StudentDTO>> response = studentController.listStudents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }
}



