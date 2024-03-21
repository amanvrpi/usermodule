package com.vrpigroup.usermodule.controller;

import com.vrpigroup.usermodule.exception.CourseNotActiveException;
import com.vrpigroup.usermodule.entity.CourseEntity;
import com.vrpigroup.usermodule.exception.CourseNotFoundException;
import com.vrpigroup.usermodule.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@RestController
public class EnrollController {

    private final CourseService courseService;

    public EnrollController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/{id}")
    public Optional<CourseEntity> getCourseById(Long id) {
        return courseService.getCourseDetails(id);
    }

    @PostMapping("/create-course")
    public CourseEntity createCourse(CourseEntity courseEntity){
        return courseService.createCourse(courseEntity);
    }

    @PutMapping("/update-course/{id}")
    public String updateCourse(Long id) {
        return courseService.updateCourse(id);
    }

    @DeleteMapping("/delete-course/{id}")
    public Optional<CourseEntity> deleteCourse(Long id) {
        return courseService.deleteCourse(id);
    }

    @GetMapping("/all-courses")
    public String getAllCourses() {
        return courseService.getAllCourses().toString();
    }

    @PostMapping ("/enroll-course")
    public ResponseEntity<?> enrollCourse(@RequestParam Long courseId, @RequestParam Long userId) throws
            CourseNotActiveException, CourseNotFoundException {
//        return String.valueOf(courseService.enrollUserForCourse(courseId, userId));
        return new ResponseEntity<>(courseService.enrollUserForCourse(courseId, userId), HttpStatus.OK);
    }
}
