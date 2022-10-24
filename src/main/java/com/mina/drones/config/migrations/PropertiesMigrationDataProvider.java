package com.mina.drones.config.migrations;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropertiesMigrationDataProvider implements MigrationDataProvider {
    @Getter
    private MigrationData migrationData;

    @Autowired
    public PropertiesMigrationDataProvider(MigrationData migrationData) {
        this.migrationData = migrationData;
    }
}
