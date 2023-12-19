package com.vehicle_service_spring_v2.drivers.resouce;

import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.drivers.DriverServiceI;
import com.vehicle_service_spring_v2.drivers.model.dto.DriverDto;
import com.vehicle_service_spring_v2.drivers.model.dto.ReturnedConverter;
import com.vehicle_service_spring_v2.drivers.model.dto.ReturnedDriver;
import com.vehicle_service_spring_v2.transports.ReturnedTransport;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {
    private final DriverServiceI driverService;

    @Autowired
    public DriverController(DriverServiceI driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    public ResponseEntity<ReturnedDriver> createDriver(@RequestBody @Valid DriverDto driverDto) {
        ReturnedDriver driver = ReturnedConverter.convertToReturnedDriver(driverService.addDriver(driverDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(driver);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReturnedDriver> findDriverById(@PathVariable long id) {
        Driver driver = driverService.findDriverById(id)
                .orElseThrow(() -> new RuntimeException("Driver with id = " + id + " not found")
                );

        ReturnedDriver returnedDriver = ReturnedConverter.convertToReturnedDriver(driver);
        return ResponseEntity.ok(returnedDriver);
    }

    @PutMapping
    public ResponseEntity<Driver> updateDriver(@RequestBody @Valid DriverDto driverDto) {
        Driver updateDriver = driverService.updateDriver(driverDto);
        return ResponseEntity.ok(updateDriver);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDriverById(@PathVariable long id) {
        driverService.deleteDriverById(id);
        return ResponseEntity.ok("Driver with " + id + " was deleted");
    }

    @PutMapping("/driver_to_transport/{d_id}/{t_id}")
    public ResponseEntity<String> addDriverOnTransport(@PathVariable long d_id, @PathVariable long t_id) {
        driverService.addDriverOnTransport(d_id, t_id);
        return ResponseEntity.ok("Driver with id " + d_id + " was successfully added on transport with id " + t_id);
    }

    @GetMapping("/surname/{surname}")
    public ResponseEntity<List<ReturnedDriver>> findAllDriverBySurname(@PathVariable String surname) {
        List<ReturnedDriver> drivers = driverService.findAllDriverBySurname(surname).stream()
                .map(ReturnedConverter::convertToReturnedDriver)
                .collect(Collectors.toList());
        return ResponseEntity.ok(drivers);
    }

    @GetMapping("/drivers_on_route/{id}")
    public ResponseEntity<Set<ReturnedDriver>> findAllDriverOnRoute(@PathVariable long id) {
        Set<ReturnedDriver> drivers = driverService.findAllDriverOnRoute(id).stream()
                .map(ReturnedConverter::convertToReturnedDriver)
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

    @GetMapping()
    public ResponseEntity<List<ReturnedDriver>> findAllDrivers() {
        List<ReturnedDriver> dr = driverService.findAllDrivers().stream()
                .map(ReturnedConverter::convertToReturnedDriver).toList();
        return ResponseEntity.ok(new ArrayList<>(dr));
    }
}
