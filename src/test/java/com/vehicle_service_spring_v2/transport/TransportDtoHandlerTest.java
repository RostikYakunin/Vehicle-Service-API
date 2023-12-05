package com.vehicle_service_spring_v2.transport;

import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.drivers.model.DriverQualificationEnum;
import com.vehicle_service_spring_v2.routes.model.Route;
import com.vehicle_service_spring_v2.transports.TransportDto;
import com.vehicle_service_spring_v2.transports.TransportDtoHandler;
import com.vehicle_service_spring_v2.transports.model.Bus;
import com.vehicle_service_spring_v2.transports.model.Tram;
import com.vehicle_service_spring_v2.transports.model.Transport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TransportDtoHandlerTest {
    Bus testBus;
    Tram testTram;
    TransportDto testBusDto;
    TransportDto testTramDto;

    @BeforeEach
    void setUp() {
        testBus = Bus.builder()
                .type("testType")
                .amountOfDoors(3)
                .build();
        testBus.setId(1L);
        testBus.setBrandOfTransport("testBrand");
        testBus.setDriverQualificationEnum(DriverQualificationEnum.BUS_DRIVER);
        testBus.setAmountOfPassengers(30);
        testBus.getDrivers().add(new Driver(1L));
        testBus.getRoute().add(new Route(1L));

        testTram = Tram.builder()
                .amountOfRailcar(4)
                .build();
        testBus.setId(1L);
        testBus.setBrandOfTransport("testBrand");
        testBus.setDriverQualificationEnum(DriverQualificationEnum.TRAM_DRIVER);
        testBus.setAmountOfPassengers(30);
        testTram.getDrivers().add(new Driver(1L));
        testTram.getRoute().add(new Route(1L));

        testBusDto = TransportDto.builder()
                .id(1L)
                .amountOfDoors(3)
                .amountOfPassengers(30)
                .brandOfTransport("testBrand")
                .type("testType")
                .driverQualificationEnum("BUS")
                .build();
        testBusDto.getDrivers().add(new Driver(1L));
        testBusDto.getRoutes().add(new Route(1L));

        testTramDto = TransportDto.builder()
                .id(1L)
                .amountOfDoors(3)
                .amountOfPassengers(30)
                .brandOfTransport("testBrand")
                .type("testType")
                .driverQualificationEnum("TRAM")
                .build();
        testTramDto.getDrivers().add(new Driver(1L));
        testTramDto.getRoutes().add(new Route(1L));
    }

    @Test
    void createTransportDto_inputBusAndReturnTransportBusDto() {
        //given
        testBusDto.getDrivers().clear();
        testBusDto.getRoutes().clear();

        //when
        TransportDto actualBus = TransportDtoHandler.createTransportDto(testBus);

        //then
        assertEquals(testBusDto, actualBus);
        assertNull(actualBus.getAmountOfRailcar());
    }

    @Test
    void createTransportDto_inputTramAndReturnTransportTramDto() {
        //given
        testTramDto.getDrivers().clear();
        testTramDto.getRoutes().clear();

        //when
        TransportDto actualTram = TransportDtoHandler.createTransportDto(testTram);

        //then
        assertEquals(testTramDto, actualTram);
        assertNull(actualTram.getAmountOfDoors());
        assertNull(actualTram.getType());
    }

    @Test
    void mappingDtoToTransportMethodAdd_inputBusDtoReturnsBus() {
        //given

        //when
        Transport actualBus = TransportDtoHandler.mappingDtoToTransportMethodAdd(testBusDto);

        //then
        assertEquals(testBus, actualBus);
    }

    @Test
    void mappingDtoToTransportMethodAdd_inputTramDtoReturnsTram() {
        //given

        //when
        Transport actualTram = TransportDtoHandler.mappingDtoToTransportMethodAdd(testTramDto);

        //then
        assertEquals(testTram, actualTram);
    }

    @Test
    void mappingDtoToTransportMethodAdd_inputTransportDtoAndExpectedException() {
        //given
        TransportDto transportDto = new TransportDto();
        transportDto.setId(1L);

        //when
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> TransportDtoHandler.mappingDtoToTransportMethodAdd(transportDto),
                "Expected runtime exception");

        //then
        assertEquals("Transport: " + transportDto + " cannot mapped to transport", runtimeException.getMessage());
    }

    @Test
    void mappingDtoToTransportMethodUpdate_inputBusDtoAndReturnBus() {
        //given
        Bus expectedBus = testBus;
        expectedBus.getDrivers().add(new Driver(1L));
        expectedBus.getRoute().add(new Route(1L));

        //when
        Transport actualBus = TransportDtoHandler.mappingDtoToTransportMethodUpdate(testBusDto, Optional.of(testBus));

        //then
        assertEquals(expectedBus, actualBus);
    }

    @Test
    void mappingDtoToTransportMethodUpdate_inputTramDtoAndReturnTram() {
        //given
        Tram expectedTram = testTram;
        expectedTram.getDrivers().add(new Driver(1L));
        expectedTram.getRoute().add(new Route(1L));

        //when
        Transport actualTram = TransportDtoHandler.mappingDtoToTransportMethodUpdate(testTramDto, Optional.of(testTram));

        //then
        assertEquals(expectedTram, actualTram);
    }

    @Test
    void mappingDtoToTransportMethodUpdate_inputNullAndExpectRuntimeException() {
        //given

        //when
        RuntimeException runtimeException = new RuntimeException(
                "My runtime exception",
                assertThrows(
                        RuntimeException.class,
                        () -> TransportDtoHandler.mappingDtoToTransportMethodUpdate(testTramDto, Optional.of(null)),
                        "runtime"
                ));

        //then
        assertTrue(runtimeException.getMessage().contains("runtime"));
    }
}