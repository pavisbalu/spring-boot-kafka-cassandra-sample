package com.eventdriven.userservice.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DeleteUserEvent extends Event {
    private final Long userId;
}
