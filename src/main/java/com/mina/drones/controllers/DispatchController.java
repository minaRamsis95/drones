package com.mina.drones.controllers;

import com.mina.drones.config.ErrorMessages;
import com.mina.drones.models.db.Drone;
import com.mina.drones.models.dro.DroneDro;
import com.mina.drones.models.dto.DroneDto;
import com.mina.drones.models.dto.MedicationDto;
import com.mina.drones.services.DronesService;
import com.mina.drones.services.MedicationsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/drones")
public class DispatchController {

    private DronesService dronesService;
    private MedicationsService medicationsService;

    @Autowired
    public DispatchController(DronesService dronesService, MedicationsService medicationsService) {
        this.dronesService = dronesService;
        this.medicationsService = medicationsService;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DuplicateKeyException.class})
    public Map<String, String> handleValidationExceptions(DuplicateKeyException ex) {
        return Map.of(ErrorMessages.ERROR_KEY, ErrorMessages.EXCEPTIONS_MESSAGES.get(ex.getClass()));
    }

    @PostMapping
    public Mono<DroneDto> registerDrone(@RequestBody @Valid DroneDro drone) {
        return dronesService.registerDrone(drone);
    }

    @PostMapping("/loadMedication")
    public Mono<DroneDto> loadMedication(@RequestParam String droneSerialNumber, @RequestParam String medicationCode) {
        return dronesService.loadDrone(droneSerialNumber,medicationCode);
    }

    @GetMapping("/checkLoadedMedications")
    public Flux<Mono<MedicationDto>> checkLoadedMedications(@RequestParam String droneSerialNumber) {
        return dronesService.findBySerialNumber(droneSerialNumber)
                .map(Drone::getLoadedMedicationsCodes)
                .flatMapMany(Flux::fromIterable)
                .map(code -> medicationsService.findByCode(code));
    }

    @GetMapping("/checkAvailableDrones")
    public Flux<DroneDto> checkAvailableDrones() {
        return dronesService.findAvailableDrones();
    }

    @GetMapping("/checkBatteryLevel")
    public Mono<Float> checkBatteryLevel(@RequestParam String droneSerialNumber) {
        return dronesService.findBySerialNumber(droneSerialNumber)
                .map(Drone::getBatteryPercentage);
    }
}
