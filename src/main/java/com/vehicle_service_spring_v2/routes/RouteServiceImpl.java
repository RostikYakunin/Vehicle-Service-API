package com.vehicle_service_spring_v2.routes;


import com.vehicle_service_spring_v2.routes.model.Route;
import com.vehicle_service_spring_v2.routes.model.dto.RouteDto;
import com.vehicle_service_spring_v2.routes.model.dto.RouteDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteServiceI {
    private final RouteRepoI routeRepo;
    private final RouteDtoMapper routeDtoMapper;

    @Override
    public Route addRoute(RouteDto routeDto) {
        Route route = routeDtoMapper.toRoute(routeDto);
        log.info("New route was added to db: " + route);

        return routeRepo.save(route);
    }

    @Override
    public Route findRouteById(Long id) {
        return routeRepo.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("Route with id=" + id + " not found !")
                );
    }

    @Override
    public Route updateRoute(RouteDto routeDto) {
        Route updatedRoute = routeDtoMapper.toRoute(routeDto);
        log.info("Route updated successful");

        return routeRepo.save(updatedRoute);
    }

    @Override
    public boolean deleteRouteById(Long id) {
        Route foundRoute = routeRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Route with id= " + id + " not found")
        );

        boolean isEmpty = foundRoute.getTransports().isEmpty();
        if (!isEmpty) {
            log.warn("This route cannot be deleted, route has assigned transport: " + foundRoute);
            return false;
        }

        log.info("Route with id" + id + " was deleted");
        routeRepo.delete(foundRoute);
        return true;
    }

    @Override
    public List<Route> findAllRoutes() {
        return (List<Route>) routeRepo.findAll();
    }

    @Override
    public List<Route> findAllRoutesWithoutTransport() {
        return routeRepo.findAllRoutesWithoutTransport();
    }
}
