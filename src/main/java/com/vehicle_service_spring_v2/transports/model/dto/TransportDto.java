package com.vehicle_service_spring_v2.transports.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TransportDto {
    private Long id;
    private String brandOfTransport;
    private Integer amountOfPassengers;
    private String driverQualificationEnum;
    private String type;
    private Integer amountOfDoors;
    private Integer amountOfRailcar;
}