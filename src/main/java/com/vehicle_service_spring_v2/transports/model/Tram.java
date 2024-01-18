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
public class Tram extends Transport {
    @Builder
    public Tram(Long id, @NotBlank(message = "Error, transport`s brand cannot be empty") String brandOfTransport, Integer amountOfPassengers, DriverQualificationEnum driverQualificationEnum, Set<Driver> drivers, Set<Route> route, Integer amountOfRailcar) {
        super(id, brandOfTransport, amountOfPassengers, driverQualificationEnum, drivers, route);
        this.amountOfRailcar = amountOfRailcar;
    }

    @Column(name = "railcar_amount")
    private Integer amountOfRailcar;
}
