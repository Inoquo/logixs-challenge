package com.logixs.estudiantes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EstudianteDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private Set<Long> cursosInscritos;
}
