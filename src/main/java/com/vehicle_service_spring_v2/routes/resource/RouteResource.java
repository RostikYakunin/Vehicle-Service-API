package com.vehicle_service_spring_v2.routes.resource;


import com.vehicle_service_spring_v2.routes.RouteServiceI;
import com.vehicle_service_spring_v2.routes.model.dto.RouteDto;
import com.vehicle_service_spring_v2.routes.model.dto.RouteView;
import com.vehicle_service_spring_v2.utils.ViewMapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteResource {
    private final RouteServiceI routeService;
    private final ViewMapperUtil viewMapperUtil;

    @PostMapping
    public ResponseEntity<RouteView> createRoute(@RequestBody @Valid RouteDto routeDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Stream.of(routeService.addRoute(routeDto))
                        .map(viewMapperUtil::toRoadView)
                        .findFirst()
                        .orElseThrow(
                                () -> new RuntimeException("Something went wrong !")
                        )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteView> findRouteById(@PathVariable long id) {
        return ResponseEntity.ok(
                Stream.of(routeService.findRouteById(id))
                        .map(viewMapperUtil::toRoadView)
                        .findFirst()
                        .orElseThrow(
                                () -> new RuntimeException("Route with id=" + id + " not found")
                        ));
    }

    @PutMapping
    public ResponseEntity<RouteView> updateRoute(@RequestBody @Valid RouteDto routeDto) {
        return ResponseEntity.ok(
                Stream.of(routeService.updateRoute(routeDto))
                        .map(viewMapperUtil::toRoadView)
                        .findFirst()
                        .orElseThrow(
                                () -> new RuntimeException("Something went wrong !")
                        )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRouteById(@PathVariable long id) {
        boolean result = routeService.deleteRouteById(id);

        return result
                ? ResponseEntity.ok("Route with id " + id + " was deleted")
                : ResponseEntity.badRequest().body("Something went wrong !");
    }

    @GetMapping
    public ResponseEntity<List<RouteView>> findAllRoutes() {
        return ResponseEntity.ok(
                routeService.findAllRoutes().stream()
                        .map(viewMapperUtil::toRoadView)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/all_without_transport")
    public ResponseEntity<List<RouteView>> findAllRoutesWithoutTransport() {
        return ResponseEntity.ok(
                routeService.findAllRoutesWithoutTransport().stream()
                        .map(viewMapperUtil::toRoadView)
                        .collect(Collectors.toList())
        );
    }
}
