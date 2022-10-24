package com.mina.drones.repositories;

import com.mina.drones.models.DroneState;
import com.mina.drones.models.db.Drone;
import com.mina.drones.models.db.DroneInfoAudit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface DronesAuditRepository extends ReactiveMongoRepository<DroneInfoAudit, String> {
}