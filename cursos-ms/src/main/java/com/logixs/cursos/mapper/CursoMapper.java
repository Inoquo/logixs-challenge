package com.logixs.cursos.mapper;

import com.logixs.cursos.dto.CursoDTO;
import com.logixs.cursos.model.Curso;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CursoMapper {

    CursoDTO toDto(Curso curso);

    Curso toEntity(CursoDTO cursoDTO);
}
