package com.vehicle_service_spring_v2.drivers.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class ReturnedDriver {
    private Long id;
    private String nameOfDriver;
    private String surnameOfDriver;
    private String phoneNumber;
    private String qualificationEnum;
    private Set<Long> transportId = new HashSet<>();
    private Set<Long> routeId = new HashSet<>();

    public ReturnedDriver() {
    }
}
