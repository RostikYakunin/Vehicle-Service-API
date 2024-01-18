package com.vehicle_service_spring_v2.transports.model;

import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.drivers.model.DriverQualificationEnum;
import com.vehicle_service_spring_v2.routes.model.Route;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Bus extends Transport {
    @Builder
    public Bus(Long id, String brandOfTransport, Integer amountOfPassengers, DriverQualificationEnum driverQualificationEnum, Set<Driver> drivers, Set<Route> route, String type, Integer amountOfDoors) {
        super(id, brandOfTransport, amountOfPassengers, driverQualificationEnum, drivers, route);
        this.type = type;
        this.amountOfDoors = amountOfDoors;
    }

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
