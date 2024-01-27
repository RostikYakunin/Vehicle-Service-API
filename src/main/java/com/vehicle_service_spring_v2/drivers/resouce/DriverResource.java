package com.vehicle_service_spring_v2.drivers.resouce;

import com.vehicle_service_spring_v2.drivers.DriverServiceI;
import com.vehicle_service_spring_v2.drivers.model.dto.DriverDto;
import com.vehicle_service_spring_v2.drivers.model.dto.DriverView;
import com.vehicle_service_spring_v2.transports.model.dto.TransportView;
import com.vehicle_service_spring_v2.utils.ViewMapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverResource {
    private final DriverServiceI driverService;
    private final ViewMapperUtil viewMapperUtil;

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

    @PutMapping
    public ResponseEntity<DriverView> updateDriver(@RequestBody @Valid DriverDto driverDto) {
        return ResponseEntity.ok(
                Stream.of(driverService.updateDriver(driverDto))
                        .map(viewMapperUtil::toDriverView)
                        .findFirst()
                        .orElseThrow(
                                () -> new RuntimeException("Something went wrong !")
                        )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDriverById(@PathVariable long id) {
        boolean result = driverService.deleteDriverById(id);

        return result
                ? ResponseEntity.ok("Driver with " + id + " was deleted")
                : ResponseEntity.badRequest().body("Failed to delete driver with id " + id);
    }

    @PutMapping("/driver_to_transport/{d_id}/{t_id}")
    public ResponseEntity<String> addDriverOnTransport(@PathVariable long d_id, @PathVariable long t_id) {
        boolean result = driverService.addDriverOnTransport(d_id, t_id);

        return result
                ? ResponseEntity.ok("Driver with id " + d_id + " was successfully added on transport with id " + t_id)
                : ResponseEntity.badRequest().body("Something went wrong");
    }

    @GetMapping("/surname/{surname}")
    public ResponseEntity<List<DriverView>> findAllDriverBySurname(@PathVariable String surname) {
        return ResponseEntity.ok(driverService.findAllDriverBySurname(surname).stream()
                .map(viewMapperUtil::toDriverView)
                .collect(Collectors.toList()));
    }

    @GetMapping("/drivers_on_route/{id}")
    public ResponseEntity<Set<DriverView>> findAllDriverOnRoute(@PathVariable long id) {
        return ResponseEntity.ok(
                driverService.findAllDriverOnRoute(id).stream()
                        .map(viewMapperUtil::toDriverView)
                        .collect(Collectors.toSet())
        );
    }

    @GetMapping("/transport_without_driver")
    public ResponseEntity<List<TransportView>> findAllTransportsWithoutDriver() {
        return ResponseEntity.ok(
                driverService.findAllTransportsWithoutDriver().stream()
                        .map(viewMapperUtil::toTransportView)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping
    public ResponseEntity<List<DriverView>> findAllDrivers() {
        return ResponseEntity.ok(
                driverService.findAllDrivers().stream()
                        .map(viewMapperUtil::toDriverView)
                        .collect(Collectors.toList())
        );
    }
}
