package com.vehicle_service_spring_v2.transports.model.dto;

import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.routes.model.Route;
import com.vehicle_service_spring_v2.transports.model.Bus;
import com.vehicle_service_spring_v2.transports.model.Tram;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TransportViewMapper {
    @Mapping(source = "route", target = "routesId", qualifiedByName = "mapRoutesSet")
    @Mapping(source = "drivers", target = "driversId", qualifiedByName = "mapDriverSet")
    BusView toBusView(Bus bus);

    @Mapping(source = "route", target = "routesId", qualifiedByName = "mapRoutesSet")
    @Mapping(source = "drivers", target = "driversId", qualifiedByName = "mapDriverSet")
    TramView toTramView(Tram tram);

    @Named("mapRoutesSet")
    default Set<Long> mapRoutesSet(Set<Route> transports) {
        return transports.stream().map(Route::getId).collect(Collectors.toSet());
    }

    @Named("mapDriverSet")
    default Set<Long> mapDriverSet(Set<Driver> drivers) {
        return drivers.stream().map(Driver::getId).collect(Collectors.toSet());
    }
}

