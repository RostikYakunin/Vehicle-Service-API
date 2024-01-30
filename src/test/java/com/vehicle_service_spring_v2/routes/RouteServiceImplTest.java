package com.vehicle_service_spring_v2.routes;

import com.vehicle_service_spring_v2.UnitTestBase;
import com.vehicle_service_spring_v2.routes.model.Route;
import com.vehicle_service_spring_v2.routes.model.dto.RouteDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RouteServiceImplTest extends UnitTestBase {
    RouteServiceI routeService;
    @Captor
    ArgumentCaptor<Route> routeArgumentCaptor;
    @Captor
    ArgumentCaptor<RouteDto> routeDtoArgumentCaptor;

    @BeforeEach
    void setUp() {
        super.configure();
        routeService = new RouteServiceImpl(mockedRouteRepo, mockedRouteDtoMapper);
    }

    @AfterEach
    void turnDown() {
        super.destroy();
        routeService = null;
    }

    @Test
    @DisplayName("Should add route with input route dto and return route")
    void addRoute_inputRouteDtoReturnsRoute() {
        //given
        when(mockedRouteRepo.save(any(Route.class))).thenReturn(testRoute);
        when(mockedRouteDtoMapper.toRoute(any(RouteDto.class))).thenReturn(testRoute);

        //when
        Route actualRoute = routeService.addRoute(testRouteDto);

        //then
        verify(mockedRouteRepo, times(1)).save(routeArgumentCaptor.capture());
        verify(mockedRouteDtoMapper, times(1)).toRoute(routeDtoArgumentCaptor.capture());

        assertEquals(testRoute, actualRoute);
    }

    @Test
    @DisplayName("Should find route by id with input long and return route")
    void findRouteById_inputLongReturnRoute() {
        //given
        when(mockedRouteRepo.findById(anyLong())).thenReturn(Optional.of(testRoute));

        //when
        Route actualRoute = routeService.findRouteById(1L);

        //then
        verify(mockedRouteRepo, times(1)).findById(longArgumentCaptor.capture());

        assertEquals(testRoute, actualRoute);
    }

    @Test
    @DisplayName("Should throw exception when finding route by id with input null and return Optional empty")
    void findRouteById_inputNullReturnOptionalEmpty() {
        //given
        when(mockedRouteRepo.findById(anyLong())).thenReturn(Optional.empty());
        when(mockedRouteRepo.save(any(Route.class))).thenReturn(testRoute);

        //when
        assertThrows(
                RuntimeException.class,
                () -> routeService.findRouteById(1L),
                "Route with id=" + 1 + " not found !"
        );

        //then
        verify(mockedRouteRepo, times(1)).findById(longArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Should update route with input route dto and return route")
    void updateRoute_inputRouteDtoReturnRoute() {
        //given
        when(mockedRouteDtoMapper.toRoute(testRouteDto)).thenReturn(testRoute);
        when(mockedRouteRepo.save(any(Route.class))).thenReturn(testRoute);

        //when
        Route actualRoute = routeService.updateRoute(1L, testRouteDto);

        //then
        verify(mockedRouteRepo, times(1)).save(routeArgumentCaptor.capture());
        verify(mockedRouteDtoMapper, times(1)).toRoute(routeDtoArgumentCaptor.capture());

        assertEquals(testRoute, actualRoute);
    }

    @Test
    @DisplayName("Should delete route by id with input long and return true")
    void deleteRouteById_inputLongReturnTrue() {
        //given
        testRoute.getTransports().clear();
        when(mockedRouteRepo.findById(anyLong())).thenReturn(Optional.of(testRoute));
        doNothing().when(mockedRouteRepo).deleteById(anyLong());

        //when
        boolean actualResult = routeService.deleteRouteById(1L);

        //then
        verify(mockedRouteRepo, times(1)).findById(longArgumentCaptor.capture());
        verify(mockedRouteRepo, times(1)).delete(routeArgumentCaptor.capture());

        assertTrue(actualResult);
    }

    @Test
    @DisplayName("Should throw exception when deleting route by id with input null and return false")
    void deleteRouteById_inputNull_ThrowException() {
        //given
        when(mockedRouteRepo.findById(anyLong())).thenReturn(Optional.empty());

        //when
        assertThrows(
                RuntimeException.class,
                () -> routeService.deleteRouteById(1L),
                "Route with id= " + 1 + " not found"
        );

        //then
        verify(mockedRouteRepo, times(1)).findById(longArgumentCaptor.capture());
        verify(mockedRouteRepo, times(0)).delete(routeArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Should not delete route by id with input route with non-empty transports and return false")
    void deleteRouteById_inputRouteWithNotEmptyTransports_ReturnFalse() {
        //given
        when(mockedRouteRepo.findById(anyLong())).thenReturn(Optional.of(testRoute));

        //when
        boolean actualResult = routeService.deleteRouteById(1L);

        //then
        verify(mockedRouteRepo, times(1)).findById(longArgumentCaptor.capture());
        verify(mockedRouteRepo, never()).delete(routeArgumentCaptor.capture());

        assertFalse(actualResult);
    }

    @Test
    @DisplayName("Should find all routes and return list of routes")
    void findAllRoutes_inputNothingReturnListOfRoutes() {
        //given
        when(mockedRouteRepo.findAll()).thenReturn(List.of(testRoute));

        //when
        List<Route> actualRoutes = routeService.findAllRoutes();

        //then
        verify(mockedRouteRepo, times(1)).findAll();

        assertEquals(List.of(testRoute), actualRoutes);
    }

    @Test
    @DisplayName("Should find all routes without transport and return list of routes without transports")
    void findAllRoutesWithoutTransport_inputNothingReturnListRoutesWithoutTransports() {
        //given
        testRoute.getTransports().clear();
        when(mockedRouteRepo.findAllRoutesWithoutTransport()).thenReturn(List.of(testRoute));

        //when
        List<Route> actualList = routeService.findAllRoutesWithoutTransport();

        //then
        verify(mockedRouteRepo, times(1)).findAllRoutesWithoutTransport();

        assertEquals(List.of(testRoute), actualList);
    }
}