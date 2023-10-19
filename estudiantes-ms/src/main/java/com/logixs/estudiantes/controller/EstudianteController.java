package com.logixs.estudiantes.controller;


import com.logixs.estudiantes.dto.EstudianteDTO;
import com.logixs.estudiantes.mapper.EstudianteMapper;
import com.logixs.estudiantes.model.Estudiante;
import com.logixs.estudiantes.service.EstudianteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    private final EstudianteMapper estudianteMapper;

    public EstudianteController(EstudianteService estudianteService, EstudianteMapper estudianteMapper) {
        this.estudianteService = estudianteService;
        this.estudianteMapper = estudianteMapper;
    }

    @PostMapping
    public ResponseEntity<EstudianteDTO> crearEstudiante(@RequestBody EstudianteDTO estudianteDTO) {
        try {
            if (Optional.ofNullable(estudianteDTO).isEmpty()) return ResponseEntity.badRequest().build();
            Estudiante nuevoEstudiante = estudianteService.crearEstudiante(estudianteMapper.toEntity(estudianteDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(estudianteMapper.toDto(nuevoEstudiante));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{estudianteId}")
    public ResponseEntity<EstudianteDTO> obtenerEstudiante(@PathVariable Long estudianteId) {
        try {
            if (Optional.ofNullable(estudianteId).isEmpty()) return ResponseEntity.badRequest().build();
            Optional<Estudiante> curso = estudianteService.obtenerEstudiantePorId(estudianteId);
            return curso.map(value -> ResponseEntity.ok(estudianteMapper.toDto(value))).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{estudianteId}")
    public ResponseEntity<EstudianteDTO> actualizarEstudiante(@PathVariable Long estudianteId, @RequestBody EstudianteDTO estudianteDTO) {
        try {
            if (Optional.ofNullable(estudianteId).isEmpty() || Optional.ofNullable(estudianteDTO).isEmpty())
                return ResponseEntity.badRequest().build();
            Estudiante estudianteActualizado = estudianteService.actualizarEstudiante(estudianteId, estudianteMapper.toEntity(estudianteDTO));
            return ResponseEntity.ok(estudianteMapper.toDto(estudianteActualizado));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{estudianteId}")
    public ResponseEntity<Void> eliminarEstudiante(@PathVariable Long estudianteId) {
        try {
            estudianteService.eliminarEstudiante(estudianteId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<EstudianteDTO>> listarEstudiantes() {
        try {
            List<Estudiante> estudiantes = estudianteService.listarEstudiantes();
            if (estudiantes.isEmpty()) return ResponseEntity.noContent().build();
            return ResponseEntity.ok(estudiantes.parallelStream().map(estudianteMapper::toDto).toList());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
