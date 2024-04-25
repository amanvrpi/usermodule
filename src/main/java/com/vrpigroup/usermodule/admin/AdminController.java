package com.vrpigroup.usermodule.admin;

import com.vrpigroup.usermodule.entity.InstructorEntity;
import com.vrpigroup.usermodule.entity.UserEntity;
import com.vrpigroup.usermodule.exception.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final Logger log = LoggerFactory.getLogger(AdminController.class);
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Add admin login endpoint
    @PostMapping("/login")

    public ResponseEntity<String> login(@RequestBody AdminLoginDto adminLoginDto) {
        // TODO: logic
        adminService.login(adminLoginDto);
        return ResponseEntity.ok("Login successful");
    }

    // Add admin logout endpoint
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // TODO: logic
        return ResponseEntity.ok("Logout successful");
    }

    @Operation(
            summary = "Get User By Id",
            description = "Get user by id")
    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable Long id) {
        log.info("UserController:getUserById {} are called with : ", id);
        return adminService.getUserById(id);
    }

    @Operation(
            summary = "Get All User",
            description = "Get all users"
    )
    @GetMapping("/all")
    public List<Map<String, Object>> getAllUser() {
        log.info("UserController:getAllUser {1} Getting all users");
        return adminService.getAllUsers();
    }
    @PostMapping("/instructor-add")
    public ResponseEntity<String> saveInstructor(@RequestBody InstructorEntity instructorEntity) {
        log.info("AdminController:saveInstructor {1} Adding instructor");
        return ResponseEntity.ok(adminService.saveInstructor(instructorEntity));
    }
//    This is under work by the team
    @PutMapping ("/instructor-update/{id}")
    public ResponseEntity<InstructorEntity> updateInstructor(@RequestBody InstructorEntity instructorEntity, @PathVariable Long id) {
        log.info("AdminController:updateInstructor {1} Updating instructor");
        return adminService.updateInstructor(instructorEntity, id);
    }
    @GetMapping("/instructor-all")
    public List<InstructorEntity> getAllInstructor() {
        log.info("AdminController:getAllInstructor {1} Getting all instructors");
        return adminService.getAllInstructor();
    }

}
