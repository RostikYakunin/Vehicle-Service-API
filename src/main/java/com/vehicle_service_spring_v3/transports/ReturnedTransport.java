package com.vehicle_service_spring_v3.transports;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class ReturnedTransport {
    private Long id;
    private String brandOfTransport;
    private Integer amountOfPassengers;
    private String driverQualificationEnum;
    private String type;
    private Integer amountOfDoors;
    private Integer amountOfRailcar;
    private Set<Long> routesId = new HashSet<>();
    private Set<Long> driversId = new HashSet<>();

    public ReturnedTransport() {
    }
}