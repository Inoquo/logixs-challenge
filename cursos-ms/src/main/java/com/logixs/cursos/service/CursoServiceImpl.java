package com.logixs.cursos.service;

import com.logixs.cursos.model.Curso;
import com.logixs.cursos.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService{

    @Autowired
    private CursoRepository cursoRepository;

    public Curso crearCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    public Optional<Curso> obtenerCursoPorId(Long cursoId) {
        return cursoRepository.findById(cursoId);
    }

    public Curso actualizarCurso(Long cursoId, Curso curso) {
        if (cursoRepository.existsById(cursoId)) {
            curso.setId(cursoId);
            return cursoRepository.save(curso);
        }
        return null;
    }

    public void eliminarCurso(Long cursoId) {
        cursoRepository.deleteById(cursoId);
    }

    public List<Curso> listarCursos() {
        return cursoRepository.findAll();
    }


    // Método para inscribir un estudiante en un curso
    public Curso inscribirEstudianteEnCurso(Long cursoId, Long estudianteId) {
        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        if (cursoOptional.isPresent()) {
            Curso curso = cursoOptional.get();
            curso.getEstudiantesInscritos().add(estudianteId);
            return cursoRepository.save(curso);
        }
        return null;
    }

    // Método para desinscribir un estudiante de un curso
    public Curso desinscribirEstudianteDeCurso(Long cursoId, Long estudianteId) {
        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        if (cursoOptional.isPresent()) {
            Curso curso = cursoOptional.get();
            curso.getEstudiantesInscritos().remove(estudianteId);
            return cursoRepository.save(curso);
        }
        return null;
    }
/*
    public void inscribirEstudiante(Long cursoId, Long estudianteId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new CursoNotFoundException("Curso no encontrado: " + cursoId));

        Estudiante estudiante = obtenerEstudianteDesdeServicioEstudiantes(estudianteId);

        curso.getInscritos().add(estudiante);
        cursoRepository.save(curso);
    }

    public void desinscribirEstudiante(Long cursoId, Long estudianteId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new CursoNotFoundException("Curso no encontrado: " + cursoId));

        Estudiante estudiante = obtenerEstudianteDesdeServicioEstudiantes(estudianteId);

        curso.getEstudiantesInscritos().remove(estudianteId);
        cursoRepository.save(curso);
    }

    private Estudiante obtenerEstudianteDesdeServicioEstudiantes(Long estudianteId) {
        // Lógica para obtener un estudiante del servicio de Estudiantes
        // Puede ser una llamada a un endpoint del servicio de Estudiantes
    }*/
}
