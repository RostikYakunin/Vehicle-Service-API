package com.vehicle_service_spring_v2.transport.model.dto;

import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.drivers.model.DriverQualificationEnum;
import com.vehicle_service_spring_v2.routes.model.Route;
import com.vehicle_service_spring_v2.transports.model.Bus;
import com.vehicle_service_spring_v2.transports.model.Tram;
import com.vehicle_service_spring_v2.transports.model.dto.view.BusView;
import com.vehicle_service_spring_v2.transports.model.dto.view.TramView;
import com.vehicle_service_spring_v2.transports.model.dto.view.TransportView;
import com.vehicle_service_spring_v2.transports.model.dto.view.TransportViewMapper;
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

    @Test
    void toTransportViewFromTramView() {
        // Given
        TransportView expected = TransportView.builder()
                .id(1L)
                .brandOfTransport("testBrand")
                .amountOfPassengers(2)
                .driverQualificationEnum(DriverQualificationEnum.TRAM_DRIVER.name())
                .amountOfRailcar(2)
                .amountOfDoors(null)
                .type(null)
                .driversId(Set.of(1L))
                .routesId(Set.of(1L))
                .build();

        TramView tested = TramView.builder()
                .id(1L)
                .brandOfTransport("testBrand")
                .amountOfPassengers(2)
                .driverQualificationEnum(DriverQualificationEnum.TRAM_DRIVER.name())
                .amountOfRailcar(2)
                .driversId(Set.of(1L))
                .routesId(Set.of(1L))
                .build();

        // When
        TransportView actualResult = transportViewMapper.toView(tested);

        // Then
        assertEquals(expected, actualResult);
    }

    @Test
    void toTransportViewFromBusView() {
        // Given
        TransportView expected = TransportView.builder()
                .id(1L)
                .brandOfTransport("testBrand")
                .amountOfPassengers(2)
                .driverQualificationEnum(DriverQualificationEnum.TRAM_DRIVER.name())
                .amountOfRailcar(null)
                .amountOfDoors(2)
                .type("testType")
                .driversId(Set.of(1L))
                .routesId(Set.of(1L))
                .build();

        BusView tested = BusView.builder()
                .id(1L)
                .brandOfTransport("testBrand")
                .amountOfPassengers(2)
                .driverQualificationEnum(DriverQualificationEnum.TRAM_DRIVER.name())
                .type("testType")
                .amountOfDoors(2)
                .driversId(Set.of(1L))
                .routesId(Set.of(1L))
                .build();

        // When
        TransportView actualResult = transportViewMapper.toView(tested);

        // Then
        assertEquals(expected, actualResult);
    }
}