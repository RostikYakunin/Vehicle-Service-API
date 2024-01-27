package com.vehicle_service_spring_v2.utils;

import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.drivers.model.dto.DriverView;
import com.vehicle_service_spring_v2.drivers.model.dto.DriverViewMapper;
import com.vehicle_service_spring_v2.routes.model.Route;
import com.vehicle_service_spring_v2.routes.model.dto.RouteView;
import com.vehicle_service_spring_v2.routes.model.dto.RouteViewMapper;
import com.vehicle_service_spring_v2.transports.model.Bus;
import com.vehicle_service_spring_v2.transports.model.Tram;
import com.vehicle_service_spring_v2.transports.model.Transport;
import com.vehicle_service_spring_v2.transports.model.dto.TransportView;
import com.vehicle_service_spring_v2.transports.model.dto.TransportViewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ViewMapperUtil {
    private final TransportViewMapper transportViewMapper;
    private final DriverViewMapper driverViewMapper;
    private final RouteViewMapper routeViewMapper;

    public TransportView toTransportView(Transport transport) {
        return switch (transport.getDriverQualificationEnum()) {
            case BUS_DRIVER -> transportViewMapper.toView(transportViewMapper.toBusView((Bus) transport));
            case TRAM_DRIVER -> transportViewMapper.toView(transportViewMapper.toTramView((Tram) transport));
            default -> throw new RuntimeException("Unknown qualification=" + transport.getDriverQualificationEnum());
        };
    }

    public RouteView toRoadView(Route route) {
        return routeViewMapper.routeToRouteView(route);
    }

    public DriverView toDriverView(Driver driver){
        return driverViewMapper.toDriverView(driver);
    }
}
