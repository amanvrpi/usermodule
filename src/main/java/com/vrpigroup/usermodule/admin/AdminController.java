package com.vrpigroup.usermodule.admin;

import com.vrpigroup.usermodule.entity.UserEntity;
import com.vrpigroup.usermodule.exception.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> login(@RequestBody AdminLoginDto adminLoginDto) {
        // TODO: logic
        adminService.login(adminLoginDto);
        return ResponseEntity.ok("Login successful");
    }

    // Add admin logout endpoint
    @PostMapping("/logout")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> logout() {
        // TODO: logic
        return ResponseEntity.ok("Logout successful");
    }

    @Operation(
            summary = "Get User By Id",
            description = "Get user by id")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserEntity getUserById(@PathVariable Long id) {
        log.info("UserController:getUserById {} are called with : ", id);
        return adminService.getUserById(id);
    }

    @Operation(
            summary = "Get All User",
            description = "Get all users"
    )
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserEntity> getAllUser() {
        log.info("UserController:getAllUser {1} Getting all users");
        return adminService.getAllUser();
    }
}
