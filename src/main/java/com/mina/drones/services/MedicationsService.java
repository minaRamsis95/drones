package com.mina.drones.services;

import com.mina.drones.models.dto.MedicationDto;
import com.mina.drones.repositories.MedicationsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable
    public Mono<MedicationDto> findByCode(String code) {
        return medicationsRepository.findByCode(code)
                .map(medication -> modelMapper.map(medication, MedicationDto.class));
    }
}
