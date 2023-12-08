package com.vehicle_service_spring_v2.transports;

import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.routes.model.Route;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class TransportDto {
    private Long id;
    private String brandOfTransport;
    private Integer amountOfPassengers;
    private String driverQualificationEnum;
    private String type;
    private Integer amountOfDoors;
    private Integer amountOfRailcar;
    private Set<Driver> drivers = new HashSet<>();
    private Set<Route> routes = new HashSet<>();

    public TransportDto() {
    }
}
