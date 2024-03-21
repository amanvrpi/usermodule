package com.vrpigroup.usermodule.controller;
import com.vrpigroup.usermodule.entity.EducationDetails;
import com.vrpigroup.usermodule.service.EducationDetailsService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Log4j2
@RestController
@RequestMapping("/education-details")
public class EducationDetailsController {
@Autowired
    private final EducationDetailsService educationDetailsService;

    public EducationDetailsController(EducationDetailsService educationDetailsService) {
        this.educationDetailsService = educationDetailsService;
    }
    @PostMapping("/create-education-details/{userId}")
    public ResponseEntity<String> saveEducationDetails(@PathVariable Long userId, @Valid @RequestBody EducationDetails educationDetails) {
        if (educationDetails == null) {
            return ResponseEntity.badRequest().body("Education details cannot be null");
        }

        try {
            String result = educationDetailsService.saveEducationDetails(educationDetails, userId);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // No body needed for not found response
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Data integrity violation: " + e.getMessage());
        } catch (Exception e) {
            // Log the exception
            log.error("Error occurred while saving education details", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong while saving data");
        }
    }
}