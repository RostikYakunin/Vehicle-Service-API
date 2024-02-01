package com.vehicle_service_spring_v2.routes.model.dto;

import com.vehicle_service_spring_v2.routes.model.Route;
import org.mapstruct.*;

import java.util.Collections;

@Mapper(componentModel = "spring")
public interface RouteDtoMapper {
    Route toRoute(RouteDto routeDto);
    RouteDto toDto(Route route);
    @Mapping(target = "id", ignore = true)
    Route updateRoute (@MappingTarget Route route, RouteDto routeDto);

    @BeforeMapping
    default Route updating(@MappingTarget Route route, RouteDto routeDto) {
        if (routeDto.getEndOfWay() != null) {
            route.setEndOfWay(routeDto.getEndOfWay());
        }

        if (routeDto.getStartOfWay() != null){
            route.setStartOfWay(routeDto.getStartOfWay());
        }

        return route;
    }
}
