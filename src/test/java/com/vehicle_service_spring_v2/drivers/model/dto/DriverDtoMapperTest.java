package com.vehicle_service_spring_v2.drivers.model.dto;

import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.drivers.model.DriverQualificationEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DriverDtoMapperTest {
    @Autowired
    DriverDtoMapper driverDtoMapper;

    @Test
    void toDriver() {
        //Given
        DriverDto testDto = DriverDto.builder()
                .id(null)
                .nameOfDriver("testName")
                .surnameOfDriver("testSurname")
                .phoneNumber("testPhone")
                .qualificationEnum("Bus")
                .build();

        Driver expected = Driver.builder()
                .id(null)
                .nameOfDriver("testName")
                .surnameOfDriver("testSurname")
                .phoneNumber("testPhone")
                .qualificationEnum(DriverQualificationEnum.BUS_DRIVER)
                .build();

        //When
        Driver actualDriver = driverDtoMapper.toDriver(testDto);

        //Then
        assertEquals(expected, actualDriver);
    }

    @Test
    void toDriverDto() {
        //Given
        DriverDto expected = DriverDto.builder()
                .id(null)
                .nameOfDriver("testName")
                .surnameOfDriver("testSurname")
                .phoneNumber("testPhone")
                .qualificationEnum(DriverQualificationEnum.BUS_DRIVER.name())
                .build();

        Driver tested = Driver.builder()
                .id(null)
                .nameOfDriver("testName")
                .surnameOfDriver("testSurname")
                .phoneNumber("testPhone")
                .qualificationEnum(DriverQualificationEnum.BUS_DRIVER)
                .build();

        //When
        DriverDto actual = driverDtoMapper.toDto(tested);

        //Then
        assertEquals(expected, actual);
    }
}