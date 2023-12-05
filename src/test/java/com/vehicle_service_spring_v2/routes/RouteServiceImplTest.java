package com.vehicle_service_spring_v2.routes;

import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.routes.model.Route;
import com.vehicle_service_spring_v2.routes.model.dto.RouteDto;
import com.vehicle_service_spring_v2.transports.model.Bus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class RouteServiceImplTest {
    @MockBean
    RouteRepoI routeRepo;
    @Autowired
    RouteServiceImpl routeService;
    @Captor
    ArgumentCaptor<Route> routeArgumentCaptor;
    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;

    RouteDto testRouteDto;
    Route testRoute;

    @BeforeEach
    void setUp() {
        testRoute = Route.builder()
                .id(1L)
                .startOfWay("testStart")
                .endOfWay("testEnd")
                .build();

        testRouteDto = RouteDto.builder()
                .id(1L)
                .startOfWay("testStart")
                .endOfWay("testEnd")
                .build();

        Bus bus = new Bus();
        bus.setId(1L);

        Driver driver = new Driver();
        driver.setId(1L);

        testRouteDto.getTransports().add(bus);
        testRouteDto.getDrivers().add(driver);

        testRoute.getTransports().add(bus);
        testRoute.getDrivers().add(driver);
    }

    @Test
    void addRoute_inputRouteDtoReturnsRoute() {
        //given

        //when
        when(routeRepo.save(any(Route.class))).thenReturn(testRoute);

        //then
        Route actualRoute = routeService.addRoute(testRouteDto);
        assertEquals(testRoute, actualRoute);
        verify(routeRepo, times(1)).save(routeArgumentCaptor.capture());
    }

    @Test
    void findRouteById_inputLongReturnOptionalOfRoute() {
        //given

        //when
        when(routeRepo.findById(anyLong())).thenReturn(Optional.of(testRoute));

        //then
        Optional<Route> actualRoute = routeService.findRouteById(1L);
        assertEquals(Optional.of(testRoute), actualRoute);
        verify(routeRepo, times(2)).findById(longArgumentCaptor.capture());
    }

    @Test
    void findRouteById_inputNullReturnOptionalEmpty() {
        //given

        //when
        when(routeRepo.findById(anyLong())).thenReturn(Optional.empty());
        when(routeRepo.save(any(Route.class))).thenReturn(testRoute);

        //then
        assertEquals(Optional.empty(), routeService.findRouteById(1L));

        verify(routeRepo, times(1)).findById(longArgumentCaptor.capture());
    }

    @Test
    void updateRoute_inputRouteDtoReturnRoute() {
        //given

        //when
        when(routeRepo.findById(anyLong())).thenReturn(Optional.of(testRoute));
        when(routeRepo.save(any(Route.class))).thenReturn(testRoute);

        //then
        Route actualRoute = routeService.updateRoute(testRouteDto);
        assertEquals(testRoute, actualRoute);

        verify(routeRepo, times(1)).findById(longArgumentCaptor.capture());
        verify(routeRepo, times(1)).save(routeArgumentCaptor.capture());
    }

    @Test
    void deleteRouteById_inputLongReturnTrue() {
        //given
        testRoute.getTransports().clear();

        //when
        when(routeRepo.findById(anyLong())).thenReturn(Optional.of(testRoute));

        //then
        boolean actualResult = routeService.deleteRouteById(1L);
        assertTrue(actualResult);

        verify(routeRepo, times(1)).findById(longArgumentCaptor.capture());
        verify(routeRepo, times(1)).delete(routeArgumentCaptor.capture());
    }

    @Test
    void deleteRouteById_inputNullReturnFalse() {
        //given

        //when
        when(routeRepo.findById(anyLong())).thenReturn(Optional.empty());

        //then
        boolean actualResult = routeService.deleteRouteById(1L);
        assertFalse(actualResult);

        verify(routeRepo, times(1)).findById(longArgumentCaptor.capture());
        verify(routeRepo, times(0)).delete(routeArgumentCaptor.capture());
    }

    @Test
    void deleteRouteById_inputRouteWithNotEmptyTransports_ReturnFalse() {
        //given

        //when
        when(routeRepo.findById(anyLong())).thenReturn(Optional.of(testRoute));

        //then
        boolean actualResult = routeService.deleteRouteById(1L);
        assertFalse(actualResult);

        verify(routeRepo, times(1)).findById(longArgumentCaptor.capture());
        verify(routeRepo, times(0)).delete(routeArgumentCaptor.capture());
    }

    @Test
    void findAllRoutes_inputNothingReturnListOfRoutes() {
        //given

        //when
        when(routeRepo.findAll()).thenReturn(List.of(testRoute));

        //then
        List<Route> actualRoutes = routeService.findAllRoutes();
        assertEquals(List.of(testRoute), actualRoutes);

        verify(routeRepo, times(1)).findAll();
    }

    @Test
    void findAllRoutesWithoutTransport_inputNothingReturnListRoutesWithoutTransports() {
        //given
        testRoute.getTransports().clear();

        //when
        when(routeRepo.findAllRoutesWithoutTransport()).thenReturn(List.of(testRoute));

        //then
        List<Route> actualList = routeService.findAllRoutesWithoutTransport();
        assertEquals(List.of(testRoute), actualList);

        verify(routeRepo, times(1)).findAllRoutesWithoutTransport();
    }
}