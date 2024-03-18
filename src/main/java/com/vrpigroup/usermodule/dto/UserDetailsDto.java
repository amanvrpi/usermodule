package com.vrpigroup.usermodule.dto;

import com.vrpigroup.usermodule.entity.CourseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDetailsDto {

    //    user entity data
    private UserDto userDto;

    //    courses data from course entity
    List<EnrollCourseListDto> courseList;

    //    getting data from eduation Entity
    private EducationDetailsDto educationDetails;

    private String statusMessage;


}