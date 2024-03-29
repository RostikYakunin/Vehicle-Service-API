package com.vehicle_service_spring_v2.drivers;

import com.vehicle_service_spring_v2.drivers.model.dto.DriverDto;
import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.transports.model.Transport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface DriverServiceI {

    Driver addDriver(DriverDto driverDto);

    Driver findDriverById(Long id);

    Driver updateDriver(Long id, DriverDto driver);

    boolean deleteDriverById(Long id);

    boolean addDriverOnTransport(long driverId, long transportId);

    List<Driver> findAllDriverBySurname(String surname);

    Set<Driver> findAllDriverOnRoute(long routeId);

    List<Transport> findAllTransportsWithoutDriver();

    List<Driver> findAllDrivers();

    Page<Driver> getAllPages (Pageable pageable);
}
