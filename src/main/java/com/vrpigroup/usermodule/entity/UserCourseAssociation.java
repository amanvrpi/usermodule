package com.vrpigroup.usermodule.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public  record UserCourseAssociation(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id,
        Long userId, Long courseId){
    public UserCourseAssociation(Long userId, Long courseId) {
        this(null, userId, courseId);
    }
}