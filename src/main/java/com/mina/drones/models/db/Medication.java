package com.mina.drones.models.db;


import com.mina.drones.models.DroneModel;
import com.mina.drones.models.DroneState;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Medication {
    @Id
    private String id;
    @Pattern(regexp = "[a-zA-Z1-9_-]*")
    @NotNull
    private String name;
    @Positive
    @Max(500)
    private float weight;
    @Indexed(unique = true)
    @Pattern(regexp = "[A-Z1-9_]*")
    @NotNull
    private String code;
    private String imageUrl;
}
