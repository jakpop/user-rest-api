package com.example.userrestapi.repository;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class IdGenerator {

    private AtomicLong nextId = new AtomicLong(1);

    public long nextId() {
        return nextId.getAndIncrement();
    }
}
