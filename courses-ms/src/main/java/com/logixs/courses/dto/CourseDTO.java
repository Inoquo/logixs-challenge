package com.logixs.courses.dto;

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
public class CourseDTO {

    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Set<Long> enrolledStudents;
}
