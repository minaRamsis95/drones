package com.mina.drones.repositories;

import com.mina.drones.models.db.Medication;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MedicationsRepository extends ReactiveMongoRepository<Medication, String> {
    Mono<Medication> findByCode(String code);
}