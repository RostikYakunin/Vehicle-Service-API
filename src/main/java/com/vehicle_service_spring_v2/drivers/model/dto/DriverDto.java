package com.vehicle_service_spring_v2.drivers.model.dto;

import com.vehicle_service_spring_v2.routes.model.Route;
import com.vehicle_service_spring_v2.transports.model.Transport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class DriverDto {
    private Long id;
    private String nameOfDriver;
    private String surnameOfDriver;
    private String phoneNumber;
    private String qualificationEnum;
    private Set<Transport> transport = new HashSet<>();
    private Set<Route> route = new HashSet<>();
}
