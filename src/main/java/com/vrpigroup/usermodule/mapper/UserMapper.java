package com.vrpigroup.usermodule.mapper;

import com.vrpigroup.usermodule.dto.DocResponseByUserGetId;
import com.vrpigroup.usermodule.dto.EducationDetailsDto;
import com.vrpigroup.usermodule.dto.UpdateUserDto;
import com.vrpigroup.usermodule.dto.UserDto;
import com.vrpigroup.usermodule.entity.EducationDetails;
import com.vrpigroup.usermodule.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Random;

/**
 * @Author Aman Raj and Kanhaiya
 * @Description This class is used to convert UserDto to UserEntity and UserEntity to UserDto
 */
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

    public static UserDto userToUserDto(UserEntity userEntity, UserDto userDto) {
     if (userEntity.isActive()){
         userDto.setId(userEntity.getId());
         userDto.setFirstName(userEntity.getFirstName());
         userDto.setLastName(userEntity.getLastName());
         userDto.setFathersName(userEntity.getFathersName());
         userDto.setGender(userEntity.getGender());
         userDto.setDateOfBirth(userEntity.getDateOfBirth());
         userDto.setPhoneNumber(userEntity.getPhoneNumber());
         userDto.setAddress(userEntity.getAddress());
         userDto.setEmail(userEntity.getEmail());
         userDto.setCreatePassword(userEntity.getCreatePassword());
         userDto.setOccupation(userEntity.getOccupation());
         userDto.setAadharCardNumber(userEntity.getAadharCardNumber());
         userDto.setRoles(userEntity.getRoles().toString());
         return userDto;
     }
     else {
         return null;
     }
    }

    public static DocResponseByUserGetId userToDocResponseByUserGetId(UserEntity userEntity, DocResponseByUserGetId userDto) {
        userDto.setId(userEntity.getId());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setFathersName(userEntity.getFathersName());
        userDto.setGender(userEntity.getGender());
        userDto.setDateOfBirth(userEntity.getDateOfBirth());
        userDto.setPhoneNumber(userEntity.getPhoneNumber());
        userDto.setAddress(userEntity.getAddress());
        userDto.setEmail(userEntity.getEmail());
        userDto.setCreatePassword(userEntity.getCreatePassword());
        userDto.setOccupation(userEntity.getOccupation());
        userDto.setAadharCardNumber(userEntity.getAadharCardNumber());
        userDto.setIncomeCert(userEntity.getIncomeCert() != null ? "present" : null);
        userDto.setProfilePic(userEntity.getProfilePic() != null ? "present" : null);
        userDto.setAadharBack(userEntity.getAadharBack() != null ? "present" : null);
        userDto.setAadharFront(userEntity.getAadharFront() != null ? "present" : null);
        return userDto;
    }

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

    public static EducationDetailsDto educationDetailsToEducationDetailsDto(EducationDetails educationDetails) {
        EducationDetailsDto educationDetailsDto = new EducationDetailsDto();
        educationDetailsDto.setEducationLevel(educationDetails.getEducationLevel());
        educationDetailsDto.setDegree(educationDetails.getDegree());
        educationDetailsDto.setInstitutionName(educationDetails.getInstitutionName());
        educationDetailsDto.setInstituteLocation(educationDetails.getInstituteLocation());
        educationDetailsDto.setStartDate(educationDetails.getStartDate());
        educationDetailsDto.setEndDate(educationDetails.getEndDate());
        educationDetailsDto.setGrade(educationDetails.getGrade());
        educationDetailsDto.setUserId(educationDetails.getUserId());
        educationDetailsDto.setCreatedAt(educationDetails.getCreatedAt());
        educationDetailsDto.setUpdatedAt(educationDetails.getUpdatedAt());
        return educationDetailsDto;
    }
}
