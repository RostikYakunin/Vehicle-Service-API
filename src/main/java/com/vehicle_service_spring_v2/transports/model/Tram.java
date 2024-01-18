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
public class Tram extends Transport {
    @Builder
    public Tram(Long id, String brandOfTransport, Integer amountOfPassengers, DriverQualificationEnum driverQualificationEnum, Set<Driver> drivers, Set<Route> route, Integer amountOfRailcar) {
        super(id, brandOfTransport, amountOfPassengers, driverQualificationEnum, drivers, route);
        this.amountOfRailcar = amountOfRailcar;
    }

    @Column(name = "railcar_amount")
    private Integer amountOfRailcar;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tram tram = (Tram) o;
        return Objects.equals(amountOfRailcar, tram.amountOfRailcar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), amountOfRailcar);
    }
}
