package com.logixs.estudiantes.service;

import com.logixs.estudiantes.model.Estudiante;

import java.util.List;
import java.util.Optional;

public interface EstudianteService {

    Estudiante crearEstudiante(Estudiante estudiante);

    Optional<Estudiante> obtenerEstudiantePorId(Long estudianteId);

    Estudiante actualizarEstudiante(Long estudianteId, Estudiante estudiante);

    void eliminarEstudiante(Long estudianteId);

    List<Estudiante> listarEstudiantes();
}
