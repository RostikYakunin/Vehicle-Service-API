package com.vehicle_service_spring_v2.routes.model.dto;

import com.vehicle_service_spring_v2.routes.model.Route;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RouteDtoMapper {
    Route toRoute(RouteDto routeDto);
    RouteDto toDto(Route route);
}
