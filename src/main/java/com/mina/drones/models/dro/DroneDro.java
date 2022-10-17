package com.mina.drones.models.dro;


import com.mina.drones.models.DroneModel;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DroneDro {
    @NotNull(message = "Serial number shouldn't be null")
    @Size(max = 100 ,message = "Serial number length shouldn't exceed 100 characters")
    @NotBlank
    private String serialNumber;
    @NotNull
    private DroneModel model;
    @Positive(message = "Weight limit should be positive")
    @Max(value = 500,message = "Maximum weight limit is 500gr")
    @NotNull
    private Integer weightLimit;
    private List<String> loadedMedicationsCodes = new ArrayList<>();
}
