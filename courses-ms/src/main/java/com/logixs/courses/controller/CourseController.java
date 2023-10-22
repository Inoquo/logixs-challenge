package com.logixs.courses.controller;

import com.logixs.courses.dto.CourseDTO;
import com.logixs.courses.dto.StudentDTO;
import com.logixs.courses.mapper.CourseMapper;
import com.logixs.courses.model.Course;
import com.logixs.courses.restClient.StudentRestClient;
import com.logixs.courses.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    private final CourseMapper courseMapper;

    private final StudentRestClient studentRestClient;

    @Autowired
    public CourseController(CourseService courseService, CourseMapper courseMapper, StudentRestClient studentRestClient) {
        this.courseService = courseService;
        this.courseMapper = courseMapper;
        this.studentRestClient = studentRestClient;
    }

    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO courseDTO) {
        try {
            if (Optional.ofNullable(courseDTO).isEmpty()) return ResponseEntity.badRequest().build();
            Course newCourse = courseService.createCourse(courseMapper.toEntity(courseDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(courseMapper.toDTO(newCourse));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDTO> getCourse(@PathVariable Long courseId) {
        try {
            if (Optional.ofNullable(courseId).isEmpty()) return ResponseEntity.badRequest().build();
            Optional<Course> course = courseService.getCourseById(courseId);
            return course.map(value -> ResponseEntity.ok(courseMapper.toDTO(value)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long courseId, @RequestBody CourseDTO courseDTO) {
        try {
            if (Optional.ofNullable(courseId).isEmpty() || Optional.ofNullable(courseDTO).isEmpty())
                return ResponseEntity.badRequest().build();
            Course courseUpdated = courseService.updateCourse(courseId, courseMapper.toEntity(courseDTO));
            return ResponseEntity.ok(courseMapper.toDTO(courseUpdated));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) {
        try {
            if (Optional.ofNullable(courseId).isEmpty()) return ResponseEntity.badRequest().build();
            courseService.deleteCourse(courseId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> listarCourses() {
        try {
            List<Course> courses = courseService.listCourses();
            if (courses.isEmpty()) return ResponseEntity.noContent().build();
            return ResponseEntity.ok(courses.parallelStream().map(courseMapper::toDTO).toList());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/{courseId}/enrol/{studentId}")
    public ResponseEntity<String> enrolStudent(@PathVariable Long courseId, @PathVariable Long studentId) {
        try {
            if (Optional.ofNullable(courseId).isEmpty() || Optional.ofNullable(studentId).isEmpty())
                return ResponseEntity.badRequest().build();
            StudentDTO student = studentRestClient.obtenerStudent(studentId);
            student.getEnrolledCourses().add(courseId);
            studentRestClient.updateStudent(student.getId(), student);
            courseService.enrolStudentInCourse(courseId, studentId);
            return ResponseEntity.ok("Student enrolled in course");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/{courseId}/unenrol/{studentId}")
    public ResponseEntity<String> unenrolStudent(@PathVariable Long courseId, @PathVariable Long studentId) {
        try {
            if (Optional.ofNullable(courseId).isEmpty() || Optional.ofNullable(studentId).isEmpty())
                return ResponseEntity.badRequest().build();
            StudentDTO student = studentRestClient.obtenerStudent(studentId);
            student.getEnrolledCourses().remove(courseId);
            studentRestClient.updateStudent(student.getId(), student);
            courseService.unenrolStudentFromCourse(courseId, studentId);
            return ResponseEntity.ok("Student un-enrolled from course");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

