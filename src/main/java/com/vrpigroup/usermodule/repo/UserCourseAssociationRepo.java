package com.vrpigroup.usermodule.repo;

import com.vrpigroup.usermodule.entity.UserCourseAssociation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface UserCourseAssociationRepo extends JpaRepository<UserCourseAssociation,Long> {
}