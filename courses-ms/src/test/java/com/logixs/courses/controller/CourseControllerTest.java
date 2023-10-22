package com.logixs.courses.controller;

import com.logixs.courses.dto.CourseDTO;
import com.logixs.courses.mapper.CourseMapper;
import com.logixs.courses.model.Course;
import com.logixs.courses.restClient.StudentRestClient;
import com.logixs.courses.service.CourseService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@WebMvcTest(CourseController.class)
public class CourseControllerTest {

    @Autowired
    private CourseController courseController;

    @MockBean
    private CourseService courseService;

    @MockBean
    private CourseMapper courseMapper;

    @MockBean
    private StudentRestClient studentRestClient;

    @Test
    public void test_createCourseWithValidData() {

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setName("English Course");
        courseDTO.setDescription("Learn English");

        Course course = new Course();
        course.setName("English Course");
        course.setDescription("Learn English");

        Mockito.when(courseMapper.toEntity(courseDTO)).thenReturn(course);

        Mockito.when(courseService.createCourse(course)).thenReturn(course);

        Mockito.when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        ResponseEntity<CourseDTO> response = courseController.createCourse(courseDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertEquals(courseDTO, response.getBody());
    }

    @Test
    public void test_retrieveExistingCourseById() {

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(1L);
        courseDTO.setName("English Course");
        courseDTO.setDescription("Learn English");

        Course course = new Course();
        course.setId(1L);
        course.setName("English Course");
        course.setDescription("Learn English");

        Mockito.when(courseService.getCourseById(1L)).thenReturn(Optional.of(course));

        Mockito.when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        ResponseEntity<CourseDTO> response = courseController.getCourse(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(courseDTO, response.getBody());
    }

    @Test
    public void test_updateExistingCourseWithValidData() {

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(1L);
        courseDTO.setName("English Course");
        courseDTO.setDescription("Learn English");

        Course course = new Course();
        course.setId(1L);
        course.setName("English Course");
        course.setDescription("Learn English");

        Mockito.when(courseService.updateCourse(1L, course)).thenReturn(course);

        Mockito.when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        Mockito.when(courseMapper.toEntity(courseDTO)).thenReturn(course);

        ResponseEntity<CourseDTO> response = courseController.updateCourse(1L, courseDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(courseDTO, response.getBody());
    }

    @Test
    public void test_createCourseWithInvalidData() {

        ResponseEntity<CourseDTO> response = courseController.createCourse(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void test_retrieveNonExistentCourseById() {

        Mockito.when(courseService.getCourseById(1L)).thenReturn(Optional.empty());

        ResponseEntity<CourseDTO> response = courseController.getCourse(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    public void test_deleteCourse_validCourseId() {
        Long courseId = 1L;

        ResponseEntity<Void> response = courseController.deleteCourse(courseId);

        Mockito.verify(courseService).deleteCourse(courseId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void test_deleteCourse_noContentResponse() {
        Long courseId = 1L;

        ResponseEntity<Void> response = courseController.deleteCourse(courseId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void test_deleteCourse_nullCourseId() {
        Long courseId = null;
        CourseService courseServiceMock = Mockito.mock(CourseService.class);
        CourseController courseController = new CourseController(courseServiceMock, null, null);

        ResponseEntity<Void> response = courseController.deleteCourse(courseId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void test_deleteCourse_internalServerError() {
        Long courseId = 1L;
        Mockito.doThrow(new RuntimeException()).when(courseService).deleteCourse(courseId);

        ResponseEntity<Void> response = courseController.deleteCourse(courseId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void test_deleteCourse_callsDeleteCourse() {
        Long courseId = 1L;

        ResponseEntity<Void> response = courseController.deleteCourse(courseId);

        Mockito.verify(courseService).deleteCourse(courseId);
    }

    @Test
    public void test_return_list_of_courseDTO_objects_when_courses_exist() {
        List<Course> courses = new ArrayList<>();
        Course course1 = new Course();
        course1.setId(1L);
        course1.setName("Course 1");
        course1.setDescription("Description of Course 1");
        course1.setStartDate(LocalDate.now());
        course1.setEndDate(LocalDate.now());
        courses.add(course1);

        Course course2 = new Course();
        course2.setId(2L);
        course2.setName("Course 2");
        course2.setDescription("Description of Course 2");
        course2.setStartDate(LocalDate.now());
        course2.setEndDate(LocalDate.now());
        courses.add(course2);

        CourseDTO courseDTO1 = new CourseDTO();
        courseDTO1.setId(1L);
        courseDTO1.setName("Course 1");
        courseDTO1.setDescription("Description of Course 1");
        courseDTO1.setStartDate(LocalDate.now());
        courseDTO1.setEndDate(LocalDate.now());

        CourseDTO courseDTO2 = new CourseDTO();
        courseDTO2.setId(2L);
        courseDTO2.setName("Course 2");
        courseDTO2.setDescription("Description of Course 2");
        courseDTO2.setStartDate(LocalDate.now());
        courseDTO2.setEndDate(LocalDate.now());

        Mockito.when(courseService.listCourses()).thenReturn(courses);
        Mockito.when(courseMapper.toDTO(course1)).thenReturn(courseDTO1);
        Mockito.when(courseMapper.toDTO(course2)).thenReturn(courseDTO2);


        ResponseEntity<List<CourseDTO>> response = courseController.listCourses();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(courseDTO1.getId(), response.getBody().get(0).getId());
        assertEquals(courseDTO1.getName(), response.getBody().get(0).getName());
        assertEquals(courseDTO1.getDescription(), response.getBody().get(0).getDescription());
        assertEquals(courseDTO1.getStartDate(), response.getBody().get(0).getStartDate());
        assertEquals(courseDTO1.getEndDate(), response.getBody().get(0).getEndDate());
        assertEquals(courseDTO2.getId(), response.getBody().get(1).getId());
        assertEquals(courseDTO2.getName(), response.getBody().get(1).getName());
        assertEquals(courseDTO2.getDescription(), response.getBody().get(1).getDescription());
        assertEquals(courseDTO2.getStartDate(), response.getBody().get(1).getStartDate());
        assertEquals(courseDTO2.getEndDate(), response.getBody().get(1).getEndDate());
    }

    @Test
    public void test_return_no_content_response_when_no_courses_exist() {
        List<Course> courses = new ArrayList<>();
        Mockito.when(courseService.listCourses()).thenReturn(courses);

        ResponseEntity<List<CourseDTO>> response = courseController.listCourses();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void test_return_response_with_HTTP_status_code_200_when_courses_exist() {
        List<Course> courses = new ArrayList<>();
        Course course1 = new Course();
        course1.setId(1L);
        course1.setName("Course 1");
        course1.setDescription("Description of Course 1");
        course1.setStartDate(LocalDate.now());
        course1.setEndDate(LocalDate.now());
        courses.add(course1);

        Mockito.when(courseService.listCourses()).thenReturn(courses);

        ResponseEntity<List<CourseDTO>> response = courseController.listCourses();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void test_handle_and_return_internal_server_error_response_when_exception_occurs() {
        Mockito.when(courseService.listCourses()).thenThrow(new RuntimeException());

        ResponseEntity<List<CourseDTO>> response = courseController.listCourses();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void test_http_status_code_400_null_course_id() {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(1L);
        courseDTO.setName("English Course");
        courseDTO.setDescription("Learn English");

        Course course = new Course();
        course.setId(1L);
        course.setName("English Course");
        course.setDescription("Learn English");

        Mockito.when(courseService.updateCourse(1L, course)).thenReturn(course);

        Mockito.when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        Mockito.when(courseMapper.toEntity(courseDTO)).thenReturn(course);

        ResponseEntity<CourseDTO> response = courseController.updateCourse(null, courseDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void test_http_status_code_500_exception() {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(1L);
        courseDTO.setName("English Course");
        courseDTO.setDescription("Learn English");

        Course course = new Course();
        course.setId(1L);
        course.setName("English Course");
        course.setDescription("Learn English");

        Mockito.when(courseService.updateCourse(1L, course)).thenThrow(new RuntimeException());

        Mockito.when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        Mockito.when(courseMapper.toEntity(courseDTO)).thenReturn(course);

        ResponseEntity<CourseDTO> response = courseController.updateCourse(1L, courseDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void test_createCourse_InternalServerError() {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setName("English Course");
        courseDTO.setDescription("Learn English");

        Course course = new Course();
        course.setName("English Course");
        course.setDescription("Learn English");

        Mockito.when(courseMapper.toEntity(courseDTO)).thenReturn(course);
        Mockito.when(courseService.createCourse(course)).thenThrow(new RuntimeException());

        ResponseEntity<CourseDTO> response = courseController.createCourse(courseDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void test_exception_thrown() {
        Long courseId = 1L;
        Mockito.when(courseService.getCourseById(courseId)).thenThrow(new RuntimeException());

        ResponseEntity<CourseDTO> response = courseController.getCourse(courseId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}


