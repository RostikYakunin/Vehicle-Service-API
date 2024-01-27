package com.vehicle_service_spring_v3.drivers.resouce;

import com.vehicle_service_spring_v3.drivers.DriverServiceI;
import com.vehicle_service_spring_v3.drivers.model.Driver;
import com.vehicle_service_spring_v3.drivers.model.dto.DriverDto;
import com.vehicle_service_spring_v3.drivers.model.dto.DriverView;
import com.vehicle_service_spring_v3.drivers.model.dto.DriverViewMapper;
import com.vehicle_service_spring_v3.drivers.model.dto.ReturnedConverter;
import com.vehicle_service_spring_v3.transports.ReturnedTransport;
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

    @PostMapping("/")
    public ResponseEntity<DriverView> createDriver(@RequestBody @Valid DriverDto driverDto) {
        DriverView driver = Stream.of(driverService.addDriver(driverDto))
                .map(driverViewMapper::driverToDriverView)
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

        return ResponseEntity.ok(driverViewMapper.driverToDriverView(driver));
    }

    @PutMapping("/")
    public ResponseEntity<DriverView> updateDriver(@RequestBody @Valid DriverDto driverDto) {
        Driver updateDriver = driverService.updateDriver(driverDto);

        return ResponseEntity.ok(driverViewMapper.driverToDriverView(updateDriver));
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
                .map(driverViewMapper::driverToDriverView)
                .collect(Collectors.toList());

        return ResponseEntity.ok(drivers);
    }

    @GetMapping("/drivers_on_route/{id}")
    public ResponseEntity<Set<DriverView>> findAllDriverOnRoute(@PathVariable long id) {
        Set<DriverView> drivers = driverService.findAllDriverOnRoute(id).stream()
                .map(driverViewMapper::driverToDriverView)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(drivers);
    }

    @GetMapping("/transport_without_driver")
    public ResponseEntity<List<ReturnedTransport>> findAllTransportsWithoutDriver() {
        List<ReturnedTransport> transports = driverService.findAllTransportsWithoutDriver().stream()
                .map(ReturnedConverter::convertToReturnedTransport)
                .collect(Collectors.toList());

        return ResponseEntity.ok(transports);
    }

    @GetMapping("/")
    public ResponseEntity<List<DriverView>> findAllDrivers() {
        List<DriverView> dr = driverService.findAllDrivers().stream()
                .map(driverViewMapper::driverToDriverView)
                .toList();

        return ResponseEntity.ok(new ArrayList<>(dr));
    }
}
