package com.vehicle_service_spring_v2.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicle_service_spring_v2.auth.services.AuthenticationService;
import com.vehicle_service_spring_v2.auth.services.JwtService;
import com.vehicle_service_spring_v2.auth.users.UserRepository;
import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.routes.model.Route;
import com.vehicle_service_spring_v2.routes.model.dto.RouteDto;
import com.vehicle_service_spring_v2.routes.model.dto.RouteView;
import com.vehicle_service_spring_v2.routes.resource.RouteResource;
import com.vehicle_service_spring_v2.transports.model.Bus;
import com.vehicle_service_spring_v2.utils.ViewMapperUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RouteResource.class)
@ActiveProfiles("test")
@WithMockUser(
        username = "admin",
        roles = "ADMIN",
        value = "admin"
)
class RouteControllerTest {
    // bean
    @Autowired
    MockMvc mockMvc;
    @MockBean
    RouteServiceI routeService;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    JwtService jwtService;
    @MockBean
    AuthenticationService authenticationService;
    @MockBean
    UserRepository userRepository;
    @MockBean
    CommandLineRunner commandLineRunner;
    @MockBean
    ViewMapperUtil viewMapperUtil;

    // captors
    @Captor
    ArgumentCaptor<RouteDto> routeDtoArgumentCaptor;
    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;
    @Captor
    ArgumentCaptor<Route> routeArgumentCaptor;

    // objects
    Route testRoute;
    RouteDto testRouteDto;
    RouteView testRouteView;

    @BeforeEach
    void setUp() {
        testRoute = Route.builder()
                .id(1L)
                .startOfWay("testStart")
                .endOfWay("testEnd")
                .transports(new HashSet<>(Set.of(Bus.builder()
                        .id(1L)
                        .build())))
                .drivers(new HashSet<>(Set.of(Driver.builder()
                        .id(1L)
                        .build())))
                .build();

        testRouteDto = RouteDto.builder()
                .id(1L)
                .startOfWay("testStart")
                .endOfWay("testEnd")
                .build();

        testRouteView = RouteView.builder()
                .id(1L)
                .startOfWay("testStart")
                .endOfWay("testEnd")
                .transportsId(new HashSet<>(Set.of(1L)))
                .driversId(new HashSet<>(Set.of(1L)))
                .build();
    }

    @AfterEach
    void tearDown() {
        testRoute = null;
        testRouteDto = null;
        testRouteView = null;
    }

    @Test
    @DisplayName("Creating a route should return a JSON object with 201 Created status")
    void post_createRoute_returnsObjWith201_Created() throws Exception {
        //given
        when(routeService.addRoute(any(RouteDto.class))).thenReturn(testRoute);
        when(viewMapperUtil.toRoadView(any(Route.class))).thenReturn(testRouteView);

        //when
        mockMvc.perform(
                        post("/api/routes")
                                .content(mapper.writeValueAsString(testRouteDto))
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.startOfWay").isString())
                .andExpect(jsonPath("$.endOfWay").isString())
                .andExpect(jsonPath("$.endOfWay").isString())
                .andExpect(jsonPath("$.transportsId").isArray())
                .andExpect(jsonPath("$.driversId").isArray())
                .andExpect(jsonPath("$.id").value(testRouteView.getId()))
                .andExpect(jsonPath("$.startOfWay").value(testRouteView.getStartOfWay()))
                .andExpect(jsonPath("$.endOfWay").value(testRouteView.getEndOfWay()))
                .andExpect(jsonPath("$.transportsId[0]").value(testRouteView.getTransportsId().stream().findFirst().get()))
                .andExpect(jsonPath("$.driversId[0]").value(testRouteView.getDriversId().stream().findFirst().get()));

        //then
        verify(routeService, times(1)).addRoute(routeDtoArgumentCaptor.capture());
        verify(viewMapperUtil, times(1)).toRoadView(routeArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Retrieving a route by ID should return a JSON object with 200 OK status")
    void get_findRouteById_returnObjWith200_Ok() throws Exception {
        //given
        when(routeService.findRouteById(anyLong())).thenReturn(testRoute);
        when(viewMapperUtil.toRoadView(any(Route.class))).thenReturn(testRouteView);

        //when
        mockMvc.perform(
                        get("/api/routes/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                )
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.startOfWay").isString())
                .andExpect(jsonPath("$.endOfWay").isString())
                .andExpect(jsonPath("$.endOfWay").isString())
                .andExpect(jsonPath("$.transportsId").isArray())
                .andExpect(jsonPath("$.driversId").isArray())
                .andExpect(jsonPath("$.id").value(testRouteView.getId()))
                .andExpect(jsonPath("$.startOfWay").value(testRouteView.getStartOfWay()))
                .andExpect(jsonPath("$.endOfWay").value(testRouteView.getEndOfWay()))
                .andExpect(jsonPath("$.transportsId[0]").value(testRouteView.getTransportsId().stream().findFirst().get()))
                .andExpect(jsonPath("$.driversId[0]").value(testRouteView.getDriversId().stream().findFirst().get()));

        //then
        verify(routeService, times(1)).findRouteById(longArgumentCaptor.capture());
        verify(viewMapperUtil, times(1)).toRoadView(routeArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Updating a route should return a JSON object with 200 OK status")
    void put_updateRoute_returnsObjWith200_ok() throws Exception {
        //given
        when(viewMapperUtil.toRoadView(any(Route.class))).thenReturn(testRouteView);
        when(routeService.updateRoute(anyLong(),any(RouteDto.class))).thenReturn(testRoute);

        //when
        mockMvc.perform(
                        put("/api/routes/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(testRouteDto))
                                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.startOfWay").isString())
                .andExpect(jsonPath("$.endOfWay").isString())
                .andExpect(jsonPath("$.endOfWay").isString())
                .andExpect(jsonPath("$.transportsId").isArray())
                .andExpect(jsonPath("$.driversId").isArray())
                .andExpect(jsonPath("$.id").value(testRouteView.getId()))
                .andExpect(jsonPath("$.startOfWay").value(testRouteView.getStartOfWay()))
                .andExpect(jsonPath("$.endOfWay").value(testRouteView.getEndOfWay()))
                .andExpect(jsonPath("$.transportsId[0]").value(testRouteView.getTransportsId().stream().findFirst().get()))
                .andExpect(jsonPath("$.driversId[0]").value(testRouteView.getDriversId().stream().findFirst().get()));

        //then
        verify(routeService, times(1)).updateRoute(longArgumentCaptor.capture(), routeDtoArgumentCaptor.capture());
        verify(viewMapperUtil, times(1)).toRoadView(routeArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Deleting a route by ID should return 200 OK status")
    void delete_deleteRouteById_returnsNothingWith200_Ok() throws Exception {
        //given
        when(routeService.deleteRouteById(anyLong())).thenReturn(true);

        //when
        mockMvc.perform(
                        delete("/api/routes/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(status().isOk());

        //then
        verify(routeService, times(1)).deleteRouteById(longArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Retrieving all routes should return a list of JSON objects with 200 OK status")
    void get_findAllRoutes_returnsListWith200_Ok() throws Exception {
        //given
        when(routeService.findAllRoutes()).thenReturn(List.of(testRoute));
        when(viewMapperUtil.toRoadView(any(Route.class))).thenReturn(testRouteView);

        //when
        mockMvc.perform(
                        get("/api/routes")

                )
                .andDo(print())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].startOfWay").isString())
                .andExpect(jsonPath("$[0].endOfWay").isString())
                .andExpect(jsonPath("$[0].endOfWay").isString())
                .andExpect(jsonPath("$[0].transportsId").isArray())
                .andExpect(jsonPath("$[0].driversId").isArray())
                .andExpect(jsonPath("$[0].id").value(testRouteView.getId()))
                .andExpect(jsonPath("$[0].startOfWay").value(testRouteView.getStartOfWay()))
                .andExpect(jsonPath("$[0].endOfWay").value(testRouteView.getEndOfWay()))
                .andExpect(jsonPath("$[0].transportsId[0]").value(testRouteView.getTransportsId().stream().findFirst().get()))
                .andExpect(jsonPath("$[0].driversId[0]").value(testRouteView.getDriversId().stream().findFirst().get()));

        //then
        verify(routeService, times(1)).findAllRoutes();
        verify(viewMapperUtil, times(1)).toRoadView(routeArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Retrieving all routes without transport should return a list of JSON objects with 200 OK status")
    void get_findAllRoutesWithoutTransport_returnsListWith200_Ok() throws Exception {
        //given
        when(viewMapperUtil.toRoadView(any(Route.class))).thenReturn(testRouteView);
        when(routeService.findAllRoutesWithoutTransport()).thenReturn(List.of(testRoute));

        //when
        mockMvc.perform(
                        get("/api/routes/all_without_transport")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].startOfWay").isString())
                .andExpect(jsonPath("$[0].endOfWay").isString())
                .andExpect(jsonPath("$[0].endOfWay").isString())
                .andExpect(jsonPath("$[0].transportsId").isArray())
                .andExpect(jsonPath("$[0].driversId").isArray())
                .andExpect(jsonPath("$[0].id").value(testRouteView.getId()))
                .andExpect(jsonPath("$[0].startOfWay").value(testRouteView.getStartOfWay()))
                .andExpect(jsonPath("$[0].endOfWay").value(testRouteView.getEndOfWay()))
                .andExpect(jsonPath("$[0].transportsId[0]").value(testRouteView.getTransportsId().stream().findFirst().get()))
                .andExpect(jsonPath("$[0].driversId[0]").value(testRouteView.getDriversId().stream().findFirst().get()));

        //then
        verify(viewMapperUtil, times(1)).toRoadView(routeArgumentCaptor.capture());
        verify(routeService, times(1)).findAllRoutesWithoutTransport();
    }
}