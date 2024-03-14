//package com.vrpigroup.usermodule.service;

//import com.vrpigroup.usermodule.entity.Roles;
//import com.vrpigroup.usermodule.entity.UserEntity;
//import com.vrpigroup.usermodule.repo.UserRepository;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import java.util.HashSet;
//import java.util.Set;

//@Service
//public class CustomUserDetailsService /*implements UserDetailsService*/ {

    /*private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        Set<String> roles = new HashSet<>();
        for (Roles roles1 : userEntity.getRoles()) {
            String roles1Roles = roles1.getRoles();
            roles.add(roles1Roles);
        }
        return User.builder().username(userEntity.getUserName())
                .password(userEntity.getPassword()).roles(roles.toArray(new String[0])).build();
    }*/
//}