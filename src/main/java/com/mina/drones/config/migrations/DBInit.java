package com.mina.drones.config.migrations;


import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mina.drones.repositories.DronesRepository;
import com.mina.drones.repositories.MedicationsRepository;
import org.springframework.beans.factory.annotation.Value;

@ChangeLog(order = "001")
public class DBInit {

    @ChangeSet(order = "001", id = "initDrones", author = "system")
    public void initDrones(MigrationDataProvider migrationDataProvider, DronesRepository dronesRepository) {
        dronesRepository.saveAll(migrationDataProvider.getMigrationData().getDrones()).blockLast();
    }

//    @ChangeSet(order = "001", id = "initMedications", author = "system")
//    public void initMedications(MigrationDataProvider migrationDataProvider, MedicationsRepository medicationsRepository) {
//        medicationsRepository.saveAll(migrationDataProvider.getMigrationData().getMedications()).blockLast();
//    }
}
