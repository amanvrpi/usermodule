package com.vrpigroup.usermodule.admin;

import com.vrpigroup.usermodule.entity.InstructorEntity;
import com.vrpigroup.usermodule.entity.Roles;
import com.vrpigroup.usermodule.entity.UserEntity;
import com.vrpigroup.usermodule.repo.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class AdminService {

    private final InstructorRepository instructorRepository;
    private final ContactUsRepo contactUsRepo;
    private final CourseRepository courseRepository;
    private final EducationDetailsRepo educationDetailsRepo;
    private final PaymentDetailsRequestRepo paymentDetailsRequestRepo;
    private final UserRepository userRepository;

    
    private final Logger logger = LoggerFactory.getLogger(AdminService.class);
    public List<Map<String, Object>> getAllUsers() {
        try {
            return userRepository.findAll().stream()
                    .map(userEntity -> {
                        Map<String, Object> userMap = new HashMap<>();
                        userMap.put("id", userEntity.getId());
                        userMap.put("firstName", userEntity.getFirstName());
                        userMap.put("lastName", userEntity.getLastName());
                        userMap.put("fathersName", userEntity.getFathersName());
                        userMap.put("gender", userEntity.getGender());
                        userMap.put("dateOfBirth", userEntity.getDateOfBirth());
                        userMap.put("phoneNumber", userEntity.getPhoneNumber());
                        userMap.put("address", userEntity.getAddress());
                        userMap.put("email", userEntity.getEmail());
                        userMap.put("occupation", userEntity.getOccupation());
                        userMap.put("aadharCardNumber", userEntity.getAadharCardNumber());
                        userMap.put("roles", userEntity.getRoles());
                        return userMap;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error while fetching all users", e);
            // You can handle the exception here if needed
            return Collections.emptyList();
        }
    }

    public InstructorEntity getInstructorById(Long id) {
        try {
            Optional<InstructorEntity> instructorEntity = instructorRepository.findById(id);
            return instructorEntity.orElse(null);
        } catch (Exception e) {
            logger.error("Error while fetching instructor by ID: {}", id, e);
            return null;
        }
    }

    public String saveInstructor(InstructorEntity instructorEntity) {
        try {
            instructorRepository.save(instructorEntity);
            return "Instructor saved successfully";
        } catch (Exception e) {
            logger.error("Error while saving instructor", e);
            return "Error while saving instructor";
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




    public List<InstructorEntity> getAllInstructor() {
        try {
            return instructorRepository.findAll();
        } catch (Exception e) {
            logger.error("Error while fetching all instructors", e);
            return Collections.emptyList();
        }
    }

    public ResponseEntity<InstructorEntity> updateInstructor(InstructorEntity instructorEntity, Long id) {
        try {
            Optional<InstructorEntity> optionalInstructorEntity = instructorRepository.findById(id);
            if (optionalInstructorEntity.isPresent()) {
                InstructorEntity updatedInstructorEntity = optionalInstructorEntity.get();
                updatedInstructorEntity.setFirstName(instructorEntity.getFirstName());
                updatedInstructorEntity.setLastName(instructorEntity.getLastName());
                updatedInstructorEntity.setPhoneNumber(instructorEntity.getPhoneNumber());
                updatedInstructorEntity.setAddress(instructorEntity.getAddress());
                updatedInstructorEntity.setEmail(instructorEntity.getEmail());
                instructorRepository.save(updatedInstructorEntity);
                return new ResponseEntity<>(updatedInstructorEntity, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error while updating instructor by ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}