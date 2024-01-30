package com.vehicle_service_spring_v2.drivers.resouce;

import com.vehicle_service_spring_v2.drivers.DriverServiceI;
import com.vehicle_service_spring_v2.drivers.model.dto.DriverDto;
import com.vehicle_service_spring_v2.drivers.model.dto.DriverView;
import com.vehicle_service_spring_v2.transports.model.dto.view.TransportView;
import com.vehicle_service_spring_v2.utils.ViewMapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Tag(
        name = "REST API for Drivers",
        description = "Provides resource methods for managing drivers"
)
@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverResource {
    private final DriverServiceI driverService;
    private final ViewMapperUtil viewMapperUtil;

    @Operation(
            summary = "Create a new driver",
            description = "It is used to create new driver",
            parameters = {
                    @Parameter(
                            name = "driverDto",
                            description = "DriverDto object",
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
    public ResponseEntity<DriverView> createDriver(@RequestBody @Valid DriverDto driverDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Stream.of(driverService.addDriver(driverDto))
                        .map(viewMapperUtil::toDriverView)
                        .findFirst()
                        .orElseThrow(
                                () -> new RuntimeException("Something went wrong !")
                        )
        );
    }

    @Operation(
            summary = "Find driver by id",
            description = "It is used to find existed driver",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "Driver`s id",
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
    public ResponseEntity<DriverView> findDriverById(@PathVariable long id) {
        return ResponseEntity.ok(
                Stream.of(driverService.findDriverById(id))
                        .map(viewMapperUtil::toDriverView)
                        .findFirst()
                        .orElseThrow(
                                () -> new RuntimeException("Driver with id=" + id + " not found")
                        ));
    }

    @Operation(
            summary = "Update existed driver by id",
            description = "It is used to update existed driver",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "driver`s id which you want to update",
                            required = true
                    ),

                    @Parameter(
                            name = "driverDto",
                            description = "Driver object with new fields which you want to update",
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
    public ResponseEntity<DriverView> updateDriver(
            @PathVariable Long id,
            @RequestBody @Valid DriverDto driverDto) {
        return ResponseEntity.ok(
                Stream.of(driverService.updateDriver(id, driverDto))
                        .map(viewMapperUtil::toDriverView)
                        .findFirst()
                        .orElseThrow(
                                () -> new RuntimeException("Something went wrong !")
                        )
        );
    }

    @Operation(
            summary = "Delete existed driver by id",
            description = "It is used to delete existed driver",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "driver`s id which you want to update",
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
    public ResponseEntity<String> deleteDriverById(@PathVariable long id) {
        boolean result = driverService.deleteDriverById(id);

        return result
                ? ResponseEntity.ok("Driver with " + id + " was deleted")
                : ResponseEntity.badRequest().body("Failed to delete driver with id " + id);
    }

    @Operation(
            summary = "Add driver on transport",
            description = "It is used to add existed driver on transport",
            parameters = {
                    @Parameter(
                            name = "d_id",
                            description = "Driver`s id ",
                            required = true
                    ),

                    @Parameter(
                            name = "t_id",
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
    @PutMapping("/driver_to_transport/{d_id}/{t_id}")
    public ResponseEntity<String> addDriverOnTransport(@PathVariable long d_id, @PathVariable long t_id) {
        boolean result = driverService.addDriverOnTransport(d_id, t_id);

        return result
                ? ResponseEntity.ok("Driver with id " + d_id + " was successfully added on transport with id " + t_id)
                : ResponseEntity.badRequest().body("Something went wrong");
    }

    @Operation(
            summary = "Find all drivers by surname",
            description = "It is used to find all drivers by surname",
            parameters = {
                    @Parameter(
                            name = "surname",
                            description = "Driver`s surname",
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
    @GetMapping("/surname/{surname}")
    public ResponseEntity<List<DriverView>> findAllDriverBySurname(@PathVariable String surname) {
        return ResponseEntity.ok(driverService.findAllDriverBySurname(surname).stream()
                .map(viewMapperUtil::toDriverView)
                .collect(Collectors.toList()));
    }

    @Operation(
            summary = "Find all drivers on route",
            description = "It is used to find all drivers by route id",
            parameters = {
                    @Parameter(
                            name = "id",
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
    @GetMapping("/drivers_on_route/{id}")
    public ResponseEntity<Set<DriverView>> findAllDriverOnRoute(@PathVariable long id) {
        return ResponseEntity.ok(
                driverService.findAllDriverOnRoute(id).stream()
                        .map(viewMapperUtil::toDriverView)
                        .collect(Collectors.toSet())
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
    @GetMapping("/transport_without_driver")
    public ResponseEntity<List<TransportView>> findAllTransportsWithoutDriver() {
        return ResponseEntity.ok(
                driverService.findAllTransportsWithoutDriver().stream()
                        .map(viewMapperUtil::toTransportView)
                        .collect(Collectors.toList())
        );
    }

    @Operation(
            summary = "Find all drivers",
            description = "It is used to find all drivers"
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
    public ResponseEntity<List<DriverView>> findAllDrivers() {
        return ResponseEntity.ok(
                driverService.findAllDrivers().stream()
                        .map(viewMapperUtil::toDriverView)
                        .collect(Collectors.toList())
        );
    }
}
