package com.logixs.courses.mapper;

import com.logixs.courses.dto.CourseDTO;
import com.logixs.courses.model.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseDTO toDTO(Course course);

    Course toEntity(CourseDTO courseDTO);
}
