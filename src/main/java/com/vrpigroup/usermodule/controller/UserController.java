package com.vrpigroup.usermodule.controller;

import com.vrpigroup.usermodule.constants.UserConstants;
import com.vrpigroup.usermodule.dto.*;
import com.vrpigroup.usermodule.entity.ContactUs;
import com.vrpigroup.usermodule.entity.UserEntity;
import com.vrpigroup.usermodule.exception.UserNotFoundException;
import com.vrpigroup.usermodule.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Tag(
        name = "User",
        description = "User related operations create, update, delete, get user, login, verify account, contact us, update user profile and details"
)

@Log4j2
@RestController
@Validated
@RequestMapping("/vrpi-user")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userModuleService;

    @Autowired
    public UserController(UserService userModuleService) {
        this.userModuleService = userModuleService;
    }

    @Operation(
            summary = "Get All User",
            description = "Get all users"
    )
    @GetMapping("/all")
    public List<UserEntity> getAllUser() {
        log.info("UserController:getAllUser {1} Getting all users");
        return userModuleService.getAllUser();
    }

    @Operation(
            summary = "Get User By Id",
            description = "Get user by id")
    @GetMapping("/{id}")
    public Optional<UserEntity> getUserById(@PathVariable Long id) {
        log.info("UserController:getUserById {} are called with : ", id);
        return Optional.ofNullable(userModuleService.getUserById(id).orElseThrow(
                () -> new UserNotFoundException("User not found for ID: " + id)
        ));
    }

    @Operation(
            summary = "Create User",
            description = "Create user account")
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createUser(@RequestBody UserDto userModule,
                                                  @RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto,
                                                  @RequestParam(value = "aadharFront", required = false) MultipartFile aadharFront,
                                                  @RequestParam(value = "aadharBack", required = false) MultipartFile aadharBack
    ) {
        log.info("UserController:createUser - Creating user account for email: {}", userModule.getEmail());
        var createdUser = userModuleService.createUser(userModule,profilePhoto, aadharFront, aadharBack);
        if (createdUser != null) {
            log.info("UserController:createUser - User account created successfully for email: {}", userModule.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(UserConstants.CREATED_201, UserConstants.CREATED_MESSAGE));
        } else {
            log.warn("UserController:createUser - Failed to create user account for email: {}", userModule.getEmail());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @Operation(
            summary = "Verify Account",
            description = "Verify user account")
    @GetMapping("/verify-account/{email}/{otp}")
    public ResponseEntity<ResponseDto> verifyAccount(@PathVariable String email, @PathVariable String otp) {
        try {
            log.info("UserController:verifyAccount - Verifying account for email: {}", email);
            if (userModuleService.verifyAccount(email, otp)) {
                log.info("UserController:verifyAccount - Account verified successfully for email: {}", email);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto
                        (UserConstants.ACCOUNT_VERIFIED_SUCCESSFULLY, UserConstants.USER_ACCOUNT_CREATED_SUCCESSFULLY));
            } else {
                log.warn("UserController:verifyAccount - Failed to verify account for email: {}", email);

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto
                        (UserConstants.INTERNAL_SERVER_ERROR_500, UserConstants.FAILED_TO_VERIFY_ACCOUNT));
            }
        } catch (Exception e) {
            log.error("UserController:verifyAccount - Error while verifying account for email: {}", email, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto
                    (UserConstants.INTERNAL_SERVER_ERROR_500, UserConstants.FAILED_TO_VERIFY_ACCOUNT));

        }
    }

    @Operation(
            summary = "Login User",
            description = "Login user")
    @PostMapping("/login")
    public ResponseEntity<ResponseDto> loginUser(@RequestBody LoginDto loginDto) {
        try {
            log.info("UserController:loginUser - Attempting login for user: {}", loginDto.getEmail());
            var user = userModuleService.loginUser(loginDto);
            if (user != null) {
                if (user.isActive()) {
                    if (validateUserForLogin(user, loginDto)) {
                        log.info("UserController:loginUser - Login successful for user: {}", user.getEmail());
                        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(UserConstants.HttpStatus_OK, UserConstants.LOGIN_SUCCESSFUL));
                    } else {
                        log.warn("UserController:loginUser - Invalid credentials for user: {}", user.getEmail());
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto(UserConstants.UNAUTHORIZED_401, UserConstants.INVALID_CREDENTIALS));
                    }
                } else {
                    log.warn("UserController:loginUser - Account not verified for user: {}", user.getEmail());
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto(UserConstants.UNAUTHORIZED_401, UserConstants.FAILED_TO_VERIFY_ACCOUNT));
                }
            } else {
                log.warn("UserController:loginUser - Invalid credentials for user");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto(UserConstants.UNAUTHORIZED_401, UserConstants.INVALID_CREDENTIALS));
            }
        } catch (Exception e) {
            log.error("UserController:loginUser - Error during login for user: {}", loginDto.getEmail(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(UserConstants.INTERNAL_SERVER_ERROR_500, UserConstants.FAILED_TO_PROCEED_LOGIN));
        }
    }

    private boolean validateUserForLogin(UserEntity user, LoginDto loginDto) {
        return (user.getEmail().equals(loginDto.getEmail())
                && userModuleService.verifyEncryptedPassword(loginDto.getPassword(), user.getCreatePassword()));
    }

    @Operation(
            summary = "Update User",
            description = "Update user profile and details")
    @RequestMapping(value = "/update-user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<UpdateUserDto> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserDto user
    ){
        try {
            log.info("UserController:updateUser - Updating user details and profile photo for user ID: {}", id);
            var updatedUser = userModuleService.updateUserProfileAndDetails(id, user);
            if (updatedUser != null) {
                log.info("UserController:updateUser - User details and profile photo updated successfully for user ID: {}", id);
                return ResponseEntity.ok(updatedUser);
            } else {
                log.warn("UserController:updateUser - Failed to update user details and profile photo for user ID: {}", id);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            log.error("UserController:updateUser - Error while updating user details and profile photo for user ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/update-doc/{id}")
    public ResponseEntity<ResponseDto> updateUserDocuments(@PathVariable Long id,
                                                           @RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto,
                                                           @RequestParam(value = "aadharFront", required = false) MultipartFile aadharFront,
                                                           @RequestParam(value = "aadharBack", required = false) MultipartFile aadharBack) {
        try {
            // Validate file types
            if (profilePhoto != null && !profilePhoto.isEmpty()) {
                if (!isValidFileType(profilePhoto, "jpeg")) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ResponseDto(UserConstants.BAD_REQUEST_400, "Profile photo must be in JPEG format."));
                }
            }

            if (aadharFront != null && !aadharFront.isEmpty()) {
                if (!isValidFileType(aadharFront, "pdf")) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ResponseDto(UserConstants.BAD_REQUEST_400, "Aadhar front must be in PDF format."));
                }
            }

            if (aadharBack != null && !aadharBack.isEmpty()) {
                if (!isValidFileType(aadharBack, "pdf")) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ResponseDto(UserConstants.BAD_REQUEST_400, "Aadhar back must be in PDF format."));
                }
            }

            // Call service method to update user documents
            Boolean isDocSave =  userModuleService.updateUserDocuments(id, profilePhoto, aadharFront, aadharBack);

            // Return response based on the result of the service call
            if (isDocSave) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDto(UserConstants.HttpStatus_OK, UserConstants.USER_DOCUMENTS_UPDATED_SUCCESSFULLY));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseDto(UserConstants.INTERNAL_SERVER_ERROR_500, UserConstants.FAILED_TO_UPDATE_USER_DOCUMENTS));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(UserConstants.INTERNAL_SERVER_ERROR_500, "Error occurred while updating user documents."));
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
    @GetMapping("/get-image/{field}/{id}")
    public ResponseEntity<?> getImage(@PathVariable String field, @RequestParam Long id) {
        byte[] image = userModuleService.getImage(id, field);
        if (image != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Operation(
            summary = "Delete User",
            description = "Delete user")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable Long id) {
        try {
            log.info("UserController:deleteUser - Deleting user with ID: {}", id);
            userModuleService.deleteUser(id);
            log.info("UserController:deleteUser - User deleted successfully with ID: {}", id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(UserConstants.HttpStatus_OK, UserConstants.USER_DELETED_SUCCESSFULLY));
        } catch (Exception e) {
            log.error("UserController:deleteUser - Error while deleting user with ID: {}", id, e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(UserConstants.INTERNAL_SERVER_ERROR_500, UserConstants.FAILED_TO_DELETE_USER));
        }
    }

    @Operation(
            summary = "Contact Us",
            description = "Send contact us message")
    @PostMapping("/contact-us")
    public ResponseEntity<ResponseDto> contactUs(@RequestBody ContactUs contactUs) {
        try {
            log.info("UserController:contactUs - Processing contact us message");
            userModuleService.contactUs(contactUs);
            log.info("UserController:contactUs - Contact us message sent successfully");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(
                            UserConstants.HttpStatus_OK, UserConstants.MESSAGE_SENT_SUCCESSFULLY));
        } catch (Exception e) {
            log.error("UserController:contactUs - Error while processing contact us message", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(UserConstants.INTERNAL_SERVER_ERROR_500, UserConstants.FAILED_TO_SEND_MESSAGE));
        }
    }

    // Get user details by userId
    @GetMapping("/get-user-details/{userId}")
public ResponseEntity<UserDto> getUserDetails(@PathVariable Long userId) {
        try {
            log.info("UserController:getUserDetails - Getting user details for user ID: {}", userId);
            var userDetails = userModuleService.getUserDetails(userId);
            if (userDetails != null) {
                log.info("UserController:getUserDetails - User details fetched successfully for user ID: {}", userId);
                return ResponseEntity.ok(userDetails.getBody());
            } else {
                log.warn("UserController:getUserDetails - Failed to fetch user details for user ID: {}", userId);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            log.error("UserController:getUserDetails - Error while fetching user details for user ID: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}