package com.eventdriven.userservice.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UpdateUserEvent extends Event {
    private final Long userId;
    private final String firstName;
    private final String lastName;
    private final String email;
}
