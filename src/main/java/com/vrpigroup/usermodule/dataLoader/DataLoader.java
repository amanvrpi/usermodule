//package com.vrpigroup.usermodule.dataLoader;
//
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import com.vrpigroup.usermodule.entity.CourseEntity;
//import com.vrpigroup.usermodule.entity.EnrollmentEntity;
//import com.vrpigroup.usermodule.entity.UserEntity;
//import com.vrpigroup.usermodule.repo.CourseRepository;
//import com.vrpigroup.usermodule.repo.EnrollmentRepository;
//import com.vrpigroup.usermodule.repo.UserRepository;
//import jakarta.persistence.EntityManager;
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//@Component
//public class DataLoader implements ApplicationRunner {
//    private final UserRepository userRepository;
//    private PasswordEncoder passwordEncoder;
//    private final CourseRepository courseRepository;
//    private final EnrollmentRepository enrollmentRepository;
//    private final EntityManager entityManager;
//
//    public DataLoader(UserRepository userRepository, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository, EntityManager entityManager,PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.courseRepository = courseRepository;
//        this.enrollmentRepository = enrollmentRepository;
//        this.entityManager = entityManager;
//        this.passwordEncoder= passwordEncoder;
//    }
//
//    @Override
//    @Transactional
//    public void run(ApplicationArguments args) {
//        /*userRepository.deleteAll();
//        courseRepository.deleteAll();
//        // Since EnrollmentEntity references UserEntity, delete all enrollments first
//        enrollmentRepository.deleteAll();*/
//
//        List<UserEntity> users = generateUniqueUsers();
//        List<CourseEntity> courses = Arrays.asList(CourseEntity.JAVA_PROGRAMMING, CourseEntity.DEVOPS, CourseEntity.AI, CourseEntity.EMBEDED_IOT);
//
//        userRepository.saveAll(users);
//        courseRepository.saveAll(courses);
//
//        persistEnrollments(users, courses);
//    }
//
//    private List<UserEntity> generateUniqueUsers() {
//        List<String> firstNames = Arrays.asList("Alice", "Bob", "Charlie", "David", "Emma", "Frank", "Grace", "Hank", "Ivy", "Jack");
//        List<String> lastNames = Arrays.asList("Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor");
//
//        return IntStream.range(0, 10)
//                .mapToObj(i -> {
//                    UserEntity userEntity = new UserEntity();
//                    userEntity.setFirstName(firstNames.get(i));
//                    userEntity.setLastName(lastNames.get(i));
//                    userEntity.setFathersName("Father" + (i + 1) + " " + lastNames.get(i));
//                    userEntity.setGender(i % 2 == 0 ? "Male" : "Female");
//                    userEntity.setDateOfBirth(LocalDate.of(1990 + i, i % 12 + 1, 15));
//                    userEntity.setPhoneNumber("123456789" + (i + 1));
//                    userEntity.setAddress("Address" + (i + 1) + ", City" + (i + 1));
//                    userEntity.setEmail("user" + (i + 1) + "@example.com");
//                    userEntity.setCreatePassword(passwordEncoder.encode("Pass" + (i + 1) + "@word"));
//                    userEntity.setOccupation(i % 2 == 0 ? "Professional" : "Student");
//                    userEntity.setAadharCardNumber("01234567890" + (i + 1));
//                    userEntity.setAadharFront(("aadhar_front" + (i + 1) + ".jpg").getBytes());
//                    userEntity.setAadharBack(("aadhar_back" + (i + 1) + ".jpg").getBytes());
//                    userEntity.setProfilePic(("profile" + (i + 1) + ".jpg").getBytes());
//                    userEntity.setRoles(Arrays.asList("USER", "ROLE" + (i + 1)));
//                    userEntity.setActive(true);
//                    userEntity.setOtp("1234" + (i + 1));
//                    return userEntity;
//                })
//                .collect(Collectors.toList());
//    }
//
//    private void persistEnrollments(List<UserEntity> users, List<CourseEntity> courses) {
//        for (UserEntity user : users) {
//            for (CourseEntity course : courses) {
//                EnrollmentEntity enrollment = new EnrollmentEntity();
//                enrollment.setUser(user);
//                enrollment.setCourse(course);
//                enrollment.setEnrollmentDate(new Date());
//                entityManager.persist(enrollment);
//            }
//        }
//    }
//}