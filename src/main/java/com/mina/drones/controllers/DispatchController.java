package com.mina.drones.controllers;

import com.mina.drones.config.ErrorMessages;
import com.mina.drones.models.db.Drone;
import com.mina.drones.models.dro.DroneDro;
import com.mina.drones.services.DronesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/drones")
public class DispatchController {

    private DronesService dronesService;

    @Autowired
    public DispatchController(DronesService dronesService) {
        this.dronesService = dronesService;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DuplicateKeyException.class})
    public Map<String, String> handleValidationExceptions(DuplicateKeyException ex) {
        return Map.of(ErrorMessages.ERROR_KEY,ErrorMessages.EXCEPTIONS_MESSAGES.get(ex.getClass()));
    }

    @PostMapping
    public Mono<Drone> registerDrone(@RequestBody @Valid DroneDro drone){
        return dronesService.registerDrone(drone);
    }
}
