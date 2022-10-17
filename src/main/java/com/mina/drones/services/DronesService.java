package com.mina.drones.services;

import com.mina.drones.models.db.Drone;
import com.mina.drones.models.dro.DroneDro;
import com.mina.drones.models.dto.DroneDto;
import com.mina.drones.repositories.DronesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DronesService {
    private DronesRepository dronesRepository;
    private ModelMapper modelMapper;

    @Autowired
    public DronesService(DronesRepository dronesRepository, ModelMapper modelMapper) {
        this.dronesRepository = dronesRepository;
        this.modelMapper = modelMapper;
    }

    public Mono<DroneDto> registerDrone(DroneDro drone) {
        return dronesRepository
                .save(modelMapper.map(drone, Drone.class))
                .map(d -> modelMapper.map(d, DroneDto.class));
    }

    public Mono<Drone> findBySerialNumber(String serialNumber) {
        return dronesRepository.findDroneBySerialNumber(serialNumber);
    }
}
