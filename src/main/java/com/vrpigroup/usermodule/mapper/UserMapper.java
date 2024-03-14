package com.vrpigroup.usermodule.mapper;

import com.vrpigroup.usermodule.dto.UpdateUserDto;
import com.vrpigroup.usermodule.dto.UserDto;
import com.vrpigroup.usermodule.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Random;
/**
 * @Author Aman Raj and Kanhaiya
 * @Description This class is used to convert UserDto to UserEntity and UserEntity to UserDto
 * */
@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    //    convert UserDto to UserEntity
    public UserEntity userDtoToUser(UserEntity userEntity, UserDto userDto) {
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setFathersName(userDto.getFathersName());
        userEntity.setGender(userDto.getGender());
        userEntity.setDateOfBirth(userDto.getDateOfBirth());
        userEntity.setPhoneNumber(userDto.getPhoneNumber());
        userEntity.setAddress(userDto.getAddress());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setCreatePassword(passwordEncoder.encode(userDto.getCreatePassword()));
        userEntity.setOccupation(userDto.getOccupation());
        userEntity.setAadharCardNumber(userDto.getAadharCardNumber());
        userEntity.setOtp(generateOtp());

        return userEntity;
    }

    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

//    public UserDto userToUserDto(UserEntity userEntity, UserDto userDto) {
//
//        userDto.setName(userEntity.getName());
//        userDto.setFathersName(userEntity.getFathersName());
//        userDto.setUserName(userEntity.getUserName());
//        userDto.setPassword(userEntity.getPassword());
//        userDto.setAddress(userEntity.getAddress());
//        userDto.setAadharCardNumber(userEntity.getAadharCardNumber());
//        userDto.setActive(userEntity.isActive());
//        userDto.setAadharCardNumber(userEntity.getAadharCardNumber());
//        userDto.setPanCardNumber(userEntity.getPanCardNumber());
//        userDto.setPhoneNumber(userEntity.getPhoneNumber());
//        userDto.setPincode(userEntity.getPincode());
//        userDto.setRoles(userEntity.getRoles());
//        userDto.setOtp(userEntity.getOtp());
//        userDto.setDateOfBirth(userEntity.getDateOfBirth());
//        userDto.setEmail(userEntity.getEmail());
//        return userDto;
//    }



    public UpdateUserDto updateUserProfileAndDetails(UserEntity userEntity, UpdateUserDto updatedUser) {
        updatedUser.setFirstName(userEntity.getFirstName());
        updatedUser.setLastName(userEntity.getLastName());
        updatedUser.setFathersName(userEntity.getFathersName());
        updatedUser.setGender(userEntity.getGender());
        updatedUser.setDateOfBirth(userEntity.getDateOfBirth());
        updatedUser.setPhoneNumber(userEntity.getPhoneNumber());
        updatedUser.setAddress(userEntity.getAddress());
        updatedUser.setEmail(userEntity.getEmail());
        updatedUser.setCreatePassword(passwordEncoder.encode(userEntity.getCreatePassword()));
        updatedUser.setOccupation(userEntity.getOccupation());
        updatedUser.setAadharCardNumber(userEntity.getAadharCardNumber());
        return updatedUser;
    }

}
