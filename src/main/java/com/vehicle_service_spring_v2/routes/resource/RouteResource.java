package com.vehicle_service_spring_v2.routes.resource;


import com.vehicle_service_spring_v2.routes.RouteServiceI;
import com.vehicle_service_spring_v2.routes.model.dto.RouteDto;
import com.vehicle_service_spring_v2.routes.model.dto.RouteView;
import com.vehicle_service_spring_v2.utils.ViewMapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Tag(
        name = "REST API for Routes",
        description = "Provides resource methods for managing routes"
)
@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteResource {
    private final RouteServiceI routeService;
    private final ViewMapperUtil viewMapperUtil;

    @Operation(
            summary = "Create a new route",
            description = "It is used to create new route",
            parameters = {
                    @Parameter(
                            name = "routeDto",
                            description = "RouteDto object",
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "201", description = "Route is created"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
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

    @Operation(
            summary = "Find route by id",
            description = "It is used to find existed route",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "Route`s id",
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Route is found"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
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

    @Operation(
            summary = "Update existed route by id",
            description = "It is used to update existed route",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "route`s id which you want to update",
                            required = true
                    ),
                    @Parameter(
                            name = "routeDto",
                            description = "Route object with new fields which you want to update",
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Route updated"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<RouteView> updateRoute(
            @PathVariable long id,
            @RequestBody @Valid RouteDto routeDto
    ) {
        return ResponseEntity.ok(
                Stream.of(routeService.updateRoute(id, routeDto))
                        .map(viewMapperUtil::toRoadView)
                        .findFirst()
                        .orElseThrow(
                                () -> new RuntimeException("Something went wrong !")
                        )
        );
    }

    @Operation(
            summary = "Delete existed route by id",
            description = "It is used to delete existed route",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "route`s id which you want to update",
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Route is deleted"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRouteById(@PathVariable long id) {
        boolean result = routeService.deleteRouteById(id);

        return result
                ? ResponseEntity.ok("Route with id " + id + " was deleted")
                : ResponseEntity.badRequest().body("Something went wrong !");
    }

    @Operation(
            summary = "Find all existed routes",
            description = "It is used to find all existed routes",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Routes are found"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @GetMapping
    public ResponseEntity<List<RouteView>> findAllRoutes() {
        return ResponseEntity.ok(
                routeService.findAllRoutes().stream()
                        .map(viewMapperUtil::toRoadView)
                        .collect(Collectors.toList())
        );
    }

    @Operation(
            summary = "Find all routes without transports",
            description = "It is used to find all routes without transports",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Routes are found"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @GetMapping("/all_without_transport")
    public ResponseEntity<List<RouteView>> findAllRoutesWithoutTransport() {
        return ResponseEntity.ok(
                routeService.findAllRoutesWithoutTransport().stream()
                        .map(viewMapperUtil::toRoadView)
                        .collect(Collectors.toList())
        );
    }
}
