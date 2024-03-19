package com.vrpigroup.usermodule.service;
import com.vrpigroup.usermodule.annotations.email.EmailValidation;
import com.vrpigroup.usermodule.annotations.email.EmailValidationServiceImpl;
import com.vrpigroup.usermodule.constants.UserConstants;
import com.vrpigroup.usermodule.dto.*;
import com.vrpigroup.usermodule.entity.*;
import com.vrpigroup.usermodule.exception.UserAlreadyExistException;
import com.vrpigroup.usermodule.mapper.UserMapper;
import com.vrpigroup.usermodule.repo.ContactUsRepo;
import com.vrpigroup.usermodule.repo.EducationDetailsRepo;
import com.vrpigroup.usermodule.repo.EnrollmentRepository;
import com.vrpigroup.usermodule.repo.UserRepository;
//import com.vrpigroup.usermodule.security.SecurityConfig;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.vrpigroup.usermodule.constants.UserConstants.USER_WITH_USERNAME_OR_EMAIL_ALREADY_EXISTS;

@Service
@AllArgsConstructor
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userModuleRepository;
    private final ContactUsRepo contactUsRepo;
    private final EmailValidationServiceImpl emailValidationService;
    private final EmailValidation emailValidation;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;
    private  final EducationDetailsRepo educationDetailsRepo;



//    private final SecurityConfig securityConfig;


    public List<UserEntity> getAllUser() {
        try {
            return userModuleRepository.findAll();
        } catch (Exception e) {
            logger.error("Error while fetching all users", e);
            return Collections.emptyList();
        }
    }

    public Optional<UserEntity> getUserById(Long id) {
        try {
            return userModuleRepository.findById(id);

        } catch (Exception e) {
            logger.error("Error while fetching user by ID: {}", id, e);
            return Optional.empty();
        }
    }

    public UserDto createUser(UserDto userDto,MultipartFile profilePhoto,
                              MultipartFile aadharFront,MultipartFile aadharBack) {
            if (userModuleRepository.existsByEmail(userDto.getEmail())) {
                logger.warn(" email already exists. Cannot create a new user.");
                throw new UserAlreadyExistException(USER_WITH_USERNAME_OR_EMAIL_ALREADY_EXISTS+userDto.getEmail());
            }
            UserMapper userMapper = new UserMapper(passwordEncoder);
         boolean isValid=   emailValidation.isEmailValid(userDto.getEmail());
            UserEntity userEntity = new UserEntity();
            UserEntity user = userMapper.userDtoToUser(userEntity,userDto);
            try {
                if(isValid) {
                    if (profilePhoto != null && !profilePhoto.isEmpty()) {
                        user.setProfilePic(profilePhoto.getBytes());
                    }

                    if (aadharFront != null && !aadharFront.isEmpty()) {
                        user.setAadharFront(aadharFront.getBytes());
                    }
                    if (aadharBack != null && !aadharBack.isEmpty()) {
                        user.setAadharBack(aadharBack.getBytes());
                    }
                    userModuleRepository.save(user);
                    sendOtpByEmail(userDto.getEmail(), user.getOtp());
                    logger.info("User created successfully for email: {}", userDto.getEmail());
                    return userDto;
                };
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Error while setting Stroring image/pdf for user", e);
            }
            return null;
    }

    private void sendOtpByEmail(String email, String otp) {
        emailValidationService.sendVerificationEmail(email, otp);
        logger.info("Sending OTP to {}: {}", email, otp);
    }



    /*public UserEntity updateUser(Long id, UserEntity updatedUserModule) {
        if (userModuleRepository.existsById(id)) {
            updatedUserModule.setId(id);
            return userModuleRepository.save(updatedUserModule);
        } else {
            logger.warn("Failed to update user. User not found for ID: {}", id);
            return null;
        }
    }*/

    public void deleteUser(Long id) {
        try {
            userModuleRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error while deleting user with ID: {}", id, e);
        }
    }


    public UserDetailsDto loginUser(LoginDto userModule) {
        Optional<UserEntity> userByEmail = userModuleRepository.findByEmail(userModule.getEmail());
        if (userByEmail.isPresent() && verifyLogin(userByEmail.get(), userModule)) {
//            when login success then data geting from db
            UserEntity user = userByEmail.get();
            List<EnrollmentEntity> enrollments = enrollmentRepository.findByUser(user);
            List<EnrollCourseListDto> enrolledCourses = enrollments.stream()
                    .map(enrollment -> {
                        EnrollCourseListDto dto = new EnrollCourseListDto();
                        dto.setId(enrollment.getCourse().getId());
                        dto.setCouseId(enrollment.getCourse().getCouseId());
                        dto.setCourseName(enrollment.getCourse().getCourseName());
                        // Set any other fields as needed
                        return dto;
                    })
                    .collect(Collectors.toList());
            Optional<EducationDetails> educationDetails=educationDetailsRepo.findById(user.getId());

            return new UserDetailsDto(UserMapper.userToUserDto(user,new UserDto()),enrolledCourses,UserMapper.educationDetailsToEducationDetailsDto(educationDetails.get()), UserConstants.HttpStatus_OK);
        }
        logger.warn("Unsuccessful login attempt for email: {}", userModule.getEmail());

        return null;
    }
    private boolean verifyLogin(UserEntity user, LoginDto userModule) {
        if ( user.isActive()
                && passwordEncoder.matches(userModule.getPassword(), user.getCreatePassword())) {
            return true;
        }
        return false;
    }

    public boolean verifyEncryptedPassword(String password, String hashedPassword) {
        return passwordEncoder.matches(password, hashedPassword);
    }

    public void contactUs(ContactUs contactUs) {
        try {
            contactUsRepo.save(contactUs);
        } catch (Exception e) {
            logger.error("Error while processing contact us message", e);
        }
    }


    @Transactional
    public boolean verifyAccount(String email, String otp) {
        try {
            Optional<UserEntity> user = userModuleRepository.findByEmail(email);
            if (user.isPresent() && user.get().getOtp().equals(otp)) {
                UserEntity usr= user.get();
                usr.setActive(true);
                userModuleRepository.save(usr);
                logger.warn("Save data: {}", email);
                return true;
            }
            logger.warn("Failed to verify account. User not found for email: {}", email);
            return false;
        } catch (Exception e) {
            logger.error("Error while verifying account for email: {}", email, e);
            return false;
        }
    }

    public UpdateUserDto updateUserProfileAndDetails(Long id, UpdateUserDto updatedUser) {
        Optional<UserEntity> optionalUser = userModuleRepository.findById(id);
        UserMapper userMapper = new UserMapper(passwordEncoder);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setFathersName(updatedUser.getFathersName());
            user.setGender(updatedUser.getGender());
            user.setDateOfBirth(updatedUser.getDateOfBirth());
            user.setPhoneNumber(updatedUser.getPhoneNumber());
            user.setAddress(updatedUser.getAddress());
            user.setEmail(updatedUser.getEmail());
            user.setCreatePassword(passwordEncoder.encode(updatedUser.getCreatePassword()));
            user.setOccupation(updatedUser.getOccupation());
            user.setAadharCardNumber(updatedUser.getAadharCardNumber());
            try {
               UserEntity e= userModuleRepository.save(user);
                return userMapper.updateUserProfileAndDetails(e, updatedUser);
            } catch (Exception e) {
                logger.error("Error while updating user ", e);
            }
        } else {
            logger.warn("Failed to update user profile and details. User not found for ID: {}", id);
        }
        return null;
    }

    public Boolean updateUserDocuments(Long id, MultipartFile aadharFront, MultipartFile aadharBack, MultipartFile profilePic, MultipartFile incomeCert) {
        Optional<UserEntity> optionalUser = userModuleRepository.findById(id);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            try {
                if (profilePic != null && !profilePic.isEmpty()) {
                    if (isValidFileType(profilePic, "jpeg")) {
                        user.setProfilePic(profilePic.getBytes());
                    } else {
                        throw new IllegalArgumentException("Profile photo must be in JPEG format.");
                    }
                }
                if (aadharFront != null && !aadharFront.isEmpty()) {
                    if (isValidFileType(aadharFront, "pdf")) {
                        user.setAadharFront(aadharFront.getBytes());
                    } else {
                        throw new IllegalArgumentException("Aadhar front must be in PDF format.");
                    }
                }
                if (aadharBack != null && !aadharBack.isEmpty()) {
                    if (isValidFileType(aadharBack, "pdf")) {
                        user.setAadharBack(aadharBack.getBytes());
                    } else {
                        throw new IllegalArgumentException("Aadhar back must be in PDF format.");
                    }
                }
                if (incomeCert != null && !incomeCert.isEmpty()) {
                    if (isValidFileType(incomeCert, "pdf")) {
                        user.setAadharBack(incomeCert.getBytes());
                    } else {
                        throw new IllegalArgumentException("incomeCert must be in PDF format.");
                    }
                }
                userModuleRepository.save(user);
                return true;
            } catch (Exception e) {
                logger.error("Error while updating user documents", e);
                return false;
            }
        } else {
            logger.warn("Failed to update user documents. User not found for ID: {}", id);
            return false;
        }
    }

    private boolean isValidFileType(MultipartFile file, String fileType) {
        if (file == null || file.isEmpty()) {
            // No file uploaded, so it's valid
            return true;
        }

        String[] allowedExtensions = {"jpeg", "pdf"};
        String fileExtension = getFileExtension(file);

        return fileType.equalsIgnoreCase(fileExtension) && Arrays.asList(allowedExtensions).contains(fileExtension.toLowerCase());
    }

    private String getFileExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return fileName != null ? fileName.substring(fileName.lastIndexOf(".") + 1) : null;
    }

    public byte[] getImage(Long id, String field) {
        Optional<UserEntity> userOptional = userModuleRepository.findById(id);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();

            switch (field) {
                case "profilePic":
                    if (user.getProfilePic() != null) {
                        return user.getProfilePic();
                    } else {
                        logger.warn("Profile photo is null for user with ID: {}", id);
                        return null; // or return a default profile photo
                    }
                case "aadharFront":
                    if (user.getAadharFront() != null) {
                        return user.getAadharFront();
                    } else {
                        logger.warn("Aadhar Front photo is null for user with ID: {}", id);
                        return null; // or return a default profile photo
                    }
                case "aadharBack":
                    if (user.getAadharBack() != null) {
                        return user.getAadharBack();
                    } else {
                        logger.warn("Aadhar Back photo is null for user with ID: {}", id);
                        return null; // or return a default profile photo
                    }
                default:
                    logger.error("Invalid field type: {}", field);
                    return null; // or throw an exception
            }
        } else {
            logger.warn("Failed to get user. User not found for ID: {}", id);
            return null; // or throw an exception, depending on your use case
        }
    }

    public ResponseEntity<UserDto> getUserDetails(Long userId) {
        Optional<UserEntity> user = userModuleRepository.findById(userId);
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            UserDto userDto = new UserDto();
            userDto.setFirstName(userEntity.getFirstName());
            userDto.setLastName(userEntity.getLastName());
            userDto.setPhoneNumber(userEntity.getPhoneNumber());
            userDto.setEmail(userEntity.getEmail());
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

