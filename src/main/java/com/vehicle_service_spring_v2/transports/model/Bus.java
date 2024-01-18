package com.vehicle_service_spring_v2.transports.model;

import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.drivers.model.DriverQualificationEnum;
import com.vehicle_service_spring_v2.routes.model.Route;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bus extends Transport {
    @Builder
    public Bus(Long id, @NotBlank(message = "Error, transport`s brand cannot be empty") String brandOfTransport, Integer amountOfPassengers, DriverQualificationEnum driverQualificationEnum, Set<Driver> drivers, Set<Route> route, String type, Integer amountOfDoors) {
        super(id, brandOfTransport, amountOfPassengers, driverQualificationEnum, drivers, route);
        this.type = type;
        this.amountOfDoors = amountOfDoors;
    }

    @NotBlank(message = "Error, bus`s type cannot be empty")
    @Column(name = "bus_type")
    private String type;

    @Column(name = "doors_amount")
    private Integer amountOfDoors;
}
