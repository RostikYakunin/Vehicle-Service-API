package com.vehicle_service_spring_v2.drivers;

import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.drivers.model.DriverQualificationEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DriverRepoITest {
    @Autowired
    DriverRepoI driverRepo;

    @BeforeEach
    void setUp() {
        driverRepo.save(
                Driver.builder()
                        .id(1L)
                        .nameOfDriver("testName")
                        .surnameOfDriver("testSurname")
                        .phoneNumber("1223334444")
                        .qualificationEnum(DriverQualificationEnum.TRAM_DRIVER)
                        .build()
        );
    }

    @AfterEach
    void turnDown() {
        driverRepo.deleteAll();
    }

    @Test
    void findDriversBySurname_inputSurnameAndReturnObjWithSurname() {
        //given
        Driver expectedDriver = new Driver();
        expectedDriver.setSurnameOfDriver("testSurname");

        //when
        Driver actualDriver = driverRepo.findDriversBySurname("testSurname").get(0);

        //then
        assertEquals(expectedDriver.getSurnameOfDriver(), actualDriver.getSurnameOfDriver());
    }
}