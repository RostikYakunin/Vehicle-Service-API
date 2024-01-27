package com.vehicle_service_spring_v2.transports.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TramDto {
    private Long id;
    private String brandOfTransport;
    private Integer amountOfPassengers;
    private String driverQualificationEnum;
    private Integer amountOfRailcar;
}
