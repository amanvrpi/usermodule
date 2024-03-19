package com.vrpigroup.usermodule.annotations.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    //@Value("${spring.mail.host}")
    private String mailHost = "smtp.gmail.com";
    //@Value("${spring.mail.port}")
    private String mailPort = "587";
    //@Value("${spring.mail.username}")
    private String mailUsername = "amanrashm@gmail.com";
    //@Value("${spring.mail.password}")
    private String mailPassword = "cchgrrsdnchmraxc";

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailHost);
        javaMailSender.setPort(Integer.parseInt(mailPort));
        javaMailSender.setUsername(mailUsername);
        javaMailSender.setPassword(mailPassword);

        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.smtp.starttls.enable", "true");
        return javaMailSender;
    }
}