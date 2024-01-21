package com.vehicle_service_spring_v2.transports.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TramView {
    private Long id;
    private String brandOfTransport;
    private Integer amountOfPassengers;
    private String driverQualificationEnum;
    private Integer amountOfRailcar;
    private Set<Long> routesId = new HashSet<>();
    private Set<Long> driversId = new HashSet<>();
}
