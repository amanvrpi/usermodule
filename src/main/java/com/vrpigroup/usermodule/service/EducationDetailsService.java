package com.vrpigroup.usermodule.service;

import com.vrpigroup.usermodule.entity.EducationDetails;
import com.vrpigroup.usermodule.entity.UserEntity;
import com.vrpigroup.usermodule.repo.EducationDetailsRepo;
import com.vrpigroup.usermodule.repo.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class EducationDetailsService {

    private final EducationDetailsRepo educationDetailsRepo;
    private final UserRepository userModuleRepository;
    public EducationDetailsService(EducationDetailsRepo educationDetailsRepo, UserRepository userModuleRepository) {
        this.educationDetailsRepo = educationDetailsRepo;
        this.userModuleRepository = userModuleRepository;
    }

    @Transactional
    public String saveEducationDetails(EducationDetails educationDetails, Long userId) {
        if (educationDetails == null || userId == null) {
            return "Invalid input parameters";
        }

        try {
            Optional<UserEntity> optionalUser = userModuleRepository.findById(userId);
            if (optionalUser.isEmpty()) {
                return "User not found with ID: " + userId;
            }

            UserEntity user = optionalUser.get();
            educationDetails.setUser(user);

            educationDetailsRepo.save(educationDetails);

            return "Data saved successfully";
        } catch (DataIntegrityViolationException e) {
            return "Data integrity violation: " + e.getMessage();
        } catch (Exception e) {
            // Log the exception
            log.error("Error while saving education details", e);
            return "Something went wrong while saving data";
        }
    }

}
