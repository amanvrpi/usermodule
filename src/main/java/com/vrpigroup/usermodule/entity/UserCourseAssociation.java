package com.vrpigroup.usermodule.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public  class UserCourseAssociation{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        Long userId;
        Long courseId;
    public UserCourseAssociation(Long userId, Long courseId) {
        this(null, userId, courseId);
    }

}