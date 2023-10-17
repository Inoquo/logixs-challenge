package com.logixs.estudiantes.controller;


import com.logixs.estudiantes.dto.EstudianteDTO;
import com.logixs.estudiantes.mapper.EstudianteMapper;
import com.logixs.estudiantes.model.Estudiante;
import com.logixs.estudiantes.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {

    @Autowired
    EstudianteService estudianteService;

    @Autowired
    EstudianteMapper estudianteMapper;

    @PostMapping
    public ResponseEntity<EstudianteDTO> crearEstudiante(@RequestBody EstudianteDTO estudianteDTO) {
        Estudiante nuevoEstudiante = estudianteService.crearEstudiante(estudianteMapper.toEntity(estudianteDTO));
        return new ResponseEntity<>(estudianteMapper.toDto(nuevoEstudiante), HttpStatus.CREATED);
    }

    @GetMapping("/{estudianteId}")
    public ResponseEntity<EstudianteDTO> obtenerEstudiante(@PathVariable Long estudianteId) {
        Optional<Estudiante> estudiante = estudianteService.obtenerEstudiantePorId(estudianteId);
        return ResponseEntity.ok(estudianteMapper.toDto(estudiante.get()));
    }

    @PutMapping("/{estudianteId}")
    public ResponseEntity<EstudianteDTO> actualizarEstudiante(@PathVariable Long estudianteId, @RequestBody EstudianteDTO estudianteDTO) {
        Estudiante estudianteActualizado = estudianteService.actualizarEstudiante(estudianteId, estudianteMapper.toEntity(estudianteDTO));
        return ResponseEntity.ok(estudianteMapper.toDto(estudianteActualizado));
    }

    @DeleteMapping("/{estudianteId}")
    public ResponseEntity<Void> eliminarEstudiante(@PathVariable Long estudianteId) {
        estudianteService.eliminarEstudiante(estudianteId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<EstudianteDTO>> listarEstudiantes() {
        List<Estudiante> estudiantes = estudianteService.listarEstudiantes();
        return ResponseEntity.ok(estudiantes.parallelStream().map(estudianteMapper::toDto).toList());
    }

   /* @PostMapping("/{estudianteId}/inscribir/{cursoId}")
    public ResponseEntity<String> inscribirEstudianteEnCurso(@PathVariable Long estudianteId, @PathVariable Long cursoId) {
        // Realizar una solicitud al microservicio de cursos para inscribir al estudiante en el curso
        restTemplate.postForEntity("http://api-gateway/cursos/" + cursoId + "/inscribir/" + estudianteId, null, String.class);
        // Lógica adicional para inscribir al estudiante en este servicio
        return ResponseEntity.ok("Estudiante inscrito en el curso");
    }

    @PostMapping("/{estudianteId}/desinscribir/{cursoId}")
    public ResponseEntity<String> desinscribirEstudianteDeCurso(@PathVariable Long estudianteId, @PathVariable Long cursoId) {
        // Realizar una solicitud al microservicio de cursos para desinscribir al estudiante del curso
        restTemplate.postForEntity("http://api-gateway/cursos/" + cursoId + "/desinscribir/" + estudianteId, null, String.class);
        // Lógica adicional para desinscribir al estudiante en este servicio
        return ResponseEntity.ok("Estudiante desinscrito del curso");
    }*/
}
