package com.vehicle_service_spring_v2.routes.model.dto;

import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.transports.model.Transport;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class RouteDto {
    private long id;
    private String startOfWay;
    private String endOfWay;
    private Set<Transport> transports = new HashSet<>();
    private Set<Driver> drivers = new HashSet<>();

    public RouteDto() {
    }
}
