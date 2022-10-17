package com.mina.drones.repositories;

import com.mina.drones.models.DroneState;
import com.mina.drones.models.db.Drone;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface DronesRepository extends ReactiveMongoRepository<Drone, String> {
    Mono<Drone> findDroneBySerialNumber(String serialNumber);
    Flux<Drone> findByBatteryPercentageGreaterThanAndState(Float batteryPercentages, DroneState state);
}