package org.circuit.breaker.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.circuit.breaker.model.Coffee;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class CoffeeService {

    private AtomicLong counter = new AtomicLong(0);

    @CircuitBreaker(requestVolumeThreshold = 4)
    public Integer getAvailability(Coffee coffee) {
        maybeFail();
        return new Random().nextInt(30);
    }

    private void maybeFail() {
        // introduce some artificial failures
        final Long invocationNumber = counter.getAndIncrement();
        if (invocationNumber % 4 > 1) { // alternate 2 successful and 2 failing invocations
            throw new RuntimeException("Service failed.");
        }
    }
}
