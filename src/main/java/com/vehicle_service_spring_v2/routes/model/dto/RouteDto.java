package com.vehicle_service_spring_v2.routes.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RouteDto {
    private long id;
    private String startOfWay;
    private String endOfWay;
}
