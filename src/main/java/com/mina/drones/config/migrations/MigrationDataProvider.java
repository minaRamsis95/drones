package com.mina.drones.config.migrations;

import org.springframework.stereotype.Component;

@Component
public interface MigrationDataProvider {
    MigrationData getMigrationData();
}
