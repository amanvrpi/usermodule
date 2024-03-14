package com.vrpigroup.usermodule.dataLoader;

import com.vrpigroup.usermodule.entity.UserEntity;
import com.vrpigroup.usermodule.repo.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class DataLoader implements ApplicationRunner {
    private final UserRepository userRepository;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userRepository.deleteAll();
        List<UserEntity> users = generateUniqueUsers();
        userRepository.saveAll(users);
    }

    private List<UserEntity> generateUniqueUsers() {
        List<String> firstNames = Arrays.asList("Alice", "Bob", "Charlie", "David", "Emma", "Frank", "Grace", "Hank", "Ivy", "Jack");
        List<String> lastNames = Arrays.asList("Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor");

        return IntStream.range(0, 10)
                .mapToObj(i -> {
                    UserEntity userEntity = new UserEntity();
                    userEntity.setId((long) (i + 1));
                    userEntity.setFirstName(firstNames.get(i));
                    userEntity.setLastName(lastNames.get(i));
                    userEntity.setFathersName("Father" + (i + 1) + " " + lastNames.get(i));
                    userEntity.setGender(i % 2 == 0 ? "Male" : "Female");
                    userEntity.setDateOfBirth(LocalDate.of(1990 + i, i % 12 + 1, 15));
                    userEntity.setPhoneNumber("123456789" + (i + 1));
                    userEntity.setAddress("Address" + (i + 1) + ", City" + (i + 1));
                    userEntity.setEmail("user" + (i + 1) + "@example.com");
                    userEntity.setCreatePassword("Pass" + (i + 1) + "@word");
                    userEntity.setOccupation(i % 2 == 0 ? "Professional" : "Student");
                    userEntity.setAadharCardNumber("01234567890" + (i + 1));

                    // Set placeholder image data (you should replace this with actual image data)
                    userEntity.setAadharFront(("aadhar_front" + (i + 1) + ".jpg").getBytes());
                    userEntity.setAadharBack(("aadhar_back" + (i + 1) + ".jpg").getBytes());
                    userEntity.setProfilePic(("profile" + (i + 1) + ".jpg").getBytes());

                    // Set roles
                    userEntity.setRoles(Arrays.asList("USER", "ROLE" + (i + 1)));

                    userEntity.setActive(true);
                    userEntity.setOtp("1234" + (i + 1)); // Set your OTP logic here

                    return userEntity;
                })
                .collect(Collectors.toList());
    }
}
