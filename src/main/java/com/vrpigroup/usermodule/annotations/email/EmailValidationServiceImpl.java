package com.vrpigroup.usermodule.annotations.email;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class EmailValidationServiceImpl implements EmailValidationService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailValidationServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendVerificationEmail(String email) {
        var message = new SimpleMailMessage();
        message.setTo(email);
    }

    @Async
    @Override
    public void sendVerificationEmail(String email, String otp) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            var verifyLink = "https://vrpigroup.com/vrpi-user/verify-account/" + email + "/" + otp;
            var htmlContent = """
                    <div>
                        <a href="%s" target="_blank">Click link to verify</a>
                        <p>Thank you for registering with us. Please verify your email to activate your account.</p>
                        <p>Our HR team will contact you soon.</p>
                    </div>
                    """.formatted(verifyLink);
            helper.setFrom("amanraj@vrpigroup.com", "Aman Raj");
            helper.setTo(email);
            helper.setCc("amanraj@vrpigroup.com");
            helper.setSubject("Email Verification");
            helper.setText(htmlContent, true);
            helper.setSentDate(new Date());
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    /*public void sendConformationMail(String email) {
        var message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Account Created");
        message.setText("Your account has been created successfully");
        javaMailSender.send(message);
    }*/

    //confirmation email after buying course
    public void sendConformationMail(String email, String courseName) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            var htmlContent = """
                    <div>
                        <p>Thank you for purchasing %s course.</p>
                        <p>Our HR team will contact you soon.</p>
                    </div>
                    """.formatted(courseName);
            helper.setFrom("support@vrpigroup.com", "VRPI Group");
            helper.setTo(email);
            helper.setCc("amanraj@vrpigroup.com");
            helper.setSubject("Course Purchased Successfully");
            helper.setText(htmlContent, true);
            helper.setSentDate(new Date());
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}