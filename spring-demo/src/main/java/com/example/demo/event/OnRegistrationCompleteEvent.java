package com.example.demo.event;

import com.example.demo.entity.User;
import com.example.demo.entity.VerificationToken;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    VerificationToken verificationToken;

    public OnRegistrationCompleteEvent(VerificationToken verificationToken) {
        super(verificationToken);
        this.verificationToken = verificationToken;
    }
}
