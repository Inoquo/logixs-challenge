package com.logixs.cursos.controller;

import com.logixs.cursos.dto.CursoDTO;
import com.logixs.cursos.dto.EstudianteDTO;
import com.logixs.cursos.mapper.CursoMapper;
import com.logixs.cursos.model.Curso;
import com.logixs.cursos.restClient.EstudianteRestClient;
import com.logixs.cursos.service.CursoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;

    private final CursoMapper cursoMapper;

    private final EstudianteRestClient estudianteRestClient;

    public CursoController(CursoService cursoService, CursoMapper cursoMapper, EstudianteRestClient estudianteRestClient) {
        this.cursoService = cursoService;
        this.cursoMapper = cursoMapper;
        this.estudianteRestClient = estudianteRestClient;
    }

    @PostMapping
    public ResponseEntity<CursoDTO> crearCurso(@RequestBody CursoDTO cursoDTO) {
        try {
            if (Optional.ofNullable(cursoDTO).isEmpty()) return ResponseEntity.badRequest().build();
            Curso nuevoCurso = cursoService.crearCurso(cursoMapper.toEntity(cursoDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(cursoMapper.toDto(nuevoCurso));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{cursoId}")
    public ResponseEntity<CursoDTO> obtenerCurso(@PathVariable Long cursoId) {
        try {
            if (Optional.ofNullable(cursoId).isEmpty()) return ResponseEntity.badRequest().build();
            Optional<Curso> curso = cursoService.obtenerCursoPorId(cursoId);
            return curso.map(value -> ResponseEntity.ok(cursoMapper.toDto(value))).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{cursoId}")
    public ResponseEntity<CursoDTO> actualizarCurso(@PathVariable Long cursoId, @RequestBody CursoDTO cursoDTO) {
        try {
            if (Optional.ofNullable(cursoId).isEmpty() || Optional.ofNullable(cursoDTO).isEmpty())
                return ResponseEntity.badRequest().build();
            Curso cursoActualizado = cursoService.actualizarCurso(cursoId, cursoMapper.toEntity(cursoDTO));
            return ResponseEntity.ok(cursoMapper.toDto(cursoActualizado));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{cursoId}")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Long cursoId) {
        try {
            if (Optional.ofNullable(cursoId).isEmpty()) return ResponseEntity.badRequest().build();
            cursoService.eliminarCurso(cursoId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CursoDTO>> listarCursos() {
        try {
            List<Curso> cursos = cursoService.listarCursos();
            if (cursos.isEmpty()) return ResponseEntity.noContent().build();
            return ResponseEntity.ok(cursos.parallelStream().map(cursoMapper::toDto).toList());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/{cursoId}/inscribir/{estudianteId}")
    public ResponseEntity<String> inscribirEstudiante(@PathVariable Long cursoId, @PathVariable Long estudianteId) {
        try {
            if (Optional.ofNullable(cursoId).isEmpty() || Optional.ofNullable(estudianteId).isEmpty())
                return ResponseEntity.badRequest().build();
            EstudianteDTO estudiante = estudianteRestClient.obtenerEstudiante(estudianteId);
            estudiante.getCursosInscritos().add(cursoId);
            estudianteRestClient.actualizarEstudiante(estudiante.getId(), estudiante);
            cursoService.inscribirEstudianteEnCurso(cursoId, estudianteId);
            return ResponseEntity.ok("Estudiante inscrito en el curso");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/{cursoId}/desinscribir/{estudianteId}")
    public ResponseEntity<String> desinscribirEstudiante(@PathVariable Long cursoId, @PathVariable Long estudianteId) {
        try {
            if (Optional.ofNullable(cursoId).isEmpty() || Optional.ofNullable(estudianteId).isEmpty())
                return ResponseEntity.badRequest().build();
            EstudianteDTO estudiante = estudianteRestClient.obtenerEstudiante(estudianteId);
            estudiante.getCursosInscritos().remove(cursoId);
            estudianteRestClient.actualizarEstudiante(estudiante.getId(), estudiante);
            cursoService.desinscribirEstudianteDeCurso(cursoId, estudianteId);
            return ResponseEntity.ok("Estudiante desinscrito del curso");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

