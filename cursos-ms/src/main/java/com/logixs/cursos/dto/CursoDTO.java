package com.logixs.cursos.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@Builder
public class CursoDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private Set<Long> estudiantesInscritos;
}
