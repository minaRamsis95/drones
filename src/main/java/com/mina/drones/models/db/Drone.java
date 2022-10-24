package com.mina.drones.models.db;


import com.mina.drones.models.DroneModel;
import com.mina.drones.models.DroneState;
import jakarta.validation.constraints.*;
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
    @NotNull(message = "Serial number shouldn't be null")
    @Size(max = 100 ,message = "Serial number length shouldn't exceed 100 characters")
    @NotBlank
    private String serialNumber;
    @NotNull
    private DroneModel model;
    @Positive(message = "Weight limit should be positive")
    @Max(value = 500,message = "Maximum weight limit is 500gr")
    @NotNull
    private Float weightLimit;
    @Positive(message = "Weight limit should be positive")
    @Max(value = 500,message = "Maximum weight limit is 500gr")
    @NotNull
    private Float remainingWeight;
    @Min(0)
    @Max(100)
    private float batteryPercentage = 100f;
    @NotNull
    private DroneState state;
    private List<String> loadedMedicationsCodes = new ArrayList<>();
}
