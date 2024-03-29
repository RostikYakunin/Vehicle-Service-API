package com.vehicle_service_spring_v2.transport.model.dto;

import com.vehicle_service_spring_v2.drivers.model.DriverQualificationEnum;
import com.vehicle_service_spring_v2.transports.model.Bus;
import com.vehicle_service_spring_v2.transports.model.Tram;
import com.vehicle_service_spring_v2.transports.model.dto.TransportDto;
import com.vehicle_service_spring_v2.transports.model.dto.TransportDtoMapper;
import com.vehicle_service_spring_v2.transports.model.dto.TransportDtoMapperImpl;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransportDtoMapperTest {
    TransportDtoMapper transportDtoMapper = new TransportDtoMapperImpl();

    @Test
    void mapToBusType() {
        //Given
        TransportDto transportDto = TransportDto.builder()
                .id(1L)
                .brandOfTransport("testBrand")
                .amountOfPassengers(2)
                .driverQualificationEnum("BUS")
                .type("testType")
                .amountOfDoors(2)
                .amountOfRailcar(2)
                .build();

        Bus expected = Bus.builder()
                .id(1L)
                .brandOfTransport("testBrand")
                .amountOfPassengers(2)
                .driverQualificationEnum(DriverQualificationEnum.BUS_DRIVER)
                .type("testType")
                .amountOfDoors(2)
                .drivers(Collections.emptySet())
                .route(Collections.emptySet())
                .build();

        //When
        Bus actual = (Bus) transportDtoMapper.toTransport(transportDto);

        //Then
        assertEquals(actual.getClass(), Bus.class);
        assertEquals(expected, actual);
    }

    @Test
    void mapToTramType() {
        //Given
        TransportDto transportDto = TransportDto.builder()
                .id(1L)
                .brandOfTransport("testBrand")
                .amountOfPassengers(2)
                .driverQualificationEnum("TRAM")
                .type("testType")
                .amountOfDoors(2)
                .amountOfRailcar(2)
                .build();

        Tram expected = Tram.builder()
                .id(1L)
                .brandOfTransport("testBrand")
                .amountOfPassengers(2)
                .driverQualificationEnum(DriverQualificationEnum.TRAM_DRIVER)
                .amountOfRailcar(2)
                .drivers(Collections.emptySet())
                .route(Collections.emptySet())
                .build();

        //When
        Tram actual = (Tram) transportDtoMapper.toTransport(transportDto);

        //Then
        assertEquals(actual.getClass(), Tram.class);
        assertEquals(expected, actual);
    }

    @Test
    void updateVehicle() {
        //Given
        TransportDto testDto = TransportDto.builder()
                .id(1L)
                .brandOfTransport("changed")
                .amountOfPassengers(2)
                .driverQualificationEnum("TRAM")
                .type("testType")
                .amountOfDoors(null)
                .amountOfRailcar(null)
                .build();

        Tram testTram = Tram.builder()
                .id(1L)
                .brandOfTransport("testBrand")
                .amountOfPassengers(2)
                .driverQualificationEnum(DriverQualificationEnum.TRAM_DRIVER)
                .amountOfRailcar(2)
                .drivers(Collections.emptySet())
                .route(Collections.emptySet())
                .build();

        Tram expectedTram = Tram.builder()
                .id(1L)
                .brandOfTransport("changed")
                .amountOfPassengers(2)
                .driverQualificationEnum(DriverQualificationEnum.TRAM_DRIVER)
                .amountOfRailcar(2)
                .drivers(Collections.emptySet())
                .route(Collections.emptySet())
                .build();

        //When
        Tram actual = (Tram) transportDtoMapper.updateVehicle(testTram, testDto);

        //Then
        assertEquals(actual.getClass(), Tram.class);
        assertEquals(testTram, actual);
    }
}