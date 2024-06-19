package com.example.demo.event.impl;

import com.example.demo.configuration.locale.MessageUtil;
import com.example.demo.entity.User;
import com.example.demo.event.OnRegistrationCompleteEvent;
import com.example.demo.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    static String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    static int LENGTH = 10;
    static SecureRandom random = new SecureRandom();

    UserService userService;
    JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(@NonNull OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = generateRandomString();
        userService.createVerificationToken(user, token);

        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("Spring Boot DEMO <noreply@example.com>");
            helper.setTo(user.getEmail()); // Địa chỉ email nhận
            helper.setSubject(MessageUtil.getMessage("email.register-subject"));

            String content = "<html><body>";
            content += MessageUtil.getMessage("email.otp") + " <b>" + token + "</b>";
            content += "</body></html>";

            helper.setText(content, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateRandomString() {
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}