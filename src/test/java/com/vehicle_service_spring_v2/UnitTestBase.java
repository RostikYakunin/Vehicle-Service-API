package com.vehicle_service_spring_v2;

import com.vehicle_service_spring_v2.drivers.DriverRepoI;
import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.drivers.model.DriverQualificationEnum;
import com.vehicle_service_spring_v2.drivers.model.dto.DriverDto;
import com.vehicle_service_spring_v2.drivers.model.dto.DriverDtoMapper;
import com.vehicle_service_spring_v2.routes.RouteRepoI;
import com.vehicle_service_spring_v2.routes.model.Route;
import com.vehicle_service_spring_v2.routes.model.dto.RouteDto;
import com.vehicle_service_spring_v2.routes.model.dto.RouteDtoMapper;
import com.vehicle_service_spring_v2.transports.TransportRepoI;
import com.vehicle_service_spring_v2.transports.model.Bus;
import com.vehicle_service_spring_v2.transports.model.Tram;
import com.vehicle_service_spring_v2.transports.model.dto.TransportDto;
import com.vehicle_service_spring_v2.transports.model.dto.TransportDtoMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

public abstract class UnitTestBase {
    //Mocks
    @Mock
    protected DriverRepoI mockedDriverRepo;
    @Mock
    protected TransportRepoI mockedTransportRepo;
    @Mock
    protected RouteRepoI mockedRouteRepo;
    @Mock
    protected DriverDtoMapper mockedDriverDtoMapper;
    @Mock
    protected TransportDtoMapper mockedTransportDtoMapper;
    @Mock
    protected RouteDtoMapper mockedRouteDtoMapper;

    //Captors
    @Captor
    protected ArgumentCaptor<Long> longArgumentCaptor;
    @Captor
    protected ArgumentCaptor<String> stringArgumentCaptor;

    //Tested objects
    protected Driver testDriver;
    protected Route testRoute;
    protected Bus testBus;
    protected Tram testTram;

    //DTOs
    protected DriverDto testDriverDto;
    protected RouteDto testRouteDto;
    protected TransportDto testBusDto;
    protected TransportDto testTramDto;

    @BeforeEach
    protected void configure() {
        MockitoAnnotations.openMocks(this);

        //tested objects
        testDriver = Driver.builder()
                .id(1L)
                .nameOfDriver("testName")
                .surnameOfDriver("testSurname")
                .phoneNumber("testPhone")
                .qualificationEnum(DriverQualificationEnum.BUS_DRIVER)
                .route(Set.of(Route.builder()
                        .id(1L)
                        .build()))
                .transport(Set.of(Bus.builder()
                        .id(1L)
                        .build()))
                .build();

        testRoute = Route.builder()
                .id(1L)
                .startOfWay("testStart")
                .endOfWay("testEnd")
                .transports(Set.of(Bus.builder()
                        .id(1L)
                        .build()))
                .drivers(Set.of(Driver.builder()
                        .id(1L)
                        .build()))
                .build();

        testBus = Bus.builder()
                .id(1L)
                .brandOfTransport("testBus")
                .driverQualificationEnum(DriverQualificationEnum.BUS_DRIVER)
                .amountOfPassengers(30)
                .route(new HashSet<>(Set.of(Route.builder()
                        .id(1L)
                        .build())))
                .drivers(new HashSet<>(Set.of(Driver.builder()
                        .id(1L)
                        .build())))
                .type("testType")
                .amountOfDoors(3)
                .build();

        testTram = Tram.builder()
                .id(1L)
                .brandOfTransport("testTram")
                .driverQualificationEnum(DriverQualificationEnum.TRAM_DRIVER)
                .amountOfPassengers(40)
                .route(new HashSet<>(Set.of(Route.builder()
                        .id(1L)
                        .build())))
                .drivers(new HashSet<>(Set.of(Driver.builder()
                        .id(1L)
                        .build())))
                .amountOfRailcar(4)
                .build();

        // tested DTOs
        testDriverDto = DriverDto.builder()
                .id(1L)
                .nameOfDriver("testName")
                .surnameOfDriver("testSurname")
                .phoneNumber("testPhone")
                .qualificationEnum("BUS")
                .build();

        testRouteDto = RouteDto.builder()
                .id(1L)
                .startOfWay("testStart")
                .endOfWay("testEnd")
                .build();

        testBusDto = TransportDto.builder()
                .id(1L)
                .amountOfDoors(3)
                .amountOfPassengers(30)
                .brandOfTransport("testBus")
                .type("testType")
                .driverQualificationEnum("bus")
                .build();

        testTramDto = TransportDto.builder()
                .id(1L)
                .amountOfPassengers(40)
                .brandOfTransport("testTram")
                .type("testType")
                .driverQualificationEnum("tram")
                .amountOfRailcar(4)
                .build();
    }

    @AfterEach
    protected void destroy() {
        //Tested objects
        Driver testDriver = null;
        Route testRoute = null;
        Bus testBus = null;
        Tram testTram = null;

        //DTOs
        DriverDto testDriverDto = null;
        RouteDto testRouteDto = null;
        TransportDto testBusDto = null;
        TransportDto testTramDto = null;
    }
}
