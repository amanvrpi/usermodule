package com.vrpigroup.usermodule.security;
//
//import com.vrpigroup.usermodule.service.CustomUserDetailsService;
//import com.vrpigroup.usermodule.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//import org.springframework.context.annotation.Lazy;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
@Configuration
public class SecurityConfig {
//
//    /*private final UserService userService;
//    private final CustomUserDetailsService customUserDetailsService;
//
//    public SecurityConfig(@Lazy UserService userService, CustomUserDetailsService customUserDetailsService) {
//        this.userService = userService;
//        this.customUserDetailsService = customUserDetailsService;
//    }*/
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests(authorizeRequests ->
//                        authorizeRequests
//                                .requestMatchers(HttpMethod.POST, "/login/**").permitAll()
//                                .requestMatchers(HttpMethod.GET, "/vrpi-user/**").permitAll()
//                                .requestMatchers(HttpMethod.POST, "/vrpi-user/**").permitAll()
//                                .requestMatchers(HttpMethod.PUT, "/vrpi-user/**").permitAll()
//                                .requestMatchers(HttpMethod.DELETE, "/vrpi-user/**").permitAll()
//                                .requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
//                                .requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()
//                                .requestMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll()
//                                .anyRequest().authenticated()
//                )
//                .httpBasic(withDefaults())
//                .formLogin(withDefaults())
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(sessionManagement -> sessionManagement
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//                //.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return username -> User.builder()
//                .username("user")
//                .password(passwordEncoder().encode("password"))
//                .build();
//    }
//
//    /*@Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter() {
//        return new JwtAuthenticationFilter(jwtTokenProvider(), customUserDetailsService);
//    }*/
//
//    /*@Bean
//    public JwtTokenProvider jwtTokenProvider() {
//        return new JwtTokenProvider();
//    }
//
//    @Bean
//    public JwtTokenFilter jwtTokenFilter() {
//        return new JwtTokenFilter(jwtTokenProvider());
//    }*/
}