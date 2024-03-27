package com.vrpigroup.usermodule.repo;


import com.vrpigroup.usermodule.entity.EducationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EducationDetailsRepo extends JpaRepository<EducationDetails, Long> {
    Optional<EducationDetails> findByUserId(Long userId);
}
