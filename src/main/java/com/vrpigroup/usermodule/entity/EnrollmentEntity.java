package com.vrpigroup.usermodule.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class EnrollmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private CourseEntity course;

    @Temporal(TemporalType.DATE)
    private Date enrollmentDate;
}