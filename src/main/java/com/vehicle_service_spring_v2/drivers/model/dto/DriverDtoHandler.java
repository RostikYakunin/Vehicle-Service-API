package com.vehicle_service_spring_v2.drivers.model.dto;

import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.drivers.model.DriverQualificationEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class DriverDtoHandler {

    public static Driver mappingDtoToDriverMethodAdd(DriverDto driverDto) {
        Driver driver = new Driver();
        driver.setNameOfDriver(driverDto.getNameOfDriver());
        driver.setSurnameOfDriver(driverDto.getSurnameOfDriver());
        driver.setPhoneNumber(driverDto.getPhoneNumber());
        driver.setQualificationEnum(
                DriverQualificationEnum.valueOf(driverDto.getQualificationEnum().toUpperCase() + "_DRIVER")
        );
        log.info("New driver was mapped: " + driver);
        return driver;
    }

    public static Driver mappingDtoToDriverMethodUpdate(DriverDto driverDto, Optional<Driver> driver) {
        if (driverDto.getNameOfDriver() != null) {
            log.info("Driver`s name was updated from " + driver.get().getNameOfDriver() + " to " + driverDto.getNameOfDriver());
            driver.get().setNameOfDriver(driverDto.getNameOfDriver());
        }

        if (driverDto.getSurnameOfDriver() != null) {
            log.info("Driver`s surname was updated from " + driver.get().getSurnameOfDriver() + " to " + driverDto.getSurnameOfDriver());
            driver.get().setSurnameOfDriver(driverDto.getSurnameOfDriver());
        }

        if (driverDto.getPhoneNumber() != null) {
            log.info("Driver`s phone was updated from " + driver.get().getPhoneNumber() + " to " + driverDto.getPhoneNumber());
            driver.get().setPhoneNumber(driverDto.getPhoneNumber());
        }

        if (driverDto.getQualificationEnum() != null) {
            log.info("Driver`s name was updated from " + driver.get().getQualificationEnum() + " to " + driverDto.getQualificationEnum());
            driver.get().setQualificationEnum(DriverQualificationEnum.valueOf(driverDto.getQualificationEnum().toUpperCase() + "_DRIVER"));
        }

        if (!driverDto.getRoute().isEmpty()) {
            log.info("New route was added to driver");
            driver.get().getRoute().addAll(driverDto.getRoute());
        }

        if (!driverDto.getTransport().isEmpty()) {
            log.info("New transports was added to driver");
            driver.get().getTransport().addAll(driverDto.getTransport());
        }

        return driver.get();
    }

}
