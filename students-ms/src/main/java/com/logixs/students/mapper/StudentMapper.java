package com.logixs.students.mapper;

import com.logixs.students.dto.StudentDTO;
import com.logixs.students.model.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentDTO toDTO(Student student);

    Student toEntity(StudentDTO studentDTO);
}
