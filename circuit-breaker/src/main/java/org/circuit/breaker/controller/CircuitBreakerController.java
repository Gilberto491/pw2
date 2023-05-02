package org.circuit.breaker.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.circuit.breaker.model.Coffee;
import org.circuit.breaker.repository.CoffeeRepository;
import org.circuit.breaker.service.CoffeeService;

import java.util.concurrent.atomic.AtomicLong;

import static org.jboss.resteasy.cdi.i18n.LogMessages.LOGGER;

@Path("/coffee")
@RequiredArgsConstructor
public class CircuitBreakerController {

    private final AtomicLong counter = new AtomicLong(0);
    private final CoffeeRepository coffeeRepository;
    private final CoffeeService coffeeService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello";
    }

    @Path("/{id}/availability")
    @GET
    public Response availability(int id) {
        final Long invocationNumber = counter.getAndIncrement();


        Coffee coffee = coffeeRepository.getCoffeeById(id);
        // check that coffee with given id exists, return 404 if not
        if (coffee == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        try {
            Integer availability = coffeeService.getAvailability(coffee);
            LOGGER.infof("CoffeeResource#availability() invocation #%d returning successfully", invocationNumber);
            return Response.ok(availability).build();
        } catch (RuntimeException e) {
            String message = e.getClass().getSimpleName() + ": " + e.getMessage();
            LOGGER.errorf("CoffeeResource#availability() invocation #%d failed: %s", invocationNumber, message);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(message)
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .build();
        }
    }
}
