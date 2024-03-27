package com.vrpigroup.usermodule;

import com.razorpay.RazorpayClient;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "User Module",
                version = "1.0",
                description = "User Module",
               contact = @Contact(
                        name = "Aman Raj and Kanhaiya Dharu",
                        email = "officialkanhaiya121@gmail.com",
                        url = "https://vrpigroup.com"

        ),
license = @License(
 name = "vrpigroup",
        url = "https://vrpigroup.com"
)),
        servers = {
                @Server(
                        url = "http://localhost:8081",
                        description = "Local Server"
                ),
                @Server(
                        url = "https://vrpigroup.com",
                        description = "Production Server"
                )
        },
        externalDocs = @ExternalDocumentation(
                description = "User Module",
                url = "https://vrpigroup.com"
        )
)

@EnableJpaRepositories
public class UserModuleApplication {

/*
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }*/

    @Bean
    public RazorpayClient razorpayClient() {
        try {
            return new RazorpayClient("rzp_test_HDibd0r72mDwz5", "AIs9tgYbPT4quUHU8VfMPcGy");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(UserModuleApplication.class, args);
    }

}
/*
# Security
security.jwt.uri=/auth/**
security.jwt.header=Authorization
security.jwt.prefix=Bearer
security.jwt.expiration=604800
security.jwt.secret=JwtSecretKey



# Logging
logging.level.org.springframework=INFO
logging.level.com.aman=DEBUG

DB_CLOSE_ON_EXIT=FALSE



# Jwt Token
jwt.secret=5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437
*/