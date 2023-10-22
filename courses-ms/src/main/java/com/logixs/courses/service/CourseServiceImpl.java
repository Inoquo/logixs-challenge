package com.logixs.courses.service;

import com.logixs.courses.model.Course;
import com.logixs.courses.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Optional<Course> getCourseById(Long courseId) {
        return courseRepository.findById(courseId);
    }

    public Course updateCourse(Long courseId, Course course) {
        if (courseRepository.existsById(courseId)) {
            course.setId(courseId);
            return courseRepository.save(course);
        }
        return null;
    }

    public void deleteCourse(Long courseId) {
        courseRepository.deleteById(courseId);
    }

    public List<Course> listCourses() {
        return courseRepository.findAll();
    }


    public Course enrolStudentInCourse(Long courseId, Long studentId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            course.getEnrolledStudents().add(studentId);
            return courseRepository.save(course);
        }
        return null;
    }

    public Course unenrolStudentFromCourse(Long courseId, Long studentId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            course.getEnrolledStudents().remove(studentId);
            return courseRepository.save(course);
        }
        return null;
    }
}
