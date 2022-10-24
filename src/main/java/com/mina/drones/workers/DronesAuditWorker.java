package com.mina.drones.workers;

import com.mina.drones.models.db.DroneInfoAudit;
import com.mina.drones.repositories.DronesAuditRepository;
import com.mina.drones.repositories.DronesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
public class DronesAuditWorker {
    private DronesRepository dronesRepository;
    private DronesAuditRepository dronesAuditRepository;
    private ModelMapper modelMapper;

    @Autowired
    public DronesAuditWorker(DronesRepository dronesRepository, DronesAuditRepository dronesAuditRepository, ModelMapper modelMapper) {
        this.dronesRepository = dronesRepository;
        this.dronesAuditRepository = dronesAuditRepository;
        this.modelMapper = modelMapper;
    }

    @Scheduled(fixedDelayString = "${properties.audit.delay.milliseconds}")
    public void performAudit() {
        dronesAuditRepository.saveAll(
                dronesRepository.findAll()
                        .flatMap(drone ->{
                                    DroneInfoAudit infoAudit = modelMapper.map(drone, DroneInfoAudit.class);
                                    infoAudit.setId(null);
                                    infoAudit.setDate(new Date());
                                    return Mono.just(infoAudit);
                                }).toIterable()
        ).blockLast();
    }
}
