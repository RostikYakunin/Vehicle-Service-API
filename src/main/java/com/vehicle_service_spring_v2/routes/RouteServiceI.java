package com.vehicle_service_spring_v2.routes;

import com.vehicle_service_spring_v2.routes.model.Route;
import com.vehicle_service_spring_v2.routes.model.dto.RouteDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RouteServiceI {

    Route addRoute(RouteDto routeDto);

    Route findRouteById(Long id);

    Route updateRoute(Long id, RouteDto routeDto);

    boolean deleteRouteById(Long id);

    List<Route> findAllRoutes();

    List<Route> findAllRoutesWithoutTransport();

}
