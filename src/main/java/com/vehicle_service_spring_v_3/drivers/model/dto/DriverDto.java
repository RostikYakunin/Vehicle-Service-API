package com.vehicle_service_spring_v_3.drivers.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DriverDto {
    private Long id;
    private String nameOfDriver;
    private String surnameOfDriver;
    private String phoneNumber;
    private String qualificationEnum;
}
