package com.vehicle_service_spring_v_3.drivers.model.dto;

import com.vehicle_service_spring_v_3.drivers.model.Driver;
import com.vehicle_service_spring_v_3.drivers.model.DriverQualificationEnum;
import com.vehicle_service_spring_v_3.routes.model.Route;
import com.vehicle_service_spring_v_3.transports.model.Transport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DriverViewMapper {
    @Mapping(source = "qualificationEnum", target = "qualificationEnum", qualifiedByName = "mapEnumToString")
    @Mapping(source = "transport", target = "transportId", qualifiedByName = "mapTransportSetToTransportIdSet")
    @Mapping(source = "route", target = "routeId", qualifiedByName = "mapRouteSetToRouteIdSet")
    DriverView driverToDriverView(Driver driver);

    @Named("mapEnumToString")
    default String mapEnumToString(DriverQualificationEnum qualificationEnum) {
        return qualificationEnum.toString();
    }

    @Named("mapTransportSetToTransportIdSet")
    default Set<Long> mapTransportSetToTransportIdSet(Set<Transport> transportSet) {
        return transportSet.stream()
                .map(Transport::getId)
                .collect(Collectors.toSet());
    }

    @Named("mapRouteSetToRouteIdSet")
    default Set<Long> mapRouteSetToRouteIdSet(Set<Route> routeSet) {
        return routeSet.stream()
                .map(Route::getId)
                .collect(Collectors.toSet());
    }
}
