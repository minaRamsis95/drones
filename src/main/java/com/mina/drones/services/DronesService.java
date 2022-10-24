package com.mina.drones.services;

import com.mina.drones.models.DroneState;
import com.mina.drones.models.db.Drone;
import com.mina.drones.models.dro.DroneDro;
import com.mina.drones.models.dto.DroneDto;
import com.mina.drones.models.dto.MedicationDto;
import com.mina.drones.repositories.DronesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DronesService {
    private DronesRepository dronesRepository;
    private ModelMapper modelMapper;

    private Float minimumLoadingBatteryPercentage;
    private MedicationsService medicationsService;
    @Autowired
    public DronesService(DronesRepository dronesRepository, ModelMapper modelMapper, @Value("${properties.loading.batteryPercentage}") Float minimumLoadingBatteryPercentage, MedicationsService medicationsService) {
        this.dronesRepository = dronesRepository;
        this.modelMapper = modelMapper;
        this.minimumLoadingBatteryPercentage = minimumLoadingBatteryPercentage;
        this.medicationsService = medicationsService;
    }

    public Mono<DroneDto> registerDrone(DroneDro drone) {
        return dronesRepository
                .save(modelMapper.map(drone, Drone.class))
                .map(d -> modelMapper.map(d, DroneDto.class));
    }

    public Mono<Drone> findBySerialNumber(String serialNumber) {
        return dronesRepository.findDroneBySerialNumber(serialNumber);
    }

    public Flux<DroneDto> findAvailableDrones() {
        return dronesRepository
                .findByBatteryPercentageGreaterThanAndState(minimumLoadingBatteryPercentage, DroneState.IDLE)
                .map(d -> modelMapper.map(d, DroneDto.class));
    }

    public Mono<DroneDto> loadDrone(String serialNumber, String medicationCode) {
        Mono<Drone> droneMono = dronesRepository.findDroneBySerialNumber(serialNumber);
        Mono<MedicationDto> medicationMono = medicationsService.findByCode(medicationCode);
        return droneMono.zipWith(medicationMono).map(
                        zipped -> {
                            Drone drone = zipped.getT1();
                            MedicationDto medication = zipped.getT2();
                            if (couldBeLoaded(drone, medication)) {
                                drone.getLoadedMedicationsCodes().add(medicationCode);
                                drone.setRemainingWeight(drone.getRemainingWeight() - medication.getWeight());
                                drone.setState(DroneState.LOADING);
                            }
                            return drone;
                        }
                ).flatMap(d -> dronesRepository.save(d))
                .map(d -> modelMapper.map(d, DroneDto.class));
    }

    private boolean couldBeLoaded(Drone drone, MedicationDto med) {
        return drone.getRemainingWeight() >= med.getWeight() && drone.getBatteryPercentage() >= minimumLoadingBatteryPercentage && drone.getState() == DroneState.IDLE;
    }

    public Mono<DroneDto> updateBatteryLevel(String serialNumber, Float batteryLevel) {
        return dronesRepository.findDroneBySerialNumber(serialNumber)
                .map(drone -> {
                    drone.setBatteryPercentage(batteryLevel);
                    return drone;
                })
                .flatMap(d -> dronesRepository.save(d))
                .map(d -> modelMapper.map(d, DroneDto.class));
    }

    public Mono<DroneDto> updateState(String serialNumber, DroneState state) {
        return dronesRepository.findDroneBySerialNumber(serialNumber)
                .map(drone -> {
                    drone.setState(state);
                    return drone;
                })
                .flatMap(d -> dronesRepository.save(d))
                .map(d -> modelMapper.map(d, DroneDto.class));
    }

    public Mono<DroneDto> finishDelivery(String droneSerialNumber) {
        return dronesRepository.findDroneBySerialNumber(droneSerialNumber)
                .map(drone -> {
                    drone.setState(DroneState.IDLE);
                    drone.setRemainingWeight(drone.getWeightLimit());
                    drone.getLoadedMedicationsCodes().clear();
                    return drone;
                })
                .flatMap(d -> dronesRepository.save(d))
                .map(d -> modelMapper.map(d, DroneDto.class));
    }
}
