package com.vehicle_service_spring_v2.drivers;

import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.drivers.model.dto.DriverDto;
import com.vehicle_service_spring_v2.drivers.model.dto.DriverDtoMapper;
import com.vehicle_service_spring_v2.routes.RouteRepoI;
import com.vehicle_service_spring_v2.routes.model.Route;
import com.vehicle_service_spring_v2.transports.TransportRepoI;
import com.vehicle_service_spring_v2.transports.TransportServiceI;
import com.vehicle_service_spring_v2.transports.model.Transport;
import com.vehicle_service_spring_v2.transports.model.dto.TransportDto;
import com.vehicle_service_spring_v2.transports.model.dto.TransportDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverServiceI {
    private final DriverRepoI driverRepo;
    private final TransportRepoI transportRepo;
    private final RouteRepoI routeRepo;
    private final TransportServiceI transportService;
    private final DriverDtoMapper driverDtoMapper;
    private final TransportDtoMapper transportDtoMapper;

    @Override
    public Driver addDriver(DriverDto driverDto) {
        Driver driver = driverDtoMapper.toDriver(driverDto);
        log.info("Driver was added to db: " + driver);

        return driverRepo.save(driver);
    }

    @Override
    public Driver findDriverById(Long id) {
        return driverRepo.findById(id)
                .filter(x -> driverRepo.existsById(id))
                .orElseThrow(
                        () -> {
                            log.warn("Error, driver with id = " + id + " not found");
                            throw new RuntimeException("Driver with id=" + id + " not found !");
                        }
                );
    }

    @Override
    public Driver updateDriver(DriverDto driverDto) {
        Driver upgradeDriver = driverDtoMapper.toDriver(driverDto);
        log.info("Driver successfully updated " + upgradeDriver);

        return driverRepo.save(upgradeDriver);
    }

    @Override
    public boolean deleteDriverById(Long id) {
        Driver foundDriver = driverRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Error, driver with id = " + id + " not found")
        );

        boolean isEmpty = foundDriver.getTransport().isEmpty();
        if (!isEmpty) {
            log.warn("This driver can`t be deleted, driver is assigned to the transport = " + foundDriver);
            return false;
        }

        log.info("Driver : " + foundDriver + " was deleted");
        driverRepo.deleteById(id);
        return true;
    }

    @Override
    public boolean addDriverOnTransport(long driverId, long transportId) {
        Driver driver = driverRepo.findById(driverId).orElseThrow(
                () -> new RuntimeException("Driver with id=" + driverId + " not found !")
        );

        Transport transport = transportRepo.findById(transportId).orElseThrow(
                () -> new RuntimeException("Transport with id=" + transportId + " not found !")
        );

        transport.getDrivers().add(driver);
        TransportDto transportDto = transportDtoMapper.toDto(transport);

        transportService.updateTransport(transportDto);
        log.info("Driver: " + driver + " successful added to transport " + transport);
        return true;
    }

    @Override
    public List<Driver> findAllDriverBySurname(String surname) {
        log.info("Users list was successful prepared");
        return driverRepo.findDriversBySurname(surname);
    }

    @Override
    public Set<Driver> findAllDriverOnRoute(long routeId) {
        return routeRepo.findById(routeId)
                .map(Route::getDrivers)
                .orElseGet(() -> {
                    log.warn("Route with id= " + routeId + " not found");
                    return Collections.emptySet();
                });
    }

    @Override
    public List<Transport> findAllTransportsWithoutDriver() {
        return transportRepo.findTransportWithoutDriver();
    }

    @Override
    public List<Driver> findAllDrivers() {
        return driverRepo.findAll();
    }

    @Override
    public Page<Driver> getAllPages(Pageable pageable) {
        return driverRepo.findAll(pageable);
    }
}
