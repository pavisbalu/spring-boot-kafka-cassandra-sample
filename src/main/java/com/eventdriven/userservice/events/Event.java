package com.eventdriven.userservice.events;

import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class Event {
    private final UUID id = UUID.randomUUID();
    private final Long timestamp = System.currentTimeMillis();
}
