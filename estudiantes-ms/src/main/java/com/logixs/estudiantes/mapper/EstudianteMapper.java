package com.logixs.estudiantes.mapper;

import com.logixs.estudiantes.dto.EstudianteDTO;
import com.logixs.estudiantes.model.Estudiante;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EstudianteMapper {

    EstudianteDTO toDTO(Estudiante estudiante);

    Estudiante toEntity(EstudianteDTO estudianteDTO);
}
