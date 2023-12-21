package com.vehicle_service_spring_v2.drivers.model.dto;

import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.drivers.model.DriverQualificationEnum;
import com.vehicle_service_spring_v2.routes.model.dto.ReturnedRoute;
import com.vehicle_service_spring_v2.routes.model.Route;
import com.vehicle_service_spring_v2.transports.ReturnedTransport;
import com.vehicle_service_spring_v2.transports.model.Bus;
import com.vehicle_service_spring_v2.transports.model.Tram;
import com.vehicle_service_spring_v2.transports.model.Transport;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ReturnedConverter {

    public static ReturnedRoute convertToReturnedRoute(Route route) {
        ReturnedRoute returnedRoute = new ReturnedRoute();

        returnedRoute.setId(route.getId());
        returnedRoute.setStartOfWay(route.getStartOfWay());
        returnedRoute.setEndOfWay(route.getEndOfWay());

        Set<Transport> transports = route.getTransports();
        if (!transports.isEmpty()) {
            returnedRoute.getTransportsId().addAll(
                    transports.stream()
                            .map(Transport::getId)
                            .collect(Collectors.toSet())

            );
        }

        Set<Driver> drivers = route.getDrivers();
        if (!drivers.isEmpty()) {
            returnedRoute.getDriversId().addAll(
                    drivers.stream()
                            .map(Driver::getId)
                            .collect(Collectors.toSet())

            );
        }
        return returnedRoute;
    }

    public static ReturnedTransport convertToReturnedTransport(Transport transport) {
        ReturnedTransport returnedTransport = new ReturnedTransport();

        returnedTransport.setId(transport.getId());
        returnedTransport.setBrandOfTransport(transport.getBrandOfTransport());
        returnedTransport.setAmountOfPassengers(transport.getAmountOfPassengers());
        returnedTransport.setDriverQualificationEnum(transport.getDriverQualificationEnum().name());

        if (transport.getDriverQualificationEnum().equals(DriverQualificationEnum.BUS_DRIVER)) {
            Bus bus = (Bus) transport;
            returnedTransport.setType(bus.getType());
            returnedTransport.setAmountOfDoors(bus.getAmountOfDoors());
        } else {
            Tram tram = (Tram) transport;
            returnedTransport.setAmountOfRailcar(tram.getAmountOfRailcar());
        }

        Set<Driver> drivers = transport.getDrivers();
        if (!drivers.isEmpty()) {
            Set<Long> trId = drivers.stream()
                    .map(Driver::getId)
                    .collect(Collectors.toSet());

            returnedTransport.getDriversId().addAll(trId);
        }

        Set<Route> routes = transport.getRoute();
        if (!routes.isEmpty()) {
            Set<Long> routesId = routes.stream()
                    .map(Route::getId)
                    .collect(Collectors.toSet());

            returnedTransport.getRoutesId().addAll(routesId);
        }

        return returnedTransport;
    }
}
