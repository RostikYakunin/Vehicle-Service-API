package com.vehicle_service_spring_v2.routes.model.dto;

import com.vehicle_service_spring_v2.routes.model.Route;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RouteDtoMapperTest {
    RouteDtoMapper routeDtoMapper = new RouteDtoMapperImpl();

    @Test
    @DisplayName("Mapping RouteDto to Route")
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
    @DisplayName("Mapping Route to RouteDto")
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

    @Test
    @DisplayName("Updating Route using RouteDto should ignore null value")
    void updateRoute() {
        //Given
        Route testRoute = Route.builder()
                .id(1L)
                .startOfWay("Start")
                .endOfWay("End")
                .build();

        RouteDto testRouteDto = RouteDto.builder()
                .startOfWay("Changed")
                .endOfWay(null)
                .build();

        Route expectedRoute = Route.builder()
                .id(1L)
                .startOfWay("Changed")
                .endOfWay("End")
                .build();

        //When
        Route actualResult = routeDtoMapper.updateRoute(testRoute, testRouteDto);

        //Then
        assertEquals(expectedRoute, actualResult);
    }
}