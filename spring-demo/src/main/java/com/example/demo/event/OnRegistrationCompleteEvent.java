package com.example.demo.event;

import com.example.demo.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    User user;

    public OnRegistrationCompleteEvent(User user) {
        super(user);
        this.user = user;
    }
}
