package com.mina.drones.models.dto;


import com.mina.drones.models.DroneModel;
import com.mina.drones.models.DroneState;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
public class DroneDto {
    private String serialNumber;
    private DroneModel model;
    private Float weightLimit;
    private Float remainingWeight;
    private float batteryPercentage = 0f;
    private DroneState state;
    private List<String> loadedMedicationsCodes = new ArrayList<>();
}
