package com.vrpigroup.usermodule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollCourseListDto {
    private Long id;
    private String duration;
    private String courseCode;
    private String courseName;
    private Date enrollmentDate;
    private Long amount;

}
