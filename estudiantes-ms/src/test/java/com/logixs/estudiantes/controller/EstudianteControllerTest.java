package com.logixs.estudiantes.controller;

import com.logixs.estudiantes.dto.EstudianteDTO;
import com.logixs.estudiantes.mapper.EstudianteMapper;
import com.logixs.estudiantes.model.Estudiante;
import com.logixs.estudiantes.service.EstudianteService;
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
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@WebMvcTest(EstudianteController.class)
public class EstudianteControllerTest {

    @Autowired
    private EstudianteController estudianteController;

    @MockBean
    private EstudianteService estudianteService;

    @MockBean
    private EstudianteMapper estudianteMapper;


    @Test
    public void test_createEstudiante_validData_returns201Created() {
        EstudianteDTO estudianteDTO = new EstudianteDTO();
        estudianteDTO.setNombre("John");
        estudianteDTO.setApellido("Doe");
        estudianteDTO.setFechaNacimiento(LocalDate.now());
        Set<Long> cursosInscritos = new HashSet<>();
        cursosInscritos.add(1L);
        cursosInscritos.add(2L);
        estudianteDTO.setCursosInscritos(cursosInscritos);

        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("John");
        estudiante.setApellido("Doe");
        estudiante.setFechaNacimiento(LocalDate.now());
        estudiante.setCursosInscritos(cursosInscritos);

        when(estudianteMapper.toEntity(estudianteDTO)).thenReturn(estudiante);

        when(estudianteService.crearEstudiante(estudiante)).thenReturn(estudiante);

        when(estudianteMapper.toDTO(estudiante)).thenReturn(estudianteDTO);

        ResponseEntity<EstudianteDTO> response = estudianteController.crearEstudiante(estudianteDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(estudianteDTO.getNombre(), response.getBody().getNombre());
        assertEquals(estudianteDTO.getApellido(), response.getBody().getApellido());
        assertEquals(estudianteDTO.getFechaNacimiento(), response.getBody().getFechaNacimiento());
        assertEquals(estudianteDTO.getCursosInscritos(), response.getBody().getCursosInscritos());
    }

    @Test
    public void test_obtenerEstudiante_existingId_returns200OkWithStudentData() {
        Long estudianteId = 1L;

        EstudianteDTO estudianteDTO = new EstudianteDTO();
        estudianteDTO.setId(estudianteId);
        estudianteDTO.setNombre("John");
        estudianteDTO.setApellido("Doe");
        estudianteDTO.setFechaNacimiento(LocalDate.now());
        Set<Long> cursosInscritos = new HashSet<>();
        cursosInscritos.add(1L);
        cursosInscritos.add(2L);
        estudianteDTO.setCursosInscritos(cursosInscritos);

        Estudiante estudiante = new Estudiante();
        estudiante.setId(estudianteId);
        estudiante.setNombre("John");
        estudiante.setApellido("Doe");
        estudiante.setFechaNacimiento(LocalDate.now());
        estudiante.setCursosInscritos(cursosInscritos);

        when(estudianteService.obtenerEstudiantePorId(estudianteId)).thenReturn(Optional.of(estudiante));

        when(estudianteMapper.toDTO(estudiante)).thenReturn(estudianteDTO);

        ResponseEntity<EstudianteDTO> response = estudianteController.obtenerEstudiante(estudianteId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(estudianteId, response.getBody().getId());
    }

    @Test
    public void test_actualizarEstudiante_existingIdAndValidData_returns200OkWithUpdatedStudentData() {
        Long estudianteId = 1L;
        EstudianteDTO estudianteDTO = new EstudianteDTO();
        estudianteDTO.setId(estudianteId);
        estudianteDTO.setNombre("John");
        estudianteDTO.setApellido("Doe");
        estudianteDTO.setFechaNacimiento(LocalDate.now());
        Set<Long> cursosInscritos = new HashSet<>();
        cursosInscritos.add(1L);
        cursosInscritos.add(2L);
        estudianteDTO.setCursosInscritos(cursosInscritos);

        Estudiante estudiante = new Estudiante();
        estudiante.setId(estudianteId);
        estudiante.setNombre("John");
        estudiante.setApellido("Doe");
        estudiante.setFechaNacimiento(LocalDate.now());
        estudiante.setCursosInscritos(cursosInscritos);

        when(estudianteMapper.toDTO(estudiante)).thenReturn(estudianteDTO);
        when(estudianteMapper.toEntity(estudianteDTO)).thenReturn(estudiante);

        when(estudianteService.actualizarEstudiante(estudianteId, estudiante)).thenReturn(estudiante);

        ResponseEntity<EstudianteDTO> response = estudianteController.actualizarEstudiante(estudianteId, estudianteDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(estudianteId, response.getBody().getId());
        assertEquals(estudianteDTO.getNombre(), response.getBody().getNombre());
        assertEquals(estudianteDTO.getApellido(), response.getBody().getApellido());
        assertEquals(estudianteDTO.getFechaNacimiento(), response.getBody().getFechaNacimiento());
        assertEquals(estudianteDTO.getCursosInscritos(), response.getBody().getCursosInscritos());
    }

    @Test
    public void test_createEstudiante_invalidData_returns400BadRequest() {

        ResponseEntity<EstudianteDTO> response = estudianteController.crearEstudiante(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void test_obtenerEstudiante_nonExistingId_returns404NotFound() {
        Long estudianteId = 100L;

        when(estudianteService.obtenerEstudiantePorId(estudianteId)).thenReturn(Optional.empty());

        ResponseEntity<EstudianteDTO> response = estudianteController.obtenerEstudiante(estudianteId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    public void test_deleteExistingStudent() {
        Long estudianteId = 1L;

        ResponseEntity<Void> response = estudianteController.eliminarEstudiante(estudianteId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void test_returnNoContentAfterDeletingStudent() {
        Long estudianteId = 1L;

        ResponseEntity<Void> response = estudianteController.eliminarEstudiante(estudianteId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void test_attemptToDeleteNonExistingStudent() {
        Long estudianteId = 1L;

        Mockito.doThrow(new RuntimeException()).when(estudianteService).eliminarEstudiante(estudianteId);

        ResponseEntity<Void> response = estudianteController.eliminarEstudiante(estudianteId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void test_attemptToDeleteStudentWithNullId() {


        ResponseEntity<Void> response = estudianteController.eliminarEstudiante(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());


    }

    @Test
    public void test_returns_list_of_estudianteDTO_objects_when_students_exist() {
        List<Estudiante> estudiantes = new ArrayList<>();
        estudiantes.add(new Estudiante(1L, "John", "Doe", LocalDate.now(), new HashSet<>()));
        estudiantes.add(new Estudiante(2L, "Jane", "Smith", LocalDate.now(), new HashSet<>()));
        when(estudianteService.listarEstudiantes()).thenReturn(estudiantes);

        ResponseEntity<List<EstudianteDTO>> response = estudianteController.listarEstudiantes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }


    @Test
    public void test_no_students_in_database() {
        List<Estudiante> estudiantes = new ArrayList<>();
        when(estudianteService.listarEstudiantes()).thenReturn(estudiantes);

        ResponseEntity<List<EstudianteDTO>> response = estudianteController.listarEstudiantes();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void test_internal_server_error_response() {
        when(estudianteService.listarEstudiantes()).thenThrow(new RuntimeException());

        ResponseEntity<List<EstudianteDTO>> response = estudianteController.listarEstudiantes();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


    @Test
    public void test_returns_badRequest_when_estudianteDTO_is_null() {
        EstudianteDTO estudianteDTO = null;

        ResponseEntity<EstudianteDTO> response = estudianteController.crearEstudiante(estudianteDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void test_listarEstudiantes() {
        List<Estudiante> estudiantes = new ArrayList<>();
        estudiantes.add(new Estudiante(1L, "John", "Doe", LocalDate.now(), new HashSet<>()));
        estudiantes.add(new Estudiante(2L, "Jane", "Smith", LocalDate.now(), new HashSet<>()));
        when(estudianteService.listarEstudiantes()).thenReturn(estudiantes);

        ResponseEntity<List<EstudianteDTO>> response = estudianteController.listarEstudiantes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(estudiantes.size(), response.getBody().size());
    }

    @Test
    public void test_listarEstudiantes_returnsEstudianteDTOListInSameOrder() {
        List<Estudiante> estudiantes = new ArrayList<>();
        Estudiante estudiante1 = new Estudiante(1L, "John", "Doe", LocalDate.now(), new HashSet<>());
        Estudiante estudiante2 = new Estudiante(2L, "Jane", "Smith", LocalDate.now(), new HashSet<>());
        estudiantes.add(estudiante1);
        estudiantes.add(estudiante2);

        when(estudianteService.listarEstudiantes()).thenReturn(estudiantes);

        EstudianteDTO estudianteDTO1 = new EstudianteDTO(1L, "John", "Doe", LocalDate.now(), new HashSet<>());
        EstudianteDTO estudianteDTO2 = new EstudianteDTO(2L, "Jane", "Smith", LocalDate.now(), new HashSet<>());

        when(estudianteMapper.toDTO(estudiante1)).thenReturn(estudianteDTO1);
        when(estudianteMapper.toDTO(estudiante2)).thenReturn(estudianteDTO2);

        ResponseEntity<List<EstudianteDTO>> response = estudianteController.listarEstudiantes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<EstudianteDTO> estudianteDTOs = response.getBody();
        assertNotNull(estudianteDTOs);
        assertEquals(2, estudianteDTOs.size());
        assertEquals(estudianteDTO1, estudianteDTOs.get(0));
        assertEquals(estudianteDTO2, estudianteDTOs.get(1));
    }

    @Test
    public void test_noContentResponseWhenListIsNull() {
        when(estudianteService.listarEstudiantes()).thenReturn(List.of());

        ResponseEntity<List<EstudianteDTO>> response = estudianteController.listarEstudiantes();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void test_noContentResponseWhenListIsEmpty() {
        List<Estudiante> emptyList = new ArrayList<>();
        when(estudianteService.listarEstudiantes()).thenReturn(emptyList);

        ResponseEntity<List<EstudianteDTO>> response = estudianteController.listarEstudiantes();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void test_listarEstudiantes_returnsListOfEstudianteDTO() {
        List<Estudiante> estudiantes = new ArrayList<>();
        Estudiante estudiante1 = new Estudiante(1L, "John", "Doe", LocalDate.now(), new HashSet<>());
        Estudiante estudiante2 = new Estudiante(2L, "Jane", "Smith", LocalDate.now(), new HashSet<>());
        estudiantes.add(estudiante1);
        estudiantes.add(estudiante2);

        when(estudianteService.listarEstudiantes()).thenReturn(estudiantes);

        EstudianteDTO estudianteDTO1 = new EstudianteDTO(1L, "John", "Doe", LocalDate.now(), new HashSet<>());
        EstudianteDTO estudianteDTO2 = new EstudianteDTO(2L, "Jane", "Smith", LocalDate.now(), new HashSet<>());
        List<EstudianteDTO> expected = Arrays.asList(estudianteDTO1, estudianteDTO2);

        when(estudianteMapper.toDTO(estudiante1)).thenReturn(estudianteDTO1);
        when(estudianteMapper.toDTO(estudiante2)).thenReturn(estudianteDTO2);

        ResponseEntity<List<EstudianteDTO>> response = estudianteController.listarEstudiantes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }
}



