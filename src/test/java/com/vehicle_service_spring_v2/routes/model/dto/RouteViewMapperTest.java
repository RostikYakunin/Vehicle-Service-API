package com.vehicle_service_spring_v2.routes.model.dto;

import com.vehicle_service_spring_v2.drivers.model.Driver;
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
class RouteViewMapperTest {
    @Autowired
    RouteViewMapper routeViewMapper;

    @Test
    void routeToRouteView() {
        //Given
        RouteView expected = RouteView.builder()
                .id(1L)
                .startOfWay("Start")
                .endOfWay("End")
                .driversId(Set.of(1L))
                .transportsId(Set.of(1L))
                .build();

        Route testRoute = Route.builder()
                .id(1L)
                .startOfWay("Start")
                .endOfWay("End")
                .drivers(Set.of(new Driver(1L)))
                .transports(Set.of(Tram.builder()
                        .id(1L)
                        .build()))
                .build();

        //When
        RouteView actualResult = routeViewMapper.routeToRouteView(testRoute);

        //Then
        assertEquals(expected, actualResult);
        assertEquals(actualResult.getClass(), RouteView.class);
    }

    @Test
    void routeViewToRoute() {
        //Given
        RouteView testView = RouteView.builder()
                .id(1L)
                .startOfWay("Start")
                .endOfWay("End")
                .build();

        Route expected = Route.builder()
                .id(1L)
                .startOfWay("Start")
                .endOfWay("End")
                .build();

        //When
        Route actual = routeViewMapper.routeViewToRoute(testView);

        //Then
        assertEquals(expected, actual);
        assertEquals(actual.getClass(), Route.class);
    }
}