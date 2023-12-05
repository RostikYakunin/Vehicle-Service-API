package com.vehicle_service_spring_v2.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicle_service_spring_v2.routes.model.Route;
import com.vehicle_service_spring_v2.routes.model.dto.ReturnedRoute;
import com.vehicle_service_spring_v2.routes.model.dto.RouteDto;
import com.vehicle_service_spring_v2.routes.resource.RouteController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RouteController.class)
class RouteControllerTest {
    // bean
    @Autowired
    MockMvc mockMvc;
    @MockBean
    RouteServiceI routeService;
    @Autowired
    ObjectMapper mapper;

    // captors
    @Captor
    ArgumentCaptor<RouteDto> routeDtoArgumentCaptor;
    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;

    // objects
    Route testRoute;
    RouteDto testRouteDto;
    ReturnedRoute testReturnedRoute;

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

        testReturnedRoute = ReturnedRoute.builder()
                .id(1L)
                .startOfWay("testStart")
                .endOfWay("testEnd")
                .build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void post_createRoute_returnsObjWith201_Created() throws Exception {
        //given

        //when
        when(routeService.addRoute(any(RouteDto.class))).thenReturn(testRoute);

        //then
        mockMvc.perform(
                        post("/api/routes")
                                .content(mapper.writeValueAsString(testRouteDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.startOfWay").isString())
                .andExpect(jsonPath("$.endOfWay").isString())
                .andExpect(jsonPath("$.id").value(testReturnedRoute.getId()))
                .andExpect(jsonPath("$.startOfWay").value(testReturnedRoute.getStartOfWay()))
                .andExpect(jsonPath("$.endOfWay").value(testReturnedRoute.getEndOfWay()));

        verify(routeService, times(1)).addRoute(routeDtoArgumentCaptor.capture());
    }

    @Test
    void get_findRouteById_returnObjWith200_Ok() throws Exception {
        //given

        //when
        when(routeService.findRouteById(anyLong())).thenReturn(Optional.of(testRoute));

        //then
        mockMvc.perform(
                        get("/api/routes/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.startOfWay").isString())
                .andExpect(jsonPath("$.endOfWay").isString())
                .andExpect(jsonPath("$.id").value(testReturnedRoute.getId()))
                .andExpect(jsonPath("$.startOfWay").value(testReturnedRoute.getStartOfWay()))
                .andExpect(jsonPath("$.endOfWay").value(testReturnedRoute.getEndOfWay()));

        verify(routeService, times(1)).findRouteById(longArgumentCaptor.capture());
    }

    @Test
    void put_updateRoute_returnsObjWith200_ok() throws Exception {
        //given
        //when
        when(routeService.updateRoute(any(RouteDto.class))).thenReturn(testRoute);

        //then
        mockMvc.perform(
                        put("/api/routes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(testRouteDto)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.startOfWay").isString())
                .andExpect(jsonPath("$.endOfWay").isString())
                .andExpect(jsonPath("$.id").value(testReturnedRoute.getId()))
                .andExpect(jsonPath("$.startOfWay").value(testReturnedRoute.getStartOfWay()))
                .andExpect(jsonPath("$.endOfWay").value(testReturnedRoute.getEndOfWay()));

        verify(routeService, times(1)).updateRoute(routeDtoArgumentCaptor.capture());

    }

    @Test
    void delete_deleteRouteById_returnsNothingWith200_Ok() throws Exception {
        //given

        //when
        when(routeService.deleteRouteById(anyLong())).thenReturn(true);

        //then
        mockMvc.perform(
                        delete("/api/routes/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(routeService, times(1)).deleteRouteById(longArgumentCaptor.capture());
    }

    @Test
    void get_findAllRoutes_returnsListWith200_Ok() throws Exception {
        //given

        //when
        when(routeService.findAllRoutes()).thenReturn(List.of(testRoute));

        //then
        mockMvc.perform(
                        get("/api/routes")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].startOfWay").isString())
                .andExpect(jsonPath("$[0].endOfWay").isString())
                .andExpect(jsonPath("$[0].id").value(testReturnedRoute.getId()))
                .andExpect(jsonPath("$[0].startOfWay").value(testReturnedRoute.getStartOfWay()))
                .andExpect(jsonPath("$[0].endOfWay").value(testReturnedRoute.getEndOfWay()));

        verify(routeService, times(1)).findAllRoutes();
    }

    @Test
    void get_findAllRoutesWithoutTransport_returnsListWith200_Ok() throws Exception {
        //given
        //when
        when(routeService.findAllRoutesWithoutTransport()).thenReturn(List.of(testRoute));

        //then
        mockMvc.perform(
                        get("/api/routes/all_without_transport")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].startOfWay").isString())
                .andExpect(jsonPath("$[0].endOfWay").isString())
                .andExpect(jsonPath("$[0].id").value(testReturnedRoute.getId()))
                .andExpect(jsonPath("$[0].startOfWay").value(testReturnedRoute.getStartOfWay()))
                .andExpect(jsonPath("$[0].endOfWay").value(testReturnedRoute.getEndOfWay()));

        verify(routeService, times(1)).findAllRoutesWithoutTransport();
    }
}