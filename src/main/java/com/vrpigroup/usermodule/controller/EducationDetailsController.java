package com.vrpigroup.usermodule.controller;
import com.vrpigroup.usermodule.entity.EducationDetails;
import com.vrpigroup.usermodule.service.EducationDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/education-details")
public class EducationDetailsController {
@Autowired
    private final EducationDetailsService educationDetailsService;

    public EducationDetailsController(EducationDetailsService educationDetailsService) {
        this.educationDetailsService = educationDetailsService;
    }
    @PostMapping("/create-education-details/{userId}")
    public ResponseEntity<String> saveEducationDetails(@PathVariable Long userId, @RequestBody EducationDetails educationDetails) {
        String result = educationDetailsService.saveEducationDetails(educationDetails, userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/get-education-details/{userId}")
    public ResponseEntity<EducationDetails> getEducationDetails(@PathVariable Long userId) {
        EducationDetails educationDetails = educationDetailsService.getEducationDetails(userId);
        return new ResponseEntity<>(educationDetails, HttpStatus.OK);
    }
}