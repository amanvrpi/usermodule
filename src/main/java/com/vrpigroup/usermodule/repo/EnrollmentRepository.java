package com.vrpigroup.usermodule.repo;

import com.vrpigroup.usermodule.entity.EnrollmentEntity;
import com.vrpigroup.usermodule.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity,Long> {
    List<EnrollmentEntity> findByUser(UserEntity id);

    List<EnrollmentEntity> findByUserId(Long id);

    Optional<EnrollmentEntity> findByUserIdAndCourseId(Long userId, Long courseId);
}
