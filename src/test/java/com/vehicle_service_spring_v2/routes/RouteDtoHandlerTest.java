package com.vehicle_service_spring_v2.routes;

import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.routes.model.Route;
import com.vehicle_service_spring_v2.routes.model.dto.RouteDto;
import com.vehicle_service_spring_v2.routes.model.dto.RouteDtoHandler;
import com.vehicle_service_spring_v2.transports.model.Bus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class RouteDtoHandlerTest {
    RouteDto testRouteDto;
    Route testRoute;

    @BeforeEach
    void setUp() {
        testRouteDto = RouteDto.builder()
                .id(1L)
                .startOfWay("testStart")
                .endOfWay("testEnd")
                .build();

        testRoute = Route.builder()
                .id(1L)
                .startOfWay("testStart")
                .endOfWay("testEnd")
                .build();
    }

    @Test
    void createRouteDto_inputsRouteAndReturnRouteDto() {
        //given

        //when
        RouteDto actualRouteDto = RouteDtoHandler.createRouteDto(testRoute);

        //then
        assertEquals(testRouteDto, actualRouteDto);
    }

    @Test
    void mappingDtoToRouteMethodAdd_inputsRouteDtoAndReturnRoute() {
        //given

        //when
        Route actualRoute = RouteDtoHandler.mappingDtoToRouteMethodAdd(testRouteDto);

        //then
        assertEquals(testRoute.getStartOfWay(), actualRoute.getStartOfWay());
        assertEquals(testRoute.getEndOfWay(), actualRoute.getEndOfWay());
    }

    @Test
    void mappingDtoToRouteMethodUpdate_inputsRouteDtoAndReturnRoute() {
        //given
        Bus bus = new Bus();
        bus.setId(1L);

        Driver driver = new Driver();
        driver.setId(1L);

        testRouteDto.getTransports().add(bus);
        testRouteDto.getDrivers().add(driver);

        //when
        Route actualRoute = RouteDtoHandler.mappingDtoToRouteMethodUpdate(testRouteDto, Optional.of(testRoute));

        //then
        assertEquals(testRoute, actualRoute);
    }

    @Test
    void mappingDtoToRouteMethodUpdate_inputsNullAndExpectRuntimeException() {
        //given
        Route route = null;

        //when
        RuntimeException exception = new RuntimeException(
                "Error, route with id = " + testRouteDto.getId() + " not found",
                assertThrows(
                        RuntimeException.class,
                        () -> RouteDtoHandler.mappingDtoToRouteMethodUpdate(testRouteDto, Optional.of(route)),
                        "Error, route with id = " + testRouteDto.getId() + " not found"
                ));

        //then
        assertEquals(exception.getMessage(), "Error, route with id = " + testRouteDto.getId() + " not found");
    }
}