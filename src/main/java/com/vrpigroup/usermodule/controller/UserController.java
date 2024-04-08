package com.vrpigroup.usermodule.controller;

import com.vrpigroup.usermodule.constants.UserConstants;
import com.vrpigroup.usermodule.dto.*;
import com.vrpigroup.usermodule.entity.ContactUs;
import com.vrpigroup.usermodule.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Lob;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * @Author: Kanhaiya Dharu & Aman Raj
 * @Project: usermodule
 * @Date: 26/03/2024
 * @Description: This class is used to handle all user related operations
 * like create, update, delete, get user, login, verify account, contact us, update user profile and details
 */

@Tag(
        name = "User",
        description = "User related operations create, update, delete, get user, login, verify account, contact us, update user profile and details"
)

@Log4j2
@RestController
@CrossOrigin(origins = "*")
@Validated
@RequestMapping("/vrpi-user")

public class UserController {

    private final UserService userModuleService;

    public UserController(UserService userModuleService) {
        this.userModuleService = userModuleService;
    }

    @Lob
    private byte[] image;

    @Operation(
            summary = "Create User",
            description = "Create user account")
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createUser(@Validated @RequestBody UserDto userModule,
                                                  @RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto,
                                                  @RequestParam(value = "aadharFront", required = false) MultipartFile aadharFront,
                                                  @RequestParam(value = "aadharBack", required = false) MultipartFile aadharBack

    ) {
        log.info("UserController:createUser - Creating user account for email: {}", userModule.getEmail());
        var createdUser = userModuleService.createUser(userModule, profilePhoto, aadharFront, aadharBack);
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
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto
                        (UserConstants.HttpStatus_OK, UserConstants.USER_ACCOUNT_CREATED_SUCCESSFULLY));
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
    public ResponseEntity<Long> loginUser(@Validated @RequestBody LoginDto loginDto) {
            log.info("UserController:loginUser - Attempting login for user: {}", loginDto.getEmail());
       Long user = userModuleService.loginUser(loginDto);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                log.warn("UserController:loginUser - Invalid credentials for user");
                return new ResponseEntity<>(user, HttpStatus.INTERNAL_SERVER_ERROR);
            }

    }

    /*@Operation(
            summary = "Get User Data",
            description = "Get user data")
    @GetMapping("/get-user-data/{userId}")
    public ResponseEntity<UserDetailsDto> getUserData(@PathVariable Long userId) {
        try {
            log.info("UserController:getUserData - Getting user data for user ID: {}", userId);
            var userData = userModuleService.getUserData(userId);
            if (userData != null) {
                log.info("UserController:getUserData - User data fetched successfully for user ID: {}", userId);
                return ResponseEntity.ok((UserDetailsDto) userData);
            } else {
                log.warn("UserController:getUserData - Failed to fetch user data for user ID: {}", userId);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            log.error("UserController:getUserData - Error while fetching user data for user ID: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }*/

//    @Operation(
//            summary = "Forgot Password",
//            description = "Forgot password")
//    @PostMapping("/forgot-password")
//    public ResponseEntity<ResponseDto> forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto) {
//        try {
//            log.info("UserController:forgotPassword - Processing forgot password request for email: {}", forgotPasswordDto.getEmail());
//            userModuleService.forgotPassword(forgotPasswordDto);
//            log.info("UserController:forgotPassword - Forgot password request processed successfully for email: {}", forgotPasswordDto.getEmail());
//            return ResponseEntity
//                    .status(HttpStatus.OK)
//                    .body(new ResponseDto(UserConstants.HttpStatus_OK, UserConstants.FORGOT_PASSWORD_SUCCESS));
//        } catch (Exception e) {
//            log.error("UserController:forgotPassword - Error while processing forgot password request for email: {}", forgotPasswordDto.getEmail(), e);
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseDto(UserConstants.INTERNAL_SERVER_ERROR_500, UserConstants.FAILED_TO_PROCESS_FORGOT_PASSWORD));
//        }
//    }

    @Operation(
            summary = "Update User",
            description = "Update user profile and details")
    @RequestMapping(value = "/update-user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<UpdateUserDto> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserDto user
    ) {
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


    @PutMapping("/update-doc/{userId}")
    public ResponseEntity<ResponseDto> updateUserDocuments(@PathVariable Long userId,
                                                           @RequestParam("profilePhoto") MultipartFile profilePhoto,
                                                           @RequestParam("aadharFront") MultipartFile aadharFront,
                                                           @RequestParam("aadharBack") MultipartFile aadharBack,
                                                           @RequestParam("incomeCert") MultipartFile incomeCert) {
        userModuleService.updateUserDocuments(userId, profilePhoto, aadharFront, aadharBack, incomeCert);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.ACCEPTED.name(), "User documents updated successfully"));
    }

    @GetMapping("/get-image/{field}/{userId}")
    public ResponseEntity<?> getImage(@PathVariable String field, @PathVariable Long userId) {
        try {
            image = userModuleService.getImage(userId, field);
            if (image != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_PNG);
                headers.setContentType(MediaType.IMAGE_JPEG);
                return new ResponseEntity<>(image, headers, HttpStatus.OK);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Log the error for debugging purposes
            log.error("Error occurred while retrieving image for user {} and field {}", userId, field, e);
            // Return a generic error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving the image.");
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
    public ResponseEntity<UserDetailsDtoById> getUserDetails(@PathVariable Long userId) {
//        try {
//            log.info("UserController:getUserDetails - Getting user details for user ID: {}", userId);
//            var userDetails = userModuleService.getUserDetails(userId);
//            if (userDetails != null) {
//                log.info("UserController:getUserDetails - User details fetched successfully for user ID: {}", userId);
//                return ResponseEntity.ok(userDetails.getBody());
//            } else {
//                log.warn("UserController:getUserDetails - Failed to fetch user details for user ID: {}", userId);
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//            }
//        } catch (Exception e) {
//            log.error("UserController:getUserDetails - Error while fetching user details for user ID: {}", userId, e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
        try {
            log.info("UserController:getUserDetails - Getting user details for user ID: {}", userId);
            var user = userModuleService.getUserDetails(userId);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                log.warn("UserController:getUserDetails - Failed to fetch user details for user ID: {}", userId);
                return new ResponseEntity<>(user, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            log.error("UserController:getUserDetails - Error while fetching user details for user ID: {}", userId, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}