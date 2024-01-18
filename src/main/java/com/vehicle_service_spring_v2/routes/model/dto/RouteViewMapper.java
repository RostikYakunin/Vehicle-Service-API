package com.vehicle_service_spring_v2.routes.model.dto;

import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.routes.model.Route;
import com.vehicle_service_spring_v2.transports.model.Transport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RouteViewMapper {
    @Mapping(source = "transports", target = "transportsId", qualifiedByName = "mapTransportSet")
    @Mapping(source = "drivers", target = "driversId", qualifiedByName = "mapDriverSet")
    RouteView routeToRouteView(Route route);

    Route routeViewToRoute(RouteView routeView);

    @Named("mapTransportSet")
    default Set<Long> mapTransportSet(Set<Transport> transports) {
        return transports.stream().map(Transport::getId).collect(Collectors.toSet());
    }

    @Named("mapDriverSet")
    default Set<Long> mapDriverSet(Set<Driver> drivers) {
        return drivers.stream().map(Driver::getId).collect(Collectors.toSet());
    }
}
