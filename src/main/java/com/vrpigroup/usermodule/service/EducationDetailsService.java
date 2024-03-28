package com.vrpigroup.usermodule.service;

import com.vrpigroup.usermodule.entity.EducationDetails;
import com.vrpigroup.usermodule.entity.UserEntity;
import com.vrpigroup.usermodule.repo.EducationDetailsRepo;
import com.vrpigroup.usermodule.repo.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class EducationDetailsService {

    private final EducationDetailsRepo educationDetailsRepo;
    private final UserRepository userModuleRepository;
    public EducationDetailsService(EducationDetailsRepo educationDetailsRepo, UserRepository userModuleRepository) {
        this.educationDetailsRepo = educationDetailsRepo;
        this.userModuleRepository = userModuleRepository;
    }

    @Transactional
    public String saveEducationDetails(EducationDetails educationDetails, Long userId) {
        try {
            // Retrieve the user entity
            UserEntity user = userModuleRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
            educationDetails.setUserId(userId);
            educationDetailsRepo.save(educationDetails);
            return "Data saved successfully";
        } catch (EntityNotFoundException e) {
            return "User not found with ID: " + userId;
        } catch (DataIntegrityViolationException e) {
            return "Data integrity violation: " + e.getMessage();
        } catch (Exception e) {
            return "Something went wrong while saving data: " + e.getMessage();
        }
    }

    public EducationDetails getEducationDetails(Long userId) {
        return educationDetailsRepo.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Education details not found for user ID: " + userId));
    }
}
