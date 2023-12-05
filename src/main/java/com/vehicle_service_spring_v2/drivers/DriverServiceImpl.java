package com.vehicle_service_spring_v2.drivers;

import com.vehicle_service_spring_v2.drivers.model.dto.DriverDto;
import com.vehicle_service_spring_v2.drivers.model.dto.DriverDtoHandler;
import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.routes.model.Route;
import com.vehicle_service_spring_v2.routes.RouteRepoI;
import com.vehicle_service_spring_v2.transports.TransportDto;
import com.vehicle_service_spring_v2.transports.TransportDtoHandler;
import com.vehicle_service_spring_v2.transports.TransportRepoI;
import com.vehicle_service_spring_v2.transports.TransportServiceI;
import com.vehicle_service_spring_v2.transports.model.Transport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@Transactional
public class DriverServiceImpl implements DriverServiceI {

    private final DriverRepoI driverRepo;
    private final TransportRepoI transportRepo;
    private final RouteRepoI routeRepo;
    private final TransportServiceI transportService;

    @Autowired
    public DriverServiceImpl(DriverRepoI driverRepo, TransportRepoI transportRepo, RouteRepoI routeRepo, TransportServiceI transportService) {
        this.driverRepo = driverRepo;
        this.transportRepo = transportRepo;
        this.routeRepo = routeRepo;
        this.transportService = transportService;
    }


    @Override
    public Driver addDriver(DriverDto driverDto) {
        Driver driver = DriverDtoHandler.mappingDtoToDriverMethodAdd(driverDto);
        log.info("Driver was added to db: " + driver);

        return driverRepo.save(driver);
    }

    @Override
    public Optional<Driver> findDriverById(Long id) {
        if (driverRepo.findById(id).isEmpty()) {
            log.warn("Error, driver with id = " + id + " not found");
            return Optional.empty();
        }

        return driverRepo.findById(id);
    }

    @Override
    public Driver updateDriver(DriverDto driverDto) {
        Optional<Driver> driver = driverRepo.findById(driverDto.getId());
        Driver upgradeDriver = DriverDtoHandler.mappingDtoToDriverMethodUpdate(driverDto, driver);
        log.info("Driver successfully updated " + upgradeDriver);

        return driverRepo.save(upgradeDriver);
    }

    @Override
    public boolean deleteDriverById(Long id) {
        Optional<Driver> foundDriver = driverRepo.findById(id);

        if (foundDriver.isEmpty()) {
            log.warn("Error, driver with id = " + id + " not found");
            return false;
        }

        if (!foundDriver.get().getTransport().isEmpty()) {
            log.warn("This driver can`t be deleted, driver is assigned to the transport = " + foundDriver.get());
            return false;
        }

        log.info("Driver : " + foundDriver + " was deleted");
        driverRepo.deleteById(id);
        return true;
    }

    @Override
    public boolean addDriverOnTransport(long driverId, long transportId) {
        Optional<Driver> driver = driverRepo.findById(driverId);
        Optional<Transport> transport = transportRepo.findById(transportId);

        if (driver.isEmpty() || transport.isEmpty()) {
            log.warn("Driver or transport is null !");
            return false;
        }

        transport.get().getDrivers().add(driver.get());
        TransportDto transportDto = TransportDtoHandler.createTransportDto(transport.get());

        transportService.updateTransport(transportDto);
        log.info("Driver: " + driver.get() + " successful added to transport " + transport.get());
        return true;
    }

    @Override
    public List<Driver> findAllDriverBySurname(String surname) {
        List<Driver> driverList;
        driverList = driverRepo.findDriversBySurname(surname);

        log.info("Users list was successful prepared");
        return driverList;
    }

    @Override
    public Set<Driver> findAllDriverOnRoute(long routeId) {
        Optional<Route> foundRoute = routeRepo.findById(routeId);

        if (foundRoute.isEmpty()) {
            log.warn("Route with id= " + routeId + " not found");
            return Collections.emptySet();
        }

        return foundRoute.get().getDrivers();
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
