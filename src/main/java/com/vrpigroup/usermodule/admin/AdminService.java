package com.vrpigroup.usermodule.admin;

import com.vrpigroup.usermodule.entity.UserEntity;
import com.vrpigroup.usermodule.repo.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class AdminService {

    private final ContactUsRepo contactUsRepo;
    private final CourseRepository courseRepository;
    private final EducationDetailsRepo educationDetailsRepo;
    private final PaymentDetailsRequestRepo paymentDetailsRequestRepo;
    private final UserRepository userRepository;

    
    private final Logger logger = LoggerFactory.getLogger(AdminService.class);
    public List<UserEntity> getAllUser() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            logger.error("Error while fetching all users", e);
            return Collections.emptyList();
        }
    }

    public UserEntity getUserById(Long id) {
        try {
            return userRepository.findById(id).get();
        } catch (Exception e) {
            logger.error("Error while fetching user by ID: {}", id, e);
            return null;
        }
    }

    public void login(AdminLoginDto adminLoginDto) {

    }
}