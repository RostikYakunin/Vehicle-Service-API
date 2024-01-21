package com.vehicle_service_spring_v2.transport.model.dto;

import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.drivers.model.DriverQualificationEnum;
import com.vehicle_service_spring_v2.routes.model.Route;
import com.vehicle_service_spring_v2.transports.model.Bus;
import com.vehicle_service_spring_v2.transports.model.Tram;
import com.vehicle_service_spring_v2.transports.model.dto.BusView;
import com.vehicle_service_spring_v2.transports.model.dto.TramView;
import com.vehicle_service_spring_v2.transports.model.dto.TransportViewMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TransportViewMapperTest {
    @Autowired
    TransportViewMapper transportViewMapper;

    @Test
    void toBusView() {
        //Given
        Bus test = Bus.builder()
                .id(1L)
                .brandOfTransport("testBrand")
                .amountOfPassengers(2)
                .driverQualificationEnum(DriverQualificationEnum.BUS_DRIVER)
                .type("testType")
                .amountOfDoors(2)
                .drivers(Set.of(Driver.builder()
                        .id(1L)
                        .build()))
                .route(Set.of(Route.builder()
                        .id(1L)
                        .build()))
                .build();

        BusView expected = BusView.builder()
                .id(1L)
                .brandOfTransport("testBrand")
                .amountOfPassengers(2)
                .driverQualificationEnum(DriverQualificationEnum.BUS_DRIVER.name())
                .amountOfDoors(2)
                .type("testType")
                .driversId(Set.of(1L))
                .routesId(Set.of(1L))
                .build();

        //When
        BusView actualResult = transportViewMapper.toBusView(test);

        //Then
        assertEquals(expected, actualResult);
    }

    @Test
    void toTramView() {
        // Given
        Tram testTram = Tram.builder()
                .id(1L)
                .brandOfTransport("testBrand")
                .amountOfPassengers(2)
                .driverQualificationEnum(DriverQualificationEnum.TRAM_DRIVER)
                .amountOfRailcar(2)
                .drivers(Set.of(Driver.builder()
                        .id(1L)
                        .build()))
                .route(Set.of(Route.builder()
                        .id(1L)
                        .build()))
                .build();

        TramView expected = TramView.builder()
                .id(1L)
                .brandOfTransport("testBrand")
                .amountOfPassengers(2)
                .driverQualificationEnum(DriverQualificationEnum.TRAM_DRIVER.name())
                .amountOfRailcar(2)
                .driversId(Set.of(1L))
                .routesId(Set.of(1L))
                .build();

        // When
        TramView actualResult = transportViewMapper.toTramView(testTram);

        // Then
        assertEquals(expected, actualResult);
    }
}