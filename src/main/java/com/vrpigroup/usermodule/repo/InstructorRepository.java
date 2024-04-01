package com.vrpigroup.usermodule.repo;

import com.vrpigroup.usermodule.entity.InstructorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<InstructorEntity, Long> {

}
