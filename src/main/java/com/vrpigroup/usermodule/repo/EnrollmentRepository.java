package com.vrpigroup.usermodule.repo;

import com.vrpigroup.usermodule.entity.CourseEntity;
import com.vrpigroup.usermodule.entity.EnrollmentEntity;
import com.vrpigroup.usermodule.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity,Long> {

    List<EnrollmentEntity> findByUserId(Long id);

    Optional<EnrollmentEntity> findByUserAndCourse(UserEntity user, CourseEntity course);
}
