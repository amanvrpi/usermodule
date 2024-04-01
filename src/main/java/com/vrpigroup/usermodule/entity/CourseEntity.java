package com.vrpigroup.usermodule.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Course_ID")
    private Long id;

    public CourseEntity(Long id) {
        this.id = id;
    }

    @Schema(name = "Course Name", description = "Name of the Course", example = "Java Programming", required = true)
    private String courseName;
    private String description;
    private  String instructor;
    private String duration;
    private String label;
    private Boolean active;
    private Long price;
    // Other attributes and relationships...

    // Constructor to initialize fixed courses
    public CourseEntity(String courseName, String description, String instructor, String duration,String lable,Boolean active,Long price) {
        this.courseName = courseName;
        this.description = description;
        this.instructor = instructor;
        this.duration = duration;
        this.label =lable;
        this.active=active;
        this.price=price;

    }
                 // awaz aarh hai ?

    // Define fixed courses as constants or enums
    public static final CourseEntity JAVA_PROGRAMMING = new CourseEntity("Java Full-Stack", "Learn Java programming from scratch", "John Doe", "3 months","vprico001",true,1000L);
    public static final CourseEntity DEVOPS = new CourseEntity("DevOps with Cloud", "Learn Python programming from scratch", "Jane Smith", "3 months","vprico002",true,1000L);
    public static final CourseEntity AI = new CourseEntity("Artificial Intelligence", "Learn web development basics", "Mike Johnson", "2 months","vprico003",true,1000L);
    public static final CourseEntity EMBEDED_IOT = new CourseEntity("Embedded & IoT", "Introduction to data science concepts", "Emily Brown", "4 months","vprico004",true,1000L);

    public CourseEntity() {
    }
}
