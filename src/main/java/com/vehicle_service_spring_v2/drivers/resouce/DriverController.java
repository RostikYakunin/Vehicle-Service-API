package com.vehicle_service_spring_v2.drivers.resouce;

import com.vehicle_service_spring_v2.drivers.DriverServiceI;
import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.drivers.model.dto.DriverDto;
import com.vehicle_service_spring_v2.drivers.model.dto.DriverView;
import com.vehicle_service_spring_v2.drivers.model.dto.DriverViewMapper;
import com.vehicle_service_spring_v2.transports.model.dto.views.TransportView;
import com.vehicle_service_spring_v2.transports.model.dto.views.mappers.BusViewMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverController {
    private final DriverServiceI driverService;
    private final DriverViewMapper driverViewMapper;
    private final BusViewMapper transportViewMapper;

    @PostMapping("/")
    public ResponseEntity<DriverView> createDriver(@RequestBody @Valid DriverDto driverDto) {
        DriverView driver = Stream.of(driverService.addDriver(driverDto))
                .map(driverViewMapper::toDriverView)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong"));

        return ResponseEntity.status(HttpStatus.CREATED).body(driver);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverView> findDriverById(@PathVariable long id) {
        Driver driver = driverService.findDriverById(id)
                .orElseThrow(
                        () -> new RuntimeException("Driver with id = " + id + " not found")
                );

        return ResponseEntity.ok(driverViewMapper.toDriverView(driver));
    }

    @PutMapping("/")
    public ResponseEntity<DriverView> updateDriver(@RequestBody @Valid DriverDto driverDto) {
        Driver updateDriver = driverService.updateDriver(driverDto);

        return ResponseEntity.ok(driverViewMapper.toDriverView(updateDriver));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDriverById(@PathVariable long id) {
        boolean result = driverService.deleteDriverById(id);

        return result ? (ResponseEntity.ok("Driver with " + id + " was deleted"))
                : ResponseEntity.badRequest().body("Failed to delete driver with id " + id);
    }

    @PutMapping("/driver_to_transport/{d_id}/{t_id}")
    public ResponseEntity<String> addDriverOnTransport(@PathVariable long d_id, @PathVariable long t_id) {
        driverService.addDriverOnTransport(d_id, t_id);

        return ResponseEntity.ok("Driver with id " + d_id + " was successfully added on transport with id " + t_id);
    }

    @GetMapping("/surname/{surname}")
    public ResponseEntity<List<DriverView>> findAllDriverBySurname(@PathVariable String surname) {
        List<DriverView> drivers = driverService.findAllDriverBySurname(surname).stream()
                .map(driverViewMapper::toDriverView)
                .collect(Collectors.toList());

        return ResponseEntity.ok(drivers);
    }

    @GetMapping("/drivers_on_route/{id}")
    public ResponseEntity<Set<DriverView>> findAllDriverOnRoute(@PathVariable long id) {
        Set<DriverView> drivers = driverService.findAllDriverOnRoute(id).stream()
                .map(driverViewMapper::toDriverView)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(drivers);
    }

    @GetMapping("/transport_without_driver")
    public ResponseEntity<List<TransportView>> findAllTransportsWithoutDriver() {
        List<TransportView> transports = driverService.findAllTransportsWithoutDriver().stream()
                .map(transportViewMapper::transportToTransportView)
                .collect(Collectors.toList());

        return ResponseEntity.ok(transports);
    }

    @GetMapping("/")
    public ResponseEntity<List<DriverView>> findAllDrivers() {
        List<DriverView> dr = driverService.findAllDrivers().stream()
                .map(driverViewMapper::toDriverView)
                .toList();

        return ResponseEntity.ok(new ArrayList<>(dr));
    }
}
