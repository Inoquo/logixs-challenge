package com.logixs.cursos.controller;

import com.logixs.cursos.dto.CursoDTO;
import com.logixs.cursos.mapper.CursoMapper;
import com.logixs.cursos.model.Curso;
import com.logixs.cursos.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    CursoService cursoService;

    @Autowired
    CursoMapper cursoMapper;

    @PostMapping
    public ResponseEntity<CursoDTO> crearCurso(@RequestBody CursoDTO cursoDTO) {
        // Lógica para crear un curso
        Curso nuevoCurso = cursoService.crearCurso(cursoMapper.toEntity(cursoDTO));
        return new ResponseEntity<>(cursoMapper.toDto(nuevoCurso), HttpStatus.CREATED);
    }

    @GetMapping("/{cursoId}")
    public ResponseEntity<CursoDTO> obtenerCurso(@PathVariable Long cursoId) {
        // Lógica para obtener un curso por ID
        Optional<Curso> curso = cursoService.obtenerCursoPorId(cursoId);
        return ResponseEntity.ok(cursoMapper.toDto(curso.get()));
    }

    @PutMapping("/{cursoId}")
    public ResponseEntity<CursoDTO> actualizarCurso(@PathVariable Long cursoId, @RequestBody CursoDTO cursoDTO) {
        // Lógica para actualizar un curso por ID
        Curso cursoActualizado = cursoService.actualizarCurso(cursoId, cursoMapper.toEntity(cursoDTO));
        return ResponseEntity.ok(cursoMapper.toDto(cursoActualizado));
    }

    @DeleteMapping("/{cursoId}")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Long cursoId) {
        // Lógica para eliminar un curso por ID
        cursoService.eliminarCurso(cursoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<CursoDTO>> listarCursos() {
        // Lógica para listar todos los cursos
        List<Curso> cursos = cursoService.listarCursos();
        return ResponseEntity.ok(cursos.parallelStream().map(cursoMapper::toDto).toList());
    }

    @PostMapping("/{cursoId}/inscribir/{estudianteId}")
    public ResponseEntity<String> inscribirEstudiante(@PathVariable Long cursoId, @PathVariable Long estudianteId) {
        //OBTENER ESTUDIANTE

       // cursoService.inscribirEstudianteEnCurso(cursoId, estudianteId);
        return ResponseEntity.ok("Estudiante inscrito en el curso");
    }

    @PostMapping("/{cursoId}/desinscribir/{estudianteId}")
    public ResponseEntity<String> desinscribirEstudiante(@PathVariable Long cursoId, @PathVariable Long estudianteId) {
        //OBTENER ESTUDIANTE

       // cursoService.desinscribirEstudianteDeCurso(cursoId, estudianteId);
        return ResponseEntity.ok("Estudiante desinscrito del curso");
    }

  /*  @PostMapping("/{cursoId}/inscribir/{estudianteId}")
    public ResponseEntity<String> inscribirEstudiante(@PathVariable Long cursoId, @PathVariable Long estudianteId) {
        // Lógica para inscribir al estudiante en este curso
        // Actualizar la entidad Curso
        // Actualizar la entidad Estudiante (en su propio servicio)
        return ResponseEntity.ok("Estudiante inscrito en el curso");
    }

    @PostMapping("/{cursoId}/desinscribir/{estudianteId}")
    public ResponseEntity<String> desinscribirEstudiante(@PathVariable Long cursoId, @PathVariable Long estudianteId) {
        // Lógica para desinscribir al estudiante de este curso
        // Actualizar la entidad Curso
        // Actualizar la entidad Estudiante (en su propio servicio)
        return ResponseEntity.ok("Estudiante desinscrito del curso");
    }*/
}

