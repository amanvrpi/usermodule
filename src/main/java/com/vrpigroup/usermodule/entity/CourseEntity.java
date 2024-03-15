package com.vrpigroup.usermodule.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Courses")
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Course_ID")
    private Long id;

    @Schema(name = "Course Name", description = "Name of the Course", example = "Java Programming", required = true)
    private String courseName;

    @Schema(name = "Description", description = "Description of the Course", example = "Learn Java programming from scratch", required = true)
    private String description;

    @Schema(name = "Instructor", description = "Name of the Instructor", example = "John Doe", required = true)
    private String instructor;

    @Schema(name = "Duration", description = "Duration of the Course", example = "3 months", required = true)
    private String duration;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<EnrollmentEntity> enrollments = new ArrayList<>();

}
