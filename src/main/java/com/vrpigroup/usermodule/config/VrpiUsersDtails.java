package com.vrpigroup.usermodule.config;

import com.vrpigroup.usermodule.entity.UserEntity;
import com.vrpigroup.usermodule.repo.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VrpiUsersDtails implements UserDetailsService {
    private final UserRepository userRepository;

    public VrpiUsersDtails(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userOptional = userRepository.findByEmail(username);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User details not found for the user: " + username);
        }

        UserEntity user = userOptional.get();

        // Retrieve authorities from the user entity and convert them to Spring Security GrantedAuthority objects
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(user.getRoles().get(0)))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getCreatePassword(),
                authorities
        );
    }
}
