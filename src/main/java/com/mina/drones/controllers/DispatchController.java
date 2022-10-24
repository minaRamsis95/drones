package com.mina.drones.controllers;

import com.mina.drones.config.ErrorMessages;
import com.mina.drones.models.DroneState;
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

    @PutMapping("/{droneSerialNumber}/loadMedication")
    public Mono<DroneDto> loadMedication(@PathVariable String droneSerialNumber, @RequestParam String medicationCode) {
        return dronesService.loadDrone(droneSerialNumber,medicationCode);
    }

    @GetMapping("/{droneSerialNumber}/loadedMedications")
    public Flux<MedicationDto> checkLoadedMedications(@PathVariable String droneSerialNumber) {
        return dronesService.findBySerialNumber(droneSerialNumber)
                .map(Drone::getLoadedMedicationsCodes)
                .flatMapMany(Flux::fromIterable)
                .flatMap(code -> medicationsService.findByCode(code));
    }

    @GetMapping("/available")
    public Flux<DroneDto> checkAvailableDrones() {
        return dronesService.findAvailableDrones();
    }

    @GetMapping("/{droneSerialNumber}/batteryLevel")
    public Mono<Float> checkBatteryLevel(@PathVariable String droneSerialNumber) {
        return dronesService.findBySerialNumber(droneSerialNumber)
                .map(Drone::getBatteryPercentage);
    }

    @PutMapping("/{droneSerialNumber}/batteryLevel")
    public Mono<Float> updateBatteryLevel(@PathVariable String droneSerialNumber,@RequestParam Float batteryLevel) {
        return dronesService.updateBatteryLevel(droneSerialNumber,batteryLevel)
                .map(DroneDto::getBatteryPercentage);
    }

    @PutMapping("/{droneSerialNumber}/state")
    public Mono<DroneState> updateState(@PathVariable String droneSerialNumber, @RequestParam DroneState state) {
        return dronesService.updateState(droneSerialNumber,state)
                .map(DroneDto::getState);
    }

    @PutMapping("/{droneSerialNumber}/finishDelivery")
    public Mono<DroneDto> finishDelivery(@PathVariable String droneSerialNumber) {
        return dronesService.finishDelivery(droneSerialNumber);
    }

}
