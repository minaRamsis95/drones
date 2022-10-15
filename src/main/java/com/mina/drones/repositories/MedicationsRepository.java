package com.mina.drones.repositories;

import com.mina.drones.models.db.Medication;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationsRepository extends ReactiveMongoRepository<Medication, String> {
}