package com.logixs.cursos.service;

import com.logixs.cursos.model.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {

    Curso crearCurso(Curso curso);

    Optional<Curso> obtenerCursoPorId(Long cursoId);

    Curso actualizarCurso(Long cursoId, Curso curso);

    void eliminarCurso(Long cursoId);

    List<Curso> listarCursos();

}
