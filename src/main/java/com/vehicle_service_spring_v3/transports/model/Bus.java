package com.vehicle_service_spring_v3.transports.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Bus extends Transport {
    public Bus() {
    }

    @NotBlank(message = "Error, bus`s type cannot be empty")
    @Column(name = "bus_type")
    private String type;

    @Column(name = "doors_amount")
    private Integer amountOfDoors;

    @Override
    public String toString() {
        return super.toString() + ", transport`s type: " + type + ", door`s numbers: " + amountOfDoors;
    }
}
