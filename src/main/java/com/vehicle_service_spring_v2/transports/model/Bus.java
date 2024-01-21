package com.vehicle_service_spring_v2.transports.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
public class Bus extends Transport {
    @NotBlank(message = "Error, bus`s type cannot be empty")
    @Column(name = "bus_type")
    private String type;

    @Column(name = "doors_amount")
    private Integer amountOfDoors;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Bus bus = (Bus) o;
        return Objects.equals(type, bus.type) && Objects.equals(amountOfDoors, bus.amountOfDoors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type, amountOfDoors);
    }
}
