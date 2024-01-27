package com.vehicle_service_spring_v_3.transport;

import com.vehicle_service_spring_v_3.drivers.model.DriverQualificationEnum;
import com.vehicle_service_spring_v_3.transports.TransportRepoI;
import com.vehicle_service_spring_v_3.transports.model.Bus;
import com.vehicle_service_spring_v_3.transports.model.Transport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TransportRepoITest {
    @Autowired
    TransportRepoI transportRepo;
    Bus testBus;

    @BeforeEach
    void setUp() {
        testBus = Bus.builder()
                .type("testType")
                .amountOfDoors(3)
                .build();
        testBus.setId(1L);
        testBus.setBrandOfTransport("testBus");
        testBus.setDriverQualificationEnum(DriverQualificationEnum.BUS_DRIVER);
        testBus.setAmountOfPassengers(30);

        transportRepo.save(testBus);
    }

    @AfterEach
    void tearDown() {
        transportRepo.deleteAll();
    }

    @Test
    void findTransportByBrand_inputBrandOfBusAndReturnObjBus() {
        //given
        Bus expectedBus = new Bus();
        expectedBus.setBrandOfTransport("testBus");

        //when
        Transport actualBus = transportRepo.findTransportByBrand("testBus").get(0);

        //then
        assertEquals(expectedBus.getBrandOfTransport(), actualBus.getBrandOfTransport());
    }

    @Test
    void findTransportWithoutDriver_inputTransportAndReturnObjWithoutDriver() {
        //given
        Bus expectedBus = testBus;

        //when
        Optional<Transport> actualTransport = transportRepo.findTransportWithoutDriver().stream().findFirst();

        //then
        assertEquals(expectedBus, actualTransport.get());
    }
}