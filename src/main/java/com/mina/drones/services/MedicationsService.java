package com.mina.drones.services;

import com.mina.drones.models.db.Drone;
import com.mina.drones.models.dro.DroneDro;
import com.mina.drones.repositories.MedicationsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MedicationsService {
    private MedicationsRepository medicationsRepository;
    private ModelMapper modelMapper;

    @Autowired
    public MedicationsService(MedicationsRepository medicationsRepository, ModelMapper modelMapper) {
        this.medicationsRepository = medicationsRepository;
        this.modelMapper = modelMapper;
    }
}
