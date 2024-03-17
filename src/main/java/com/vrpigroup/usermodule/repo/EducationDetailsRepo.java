package com.vrpigroup.usermodule.repo;


import com.vrpigroup.usermodule.entity.EducationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationDetailsRepo extends JpaRepository<EducationDetails, Long> {
}
