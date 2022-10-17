package com.mina.drones.models.dto;


import lombok.Data;

@Data
public class MedicationDto {
    private String name;
    private float weight;
    private String code;
    private String imageUrl;
}
