package com.logixs.estudiantes.service;

import com.logixs.estudiantes.model.Estudiante;
import com.logixs.estudiantes.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstudianteServiceImpl implements EstudianteService{

    @Autowired
    private EstudianteRepository estudianteRepository;

    public Estudiante crearEstudiante(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);

    }

    public Optional<Estudiante> obtenerEstudiantePorId(Long estudianteId) {
        return estudianteRepository.findById(estudianteId);
    }

    public Estudiante actualizarEstudiante(Long estudianteId, Estudiante estudiante) {
        if (estudianteRepository.existsById(estudianteId)) {
            estudiante.setId(estudianteId);
            return estudianteRepository.save(estudiante);
        }
        return null;
    }

    public void eliminarEstudiante(Long estudianteId) {
        estudianteRepository.deleteById(estudianteId);
    }

    public List<Estudiante> listarEstudiantes() {
        return estudianteRepository.findAll();
    }

/*
    public void inscribirEstudianteEnEstudiante(Long estudianteId, Long estudianteId) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new EstudianteNotFoundException("Estudiante no encontrado: " + estudianteId));

        Estudiante estudiante = obtenerEstudianteDesdeServicioEstudiantes(estudianteId);

        estudiante.getEstudiantesInscritos().add(estudiante);
        estudianteRepository.save(estudiante);
    }

    public void desinscribirEstudianteDeEstudiante(Long estudianteId, Long estudianteId) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new EstudianteNotFoundException("Estudiante no encontrado: " + estudianteId));

        Estudiante estudiante = obtenerEstudianteDesdeServicioEstudiantes(estudianteId);

        estudiante.getEstudiantesInscritos().remove(estudiante);
        estudianteRepository.save(estudiante);
    }

    private Estudiante obtenerEstudianteDesdeServicioEstudiantes(Long estudianteId) {
        // Lógica para obtener un estudiante del servicio de Estudiantes
        // Puede ser una llamada a un endpoint del servicio de Estudiantes
    }*/
}
