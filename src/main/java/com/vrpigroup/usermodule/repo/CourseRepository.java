package com.vrpigroup.usermodule.repo;

import com.vrpigroup.usermodule.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseEntity,Long> {
}
