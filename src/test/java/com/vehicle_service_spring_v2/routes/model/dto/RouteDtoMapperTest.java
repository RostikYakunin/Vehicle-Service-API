package com.vehicle_service_spring_v2.routes.model.dto;

import com.vehicle_service_spring_v2.routes.model.Route;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RouteDtoMapperTest {
    @Autowired
    RouteDtoMapper routeDtoMapper;

    @Test
    void routeDtoToRoute() {
        //Given
        RouteDto routeDto = RouteDto.builder()
                .id(1L)
                .startOfWay("Start")
                .endOfWay("End")
                .build();

        Route expectedRoute = Route.builder()
                .id(1L)
                .startOfWay("Start")
                .endOfWay("End")
                .build();

        //When
        Route actualResult = routeDtoMapper.toRoute(routeDto);

        //Then
        assertEquals(actualResult.getClass(), Route.class);
        assertEquals(expectedRoute, actualResult);
    }

    @Test
    void routeToDto() {
        //Given
        Route route = Route.builder()
                .id(1L)
                .startOfWay("Start")
                .endOfWay("End")
                .build();

        RouteDto expectedRouteDto = RouteDto.builder()
                .id(1L)
                .startOfWay("Start")
                .endOfWay("End")
                .build();

        //When
        RouteDto actualResult = routeDtoMapper.toDto(route);

        //Then
        assertEquals(actualResult.getClass(), RouteDto.class);
        assertEquals(expectedRouteDto, actualResult);
    }
}