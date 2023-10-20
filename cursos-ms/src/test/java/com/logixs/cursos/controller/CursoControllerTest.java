package com.logixs.cursos.controller;

import com.logixs.cursos.dto.CursoDTO;
import com.logixs.cursos.mapper.CursoMapper;
import com.logixs.cursos.model.Curso;
import com.logixs.cursos.restClient.EstudianteRestClient;
import com.logixs.cursos.service.CursoService;
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

@WebMvcTest(CursoController.class)
public class CursoControllerTest {

    @Autowired
    private CursoController cursoController;

    @MockBean
    private CursoService cursoService;

    @MockBean
    private CursoMapper cursoMapper;

    @MockBean
    private EstudianteRestClient estudianteRestClient;

    @Test
    public void test_createCourseWithValidData() {

        CursoDTO cursoDTO = new CursoDTO();
        cursoDTO.setNombre("English Course");
        cursoDTO.setDescripcion("Learn English");

        Curso curso = new Curso();
        curso.setNombre("English Course");
        curso.setDescripcion("Learn English");

        Mockito.when(cursoMapper.toEntity(cursoDTO)).thenReturn(curso);

        Mockito.when(cursoService.crearCurso(curso)).thenReturn(curso);

        Mockito.when(cursoMapper.toDTO(curso)).thenReturn(cursoDTO);

        ResponseEntity<CursoDTO> response = cursoController.crearCurso(cursoDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertEquals(cursoDTO, response.getBody());
    }

    @Test
    public void test_retrieveExistingCourseById() {

        CursoDTO cursoDTO = new CursoDTO();
        cursoDTO.setId(1L);
        cursoDTO.setNombre("English Course");
        cursoDTO.setDescripcion("Learn English");

        Curso curso = new Curso();
        curso.setId(1L);
        curso.setNombre("English Course");
        curso.setDescripcion("Learn English");

        Mockito.when(cursoService.obtenerCursoPorId(1L)).thenReturn(Optional.of(curso));

        Mockito.when(cursoMapper.toDTO(curso)).thenReturn(cursoDTO);

        ResponseEntity<CursoDTO> response = cursoController.obtenerCurso(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(cursoDTO, response.getBody());
    }

    @Test
    public void test_updateExistingCourseWithValidData() {

        CursoDTO cursoDTO = new CursoDTO();
        cursoDTO.setId(1L);
        cursoDTO.setNombre("English Course");
        cursoDTO.setDescripcion("Learn English");

        Curso curso = new Curso();
        curso.setId(1L);
        curso.setNombre("English Course");
        curso.setDescripcion("Learn English");

        Mockito.when(cursoService.actualizarCurso(1L, curso)).thenReturn(curso);

        Mockito.when(cursoMapper.toDTO(curso)).thenReturn(cursoDTO);

        Mockito.when(cursoMapper.toEntity(cursoDTO)).thenReturn(curso);

        ResponseEntity<CursoDTO> response = cursoController.actualizarCurso(1L, cursoDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(cursoDTO, response.getBody());
    }

    @Test
    public void test_createCourseWithInvalidData() {

        ResponseEntity<CursoDTO> response = cursoController.crearCurso(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void test_retrieveNonExistentCourseById() {

        Mockito.when(cursoService.obtenerCursoPorId(1L)).thenReturn(Optional.empty());

        ResponseEntity<CursoDTO> response = cursoController.obtenerCurso(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    public void test_deleteCourse_validCourseId() {
        Long cursoId = 1L;

        ResponseEntity<Void> response = cursoController.eliminarCurso(cursoId);

        Mockito.verify(cursoService).eliminarCurso(cursoId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void test_deleteCourse_noContentResponse() {
        Long cursoId = 1L;

        ResponseEntity<Void> response = cursoController.eliminarCurso(cursoId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void test_deleteCourse_nullCourseId() {
        Long cursoId = null;
        CursoService cursoServiceMock = Mockito.mock(CursoService.class);
        CursoController cursoController = new CursoController(cursoServiceMock, null, null);

        ResponseEntity<Void> response = cursoController.eliminarCurso(cursoId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void test_deleteCourse_internalServerError() {
        Long cursoId = 1L;
        Mockito.doThrow(new RuntimeException()).when(cursoService).eliminarCurso(cursoId);

        ResponseEntity<Void> response = cursoController.eliminarCurso(cursoId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void test_deleteCourse_callsEliminarCurso() {
        Long cursoId = 1L;

        ResponseEntity<Void> response = cursoController.eliminarCurso(cursoId);

        Mockito.verify(cursoService).eliminarCurso(cursoId);
    }

    @Test
    public void test_return_list_of_cursoDTO_objects_when_cursos_exist() {
        List<Curso> cursos = new ArrayList<>();
        Curso curso1 = new Curso();
        curso1.setId(1L);
        curso1.setNombre("Curso 1");
        curso1.setDescripcion("Descripción del Curso 1");
        curso1.setFechaInicio(LocalDate.now());
        curso1.setFechaFin(LocalDate.now());
        cursos.add(curso1);

        Curso curso2 = new Curso();
        curso2.setId(2L);
        curso2.setNombre("Curso 2");
        curso2.setDescripcion("Descripción del Curso 2");
        curso2.setFechaInicio(LocalDate.now());
        curso2.setFechaFin(LocalDate.now());
        cursos.add(curso2);

        CursoDTO cursoDTO1 = new CursoDTO();
        cursoDTO1.setId(1L);
        cursoDTO1.setNombre("Curso 1");
        cursoDTO1.setDescripcion("Descripción del Curso 1");
        cursoDTO1.setFechaInicio(LocalDate.now());
        cursoDTO1.setFechaFin(LocalDate.now());

        CursoDTO cursoDTO2 = new CursoDTO();
        cursoDTO2.setId(2L);
        cursoDTO2.setNombre("Curso 2");
        cursoDTO2.setDescripcion("Descripción del Curso 2");
        cursoDTO2.setFechaInicio(LocalDate.now());
        cursoDTO2.setFechaFin(LocalDate.now());

        Mockito.when(cursoService.listarCursos()).thenReturn(cursos);
        Mockito.when(cursoMapper.toDTO(curso1)).thenReturn(cursoDTO1);
        Mockito.when(cursoMapper.toDTO(curso2)).thenReturn(cursoDTO2);


        ResponseEntity<List<CursoDTO>> response = cursoController.listarCursos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(cursoDTO1.getId(), response.getBody().get(0).getId());
        assertEquals(cursoDTO1.getNombre(), response.getBody().get(0).getNombre());
        assertEquals(cursoDTO1.getDescripcion(), response.getBody().get(0).getDescripcion());
        assertEquals(cursoDTO1.getFechaInicio(), response.getBody().get(0).getFechaInicio());
        assertEquals(cursoDTO1.getFechaFin(), response.getBody().get(0).getFechaFin());
        assertEquals(cursoDTO2.getId(), response.getBody().get(1).getId());
        assertEquals(cursoDTO2.getNombre(), response.getBody().get(1).getNombre());
        assertEquals(cursoDTO2.getDescripcion(), response.getBody().get(1).getDescripcion());
        assertEquals(cursoDTO2.getFechaInicio(), response.getBody().get(1).getFechaInicio());
        assertEquals(cursoDTO2.getFechaFin(), response.getBody().get(1).getFechaFin());
    }

    @Test
    public void test_return_no_content_response_when_no_cursos_exist() {
        List<Curso> cursos = new ArrayList<>();
        Mockito.when(cursoService.listarCursos()).thenReturn(cursos);

        ResponseEntity<List<CursoDTO>> response = cursoController.listarCursos();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void test_return_response_with_HTTP_status_code_200_when_cursos_exist() {
        List<Curso> cursos = new ArrayList<>();
        Curso curso1 = new Curso();
        curso1.setId(1L);
        curso1.setNombre("Curso 1");
        curso1.setDescripcion("Descripción del Curso 1");
        curso1.setFechaInicio(LocalDate.now());
        curso1.setFechaFin(LocalDate.now());
        cursos.add(curso1);

        Mockito.when(cursoService.listarCursos()).thenReturn(cursos);

        ResponseEntity<List<CursoDTO>> response = cursoController.listarCursos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void test_handle_and_return_internal_server_error_response_when_exception_occurs() {
        Mockito.when(cursoService.listarCursos()).thenThrow(new RuntimeException());

        ResponseEntity<List<CursoDTO>> response = cursoController.listarCursos();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void test_http_status_code_400_null_curso_id() {
        CursoDTO cursoDTO = new CursoDTO();
        cursoDTO.setId(1L);
        cursoDTO.setNombre("English Course");
        cursoDTO.setDescripcion("Learn English");

        Curso curso = new Curso();
        curso.setId(1L);
        curso.setNombre("English Course");
        curso.setDescripcion("Learn English");

        Mockito.when(cursoService.actualizarCurso(1L, curso)).thenReturn(curso);

        Mockito.when(cursoMapper.toDTO(curso)).thenReturn(cursoDTO);

        Mockito.when(cursoMapper.toEntity(cursoDTO)).thenReturn(curso);

        ResponseEntity<CursoDTO> response = cursoController.actualizarCurso(null, cursoDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void test_http_status_code_500_exception() {
        CursoDTO cursoDTO = new CursoDTO();
        cursoDTO.setId(1L);
        cursoDTO.setNombre("English Course");
        cursoDTO.setDescripcion("Learn English");

        Curso curso = new Curso();
        curso.setId(1L);
        curso.setNombre("English Course");
        curso.setDescripcion("Learn English");

        Mockito.when(cursoService.actualizarCurso(1L, curso)).thenThrow(new RuntimeException());

        Mockito.when(cursoMapper.toDTO(curso)).thenReturn(cursoDTO);

        Mockito.when(cursoMapper.toEntity(cursoDTO)).thenReturn(curso);

        ResponseEntity<CursoDTO> response = cursoController.actualizarCurso(1L, cursoDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void test_crearCurso_InternalServerError() {
        CursoDTO cursoDTO = new CursoDTO();
        cursoDTO.setNombre("English Course");
        cursoDTO.setDescripcion("Learn English");

        Curso curso = new Curso();
        curso.setNombre("English Course");
        curso.setDescripcion("Learn English");

        Mockito.when(cursoMapper.toEntity(cursoDTO)).thenReturn(curso);
        Mockito.when(cursoService.crearCurso(curso)).thenThrow(new RuntimeException());

        ResponseEntity<CursoDTO> response = cursoController.crearCurso(cursoDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void test_exception_thrown() {
        Long cursoId = 1L;
        Mockito.when(cursoService.obtenerCursoPorId(cursoId)).thenThrow(new RuntimeException());

        ResponseEntity<CursoDTO> response = cursoController.obtenerCurso(cursoId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}


