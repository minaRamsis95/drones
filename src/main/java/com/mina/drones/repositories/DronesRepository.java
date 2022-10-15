package com.mina.drones.repositories;

import com.mina.drones.models.db.Drone;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface DronesRepository extends ReactiveMongoRepository<Drone, String> {
    Mono<Drone> findDroneBySerialNumber(String serialNumber);
}