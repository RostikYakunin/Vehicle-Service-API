package com.vehicle_service_spring_v2.drivers.model.dto;

import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.drivers.model.DriverQualificationEnum;
import com.vehicle_service_spring_v2.routes.model.Route;
import com.vehicle_service_spring_v2.transports.model.Tram;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class DriverViewMapperTest {
    @Autowired
    DriverViewMapper driverViewMapper;

    @Test
    void driverToDriverView() {
        //Given
        Driver testedDriver = Driver.builder()
                .id(1L)
                .nameOfDriver("testName")
                .surnameOfDriver("testSurname")
                .phoneNumber("testPhone")
                .qualificationEnum(DriverQualificationEnum.BUS_DRIVER)
                .route(Set.of(Route.builder()
                        .id(1L).build()))
                .transport(Set.of(Tram.builder()
                        .id(1L)
                        .build()))
                .build();

        DriverView expected = DriverView.builder()
                .id(1L)
                .nameOfDriver("testName")
                .surnameOfDriver("testSurname")
                .phoneNumber("testPhone")
                .qualificationEnum(DriverQualificationEnum.BUS_DRIVER)
                .routeId(Set.of(1L))
                .transportId(Set.of(1L))
                .build();

        //When
        DriverView actual = driverViewMapper.toDriverView(testedDriver);

        //Then
        assertEquals(expected, actual);
    }
}