package com.vrpigroup.usermodule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollCourseListDto {
    private Long id;
    private String couseId;
    private String courseName;

}
