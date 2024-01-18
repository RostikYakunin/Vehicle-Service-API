package com.vehicle_service_spring_v2.routes.resource;


import com.vehicle_service_spring_v2.routes.RouteServiceI;
import com.vehicle_service_spring_v2.routes.model.Route;
import com.vehicle_service_spring_v2.routes.model.dto.RouteDto;
import com.vehicle_service_spring_v2.routes.model.dto.RouteView;
import com.vehicle_service_spring_v2.routes.model.dto.RouteViewMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {
    private final RouteServiceI routeService;
    private final RouteViewMapper routeViewMapper;

    @PostMapping
    public ResponseEntity<RouteView> createRoute(@RequestBody @Valid RouteDto routeDto) {
        RouteView route = routeViewMapper.routeToRouteView(routeService.addRoute(routeDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(route);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteView> findRouteById(@PathVariable long id) {
        Route route = routeService.findRouteById(id)
                .orElseThrow(() -> new RuntimeException("Route with id=" + id + " not found"));

        return ResponseEntity.ok(routeViewMapper.routeToRouteView(route));
    }

    @PutMapping
    public ResponseEntity<RouteView> updateRoute(@RequestBody @Valid RouteDto routeDto) {
        RouteView route = routeViewMapper.routeToRouteView(routeService.updateRoute(routeDto));
        return ResponseEntity.ok(route);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRouteById(@PathVariable long id) {
        routeService.deleteRouteById(id);
        return ResponseEntity.ok("Route with id " + id + " was deleted");
    }

    @GetMapping
    public ResponseEntity<List<RouteView>> findAllRoutes() {
        List<RouteView> routes = routeService.findAllRoutes().stream()
                .map(routeViewMapper::routeToRouteView)
                .collect(Collectors.toList());
        return ResponseEntity.ok(routes);
    }

    @GetMapping("/all_without_transport")
    public ResponseEntity<List<RouteView>> findAllRoutesWithoutTransport() {
        List<RouteView> routes = routeService.findAllRoutesWithoutTransport().stream()
                .map(routeViewMapper::routeToRouteView)
                .collect(Collectors.toList());
        return ResponseEntity.ok(routes);
    }
}
