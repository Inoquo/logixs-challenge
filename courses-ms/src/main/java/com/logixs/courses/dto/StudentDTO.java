package com.logixs.courses.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class StudentDTO {

    private Long id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private Set<Long> enrolledCourses;
}
