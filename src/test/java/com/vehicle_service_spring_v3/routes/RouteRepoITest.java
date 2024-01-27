package com.vehicle_service_spring_v3.routes;

import com.vehicle_service_spring_v3.routes.model.Route;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RouteRepoITest {
    @Autowired
    RouteRepoI routeRepo;
    Route testRoute;

    @BeforeEach
    void setUp() {
        testRoute = Route.builder()
                .id(1L)
                .startOfWay("testStart")
                .endOfWay("testEnd")
                .build();

        routeRepo.save(testRoute);
    }

    @AfterEach
    void tearDown() {
        routeRepo.deleteAll();
    }

    @Test
    void findAllRoutesWithoutTransport_returnsRouteWithoutTransports() {
        //given
        Route expectedRoute = testRoute;

        //when
        Route actualRoute = routeRepo.findAllRoutesWithoutTransport().stream().findFirst().get();
        System.out.println(actualRoute);

        //then
        assertEquals(expectedRoute.getTransports(), actualRoute.getTransports());
    }
}