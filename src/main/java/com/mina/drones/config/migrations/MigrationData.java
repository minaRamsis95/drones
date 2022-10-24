package com.mina.drones.config.migrations;

import com.mina.drones.models.db.Drone;
import com.mina.drones.models.db.Medication;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties
@Component
@ConfigurationProperties(prefix = "migrations")
@PropertySource(value = "classpath:migrations.yml",factory = YamlPropertySourceFactory.class)
@ToString
@Data
public class MigrationData {
    private List<Drone> drones = new ArrayList<>();
    private List<Medication> medications = new ArrayList<>();
}
