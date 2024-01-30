package com.vehicle_service_spring_v2.transports.resource;

import com.vehicle_service_spring_v2.transports.TransportServiceI;
import com.vehicle_service_spring_v2.transports.model.dto.TransportDto;
import com.vehicle_service_spring_v2.transports.model.dto.view.TransportView;
import com.vehicle_service_spring_v2.utils.ViewMapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Tag(
        name = "REST API for Transports",
        description = "Provides resource methods for managing transports"
)
@RestController
@RequestMapping("/api/transports")
@RequiredArgsConstructor
public class TransportResource {
    private final TransportServiceI transportService;
    private final ViewMapperUtil viewMapperUtil;

    @Operation(
            summary = "Create a new transport",
            description = "It is used to create new transport",
            parameters = {
                    @Parameter(
                            name = "transportDto",
                            description = "transportDto object",
                            required = true
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status 200 SUCCESS"
            ),

            @ApiResponse(
                    responseCode = "403",
                    description = "HTTP Status 403 FORBIDDEN"
            ),

            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status 404 NOT_FOUND"
            )
    })
    @PostMapping
    public ResponseEntity<TransportView> createTransport(@RequestBody TransportDto transportDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Stream.of(transportService.addTransport(transportDto))
                        .map(viewMapperUtil::toTransportView)
                        .findFirst()
                        .orElseThrow(
                                () -> new RuntimeException("Something went wrong !")
                        )
        );
    }

    @Operation(
            summary = "Find transport by id",
            description = "It is used to find existed transport",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "Transport`s id",
                            required = true
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status 200 SUCCESS"
            ),

            @ApiResponse(
                    responseCode = "403",
                    description = "HTTP Status 403 FORBIDDEN"
            ),

            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status 404 NOT_FOUND"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<TransportView> findTransportById(@PathVariable long id) {
        return ResponseEntity.ok(
                Stream.of(transportService.findTransportById(id))
                        .map(viewMapperUtil::toTransportView)
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Transport with " + id + " not found")));
    }

    @Operation(
            summary = "Update existed transport by id",
            description = "It is used to update existed transport",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "Transport`s id which you want to update",
                            required = true
                    ),

                    @Parameter(
                            name = "transportDto",
                            description = "transport object with new fields which you want to update",
                            required = true
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status 200 SUCCESS"
            ),

            @ApiResponse(
                    responseCode = "403",
                    description = "HTTP Status 403 FORBIDDEN"
            ),

            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status 404 NOT_FOUND"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<TransportView> updateTransport(
            @PathVariable Long id,
            @RequestBody TransportDto transportDto) {
        return ResponseEntity.ok(
                Stream.of(transportService.updateTransport(id, transportDto))
                        .map(viewMapperUtil::toTransportView)
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Something went wrong!"))
        );
    }

    @Operation(
            summary = "Delete existed transport by id",
            description = "It is used to delete existed route",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "Transport`s id which you want to update",
                            required = true
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status 200 SUCCESS"
            ),

            @ApiResponse(
                    responseCode = "403",
                    description = "HTTP Status 403 FORBIDDEN"
            ),

            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status 404 NOT_FOUND"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransportById(@PathVariable long id) {
        transportService.deleteTransportById(id);
        return ResponseEntity.ok("Transport with id " + id + " was deleted");
    }

    @Operation(
            summary = "Find all existed transports",
            description = "It is used to find all existed transports"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status 200 SUCCESS"
            ),

            @ApiResponse(
                    responseCode = "403",
                    description = "HTTP Status 403 FORBIDDEN"
            ),

            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status 404 NOT_FOUND"
            )
    })
    @GetMapping
    public ResponseEntity<List<TransportView>> findAllTransports() {
        return ResponseEntity.ok(
                transportService.findAllTransports().stream()
                        .map(viewMapperUtil::toTransportView)
                        .collect(Collectors.toList())
        );
    }

    @Operation(
            summary = "Find all transports by brand",
            description = "It is used to find all transports by brand",
            parameters = {
                    @Parameter(
                            name = "brand",
                            description = "Transport`s brand",
                            required = true
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status 200 SUCCESS"
            ),

            @ApiResponse(
                    responseCode = "403",
                    description = "HTTP Status 403 FORBIDDEN"
            ),

            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status 404 NOT_FOUND"
            )
    })
    @GetMapping("/by_brand/{brand}")
    public ResponseEntity<List<TransportView>> findTransportByBrand(@PathVariable String brand) {
        return ResponseEntity.ok(
                transportService.findTransportByBrand(brand).stream()
                        .map(viewMapperUtil::toTransportView)
                        .collect(Collectors.toList())
        );
    }

    @Operation(
            summary = "Find all transports without drivers",
            description = "It is used to find all transports without any drivers"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status 200 SUCCESS"
            ),

            @ApiResponse(
                    responseCode = "403",
                    description = "HTTP Status 403 FORBIDDEN"
            ),

            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status 404 NOT_FOUND"
            )
    })
    @GetMapping("/without_driver")
    public ResponseEntity<List<TransportView>> findTransportWithoutDriver() {
        return ResponseEntity.ok(
                transportService.findTransportWithoutDriver().stream()
                        .map(viewMapperUtil::toTransportView)
                        .collect(Collectors.toList())
        );
    }

    @Operation(
            summary = "Add transport on route",
            description = "It is used to add existed transport on route",
            parameters = {
                    @Parameter(
                            name = "transportId",
                            description = "Transport`s id ",
                            required = true
                    ),

                    @Parameter(
                            name = "routeId",
                            description = "Route`s id",
                            required = true
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status 200 SUCCESS"
            ),

            @ApiResponse(
                    responseCode = "403",
                    description = "HTTP Status 403 FORBIDDEN"
            ),

            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status 404 NOT_FOUND"
            )
    })
    @PutMapping("/transport_to_route/{transportId}/{routeId}")
    public ResponseEntity<?> addTransportToRoute(@PathVariable long transportId, @PathVariable long routeId) {
        boolean result = transportService.addTransportToRoute(transportId, routeId);

        return result
                ? ResponseEntity.ok("Transport with id " + transportId + " was added to route with id " + routeId)
                : ResponseEntity.badRequest().body("Something went wrong!");
    }

    @Operation(
            summary = "Delete transport from route",
            description = "It is used to delete existed transport from route",
            parameters = {
                    @Parameter(
                            name = "transportId",
                            description = "Transport`s id ",
                            required = true
                    ),

                    @Parameter(
                            name = "routeId",
                            description = "Route`s id",
                            required = true
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status 200 SUCCESS"
            ),

            @ApiResponse(
                    responseCode = "403",
                    description = "HTTP Status 403 FORBIDDEN"
            ),

            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status 404 NOT_FOUND"
            )
    })
    @DeleteMapping("/transport_from_route/{transportId}/{routeId}")
    public ResponseEntity<?> removeTransportFromRoute(@PathVariable long transportId, @PathVariable long routeId) {
        boolean result = transportService.removeTransportFromRoute(transportId, routeId);

        return result
                ? ResponseEntity.ok("Transport with id " + transportId + " was deleted from route with id " + routeId)
                : ResponseEntity.badRequest().body("Something went wrong !");
    }
}
