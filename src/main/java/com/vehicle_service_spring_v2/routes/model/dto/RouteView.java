package com.vehicle_service_spring_v2.routes.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RouteView {
    private long id;
    private String startOfWay;
    private String endOfWay;
    private Set<Long> transportsId = new HashSet<>();
    private Set<Long> driversId = new HashSet<>();
}
