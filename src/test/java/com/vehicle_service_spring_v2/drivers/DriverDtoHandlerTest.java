package com.vehicle_service_spring_v2.drivers;

import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.drivers.model.DriverQualificationEnum;
import com.vehicle_service_spring_v2.drivers.model.dto.DriverDto;
import com.vehicle_service_spring_v2.drivers.model.dto.DriverDtoHandler;
import com.vehicle_service_spring_v2.routes.model.Route;
import com.vehicle_service_spring_v2.transports.model.Bus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class DriverDtoHandlerTest {
    DriverDto testDriverDto;
    Driver testDriver;

    @BeforeEach
    void setUp() {
        // test driver dto
        Bus bus = Bus.builder()
                .type("testType")
                .amountOfDoors(3)
                .build();
        bus.setId(1L);
        bus.setDriverQualificationEnum(DriverQualificationEnum.BUS_DRIVER);
        bus.setAmountOfPassengers(30);
        bus.setBrandOfTransport("tesBrand");

        Route route = Route.builder()
                .id(1L)
                .startOfWay("testStart")
                .endOfWay("testEnd")
                .build();

        testDriverDto = DriverDto.builder()
                .id(1L)
                .nameOfDriver("testName")
                .surnameOfDriver("testSurname")
                .phoneNumber("testPhoneNumber")
                .qualificationEnum("bus")
                .build();
        testDriverDto.getTransport().add(bus);
        testDriverDto.getRoute().add(route);

        // test driver
        testDriver = Driver.builder()
                .id(1L)
                .nameOfDriver("testName")
                .surnameOfDriver("testSurname")
                .phoneNumber("testPhoneNumber")
                .qualificationEnum(DriverQualificationEnum.BUS_DRIVER)
                .build();

        testDriver.getTransport().add(bus);
        testDriver.getRoute().add(route);
    }

    @Test
    void mappingDtoToDriverMethodAdd_inputDriverDtoAndReturnDriver() {
        //given
        Driver expectedDriver = testDriver;

        //when
        Driver actualDriver = DriverDtoHandler.mappingDtoToDriverMethodAdd(testDriverDto);

        //then
        assertEquals(expectedDriver.getId(), actualDriver.getId());
        assertEquals(expectedDriver.getNameOfDriver(), actualDriver.getNameOfDriver());
        assertEquals(expectedDriver.getSurnameOfDriver(), actualDriver.getSurnameOfDriver());
        assertEquals(expectedDriver.getPhoneNumber(), actualDriver.getPhoneNumber());
    }

    @Test
    void mappingDtoToDriverMethodUpdate_inputDriverDtoAndReturnDriver() {
        //given
        Driver expectedDriver = testDriver;

        //when
        Driver actualDriver = DriverDtoHandler.mappingDtoToDriverMethodUpdate(testDriverDto, Optional.of(testDriver));

        //then
        assertEquals(expectedDriver, actualDriver);
    }

    @Test
    void mappingDtoToDriverMethodUpdate_inputNullAndExpectRuntimeException() {
        //given
        Driver driver = null;

        //when
        //then
        assertThrows(
                RuntimeException.class,
                () -> DriverDtoHandler.mappingDtoToDriverMethodUpdate(testDriverDto, Optional.of(driver)),
                "Expected runtime exception"
        );
    }
}