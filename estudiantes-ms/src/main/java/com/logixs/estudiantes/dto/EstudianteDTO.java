package com.logixs.estudiantes.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@Builder
public class EstudianteDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private Date fechaNacimiento;
    private Set<Long> cursosInscritos;
}
