package com.logixs.courses.service;

import com.logixs.courses.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    Course createCourse(Course course);

    Optional<Course> getCourseById(Long courseId);

    Course updateCourse(Long courseId, Course course);

    void deleteCourse(Long courseId);

    List<Course> listCourses();

    Course enrolStudentInCourse(Long courseId, Long studentId);

    Course unenrolStudentFromCourse(Long courseId, Long studentId);

}
