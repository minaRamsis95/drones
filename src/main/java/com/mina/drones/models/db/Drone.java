package com.mina.drones.models.db;


import com.mina.drones.models.DroneModel;
import com.mina.drones.models.DroneState;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Data
@Document
public class Drone {
    @Id
    private String id;
    @Indexed(unique = true)
    private String serialNumber;
    private DroneModel model;
    private Float weightLimit;
    private Float remainingWeight;
    private float batteryPercentage = 0f;
    private DroneState state;
    private List<String> loadedMedicationsCodes = new ArrayList<>();
}
