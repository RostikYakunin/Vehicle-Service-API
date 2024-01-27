package com.vehicle_service_spring_v3.routes;

import com.vehicle_service_spring_v3.routes.model.Route;
import com.vehicle_service_spring_v3.routes.model.dto.RouteDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RouteServiceI {

    Route addRoute(RouteDto routeDto);

    Optional<Route> findRouteById(Long id);

    Route updateRoute(RouteDto routeDto);

    boolean deleteRouteById(Long id);

    List<Route> findAllRoutes();

    List<Route> findAllRoutesWithoutTransport();

}
