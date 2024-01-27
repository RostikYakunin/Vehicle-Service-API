package com.vehicle_service_spring_v3.routes;

import com.vehicle_service_spring_v3.routes.model.Route;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepoI extends CrudRepository<Route, Long> {
    @Query("SELECT r FROM Route r WHERE r.transports is empty")
    List<Route> findAllRoutesWithoutTransport();
}
