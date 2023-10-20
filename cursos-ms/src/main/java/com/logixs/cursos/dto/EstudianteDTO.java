package com.logixs.cursos.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class EstudianteDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private Set<Long> cursosInscritos;
}
