package com.vehicle_service_spring_v3.routes;


import com.vehicle_service_spring_v3.drivers.DriverRepoI;
import com.vehicle_service_spring_v3.routes.model.Route;
import com.vehicle_service_spring_v3.routes.model.dto.RouteDto;
import com.vehicle_service_spring_v3.routes.model.dto.RouteDtoHandler;
import com.vehicle_service_spring_v3.transports.TransportRepoI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RouteServiceImpl implements RouteServiceI {

    private final DriverRepoI driverRepo;
    private final TransportRepoI transportRepo;
    private final RouteRepoI routeRepo;

    @Autowired
    public RouteServiceImpl(DriverRepoI driverRepo, TransportRepoI transportRepo, RouteRepoI routeRepo) {
        this.driverRepo = driverRepo;
        this.transportRepo = transportRepo;
        this.routeRepo = routeRepo;
    }

    @Override
    public Route addRoute(RouteDto routeDto) {
        Route route = RouteDtoHandler.mappingDtoToRouteMethodAdd(routeDto);
        log.info("New route was added to db: " + route);

        return routeRepo.save(route);
    }

    @Override
    public Optional<Route> findRouteById(Long id) {
        if (routeRepo.findById(id).isEmpty()) {
            log.warn("Route with id = " + id + " not found");
            return Optional.empty();
        }

        return routeRepo.findById(id);
    }

    @Override
    public Route updateRoute(RouteDto routeDto) {
        Optional<Route> foundRoute = routeRepo.findById(routeDto.getId());

        Route updatedRoute = RouteDtoHandler.mappingDtoToRouteMethodUpdate(routeDto, foundRoute);
        log.info("Route updated successful");

        return routeRepo.save(updatedRoute);
    }

    @Override
    public boolean deleteRouteById(Long id) {
        Optional<Route> foundRoute = routeRepo.findById(id);

        if (foundRoute.isEmpty()) {
            log.warn("Route with id= " + id + " not found");
            return false;
        }

        if (!foundRoute.get().getTransports().isEmpty()) {
            log.warn("This route cannot be deleted, route has assigned transport: " + foundRoute.get());
            return false;
        }

        log.info("Route with id" + id + " was deleted");
        routeRepo.delete(foundRoute.get());
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
