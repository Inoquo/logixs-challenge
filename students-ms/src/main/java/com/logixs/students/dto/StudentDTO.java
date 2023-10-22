package com.logixs.students.dto;

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
public class StudentDTO {

    private Long id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private Set<Long> enrolledCourses;
}
