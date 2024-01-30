package com.vehicle_service_spring_v2.transport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicle_service_spring_v2.auth.services.AuthenticationService;
import com.vehicle_service_spring_v2.auth.services.JwtService;
import com.vehicle_service_spring_v2.auth.users.UserRepository;
import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.drivers.model.DriverQualificationEnum;
import com.vehicle_service_spring_v2.routes.model.Route;
import com.vehicle_service_spring_v2.transports.TransportServiceI;
import com.vehicle_service_spring_v2.transports.model.Bus;
import com.vehicle_service_spring_v2.transports.model.Transport;
import com.vehicle_service_spring_v2.transports.model.dto.TransportDto;
import com.vehicle_service_spring_v2.transports.model.dto.view.TransportView;
import com.vehicle_service_spring_v2.transports.resource.TransportResource;
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

@WebMvcTest(TransportResource.class)
@ActiveProfiles("test")
@WithMockUser(
        username = "admin",
        roles = "ADMIN",
        value = "admin"
)
class TransportControllerTest {
    // beans
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    TransportServiceI transportService;
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
    ArgumentCaptor<Long> longArgumentCaptor;
    @Captor
    ArgumentCaptor<TransportDto> transportDtoArgumentCaptor;
    @Captor
    ArgumentCaptor<String> brandArgumentCaptor;
    @Captor
    ArgumentCaptor<Transport> transportArgumentCaptor;

    // objects
    Bus transportTest;
    TransportDto transportDtoTest;
    TransportView transportViewTest;

    @BeforeEach
    void setUp() {
        transportTest = Bus.builder()
                .id(1L)
                .brandOfTransport("testBrand")
                .driverQualificationEnum(DriverQualificationEnum.BUS_DRIVER)
                .amountOfPassengers(30)
                .type("testType")
                .amountOfDoors(3)
                .drivers(new HashSet<>(Set.of(Driver.builder()
                        .id(1L)
                        .build())))
                .route(new HashSet<>(Set.of(Route.builder()
                        .id(1L)
                        .build())))
                .build();

        transportDtoTest = TransportDto.builder()
                .id(1L)
                .amountOfDoors(3)
                .amountOfPassengers(30)
                .brandOfTransport("testBrand")
                .type("testType")
                .driverQualificationEnum(DriverQualificationEnum.BUS_DRIVER.name())
                .build();

        transportViewTest = TransportView.builder()
                .id(1L)
                .brandOfTransport("testBrand")
                .driverQualificationEnum(DriverQualificationEnum.BUS_DRIVER.name())
                .amountOfPassengers(30)
                .type("testType")
                .amountOfDoors(3)
                .driversId(new HashSet<>(Set.of(1L)))
                .routesId(new HashSet<>(Set.of(1L)))
                .build();
    }

    @AfterEach
    void tearDown() {
        transportTest = null;
        transportViewTest = null;
        transportDtoTest = null;
    }

    @Test
    @DisplayName("Creating a new transport should return a JSON object with 201 Created status")
    void post_createTransport_returnsObjWith201_Created() throws Exception {
        //given
        when(transportService.addTransport(any(TransportDto.class))).thenReturn(transportTest);
        when(viewMapperUtil.toTransportView(any(Transport.class))).thenReturn(transportViewTest);

        //when
        mockMvc.perform(
                        post("/api/transports")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(transportDtoTest))
                                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.brandOfTransport").isString())
                .andExpect(jsonPath("$.amountOfPassengers").isNumber())
                .andExpect(jsonPath("$.driverQualificationEnum").isString())
                .andExpect(jsonPath("$.type").isString())
                .andExpect(jsonPath("$.amountOfDoors").isNumber())
                .andExpect(jsonPath("$.driversId").isArray())
                .andExpect(jsonPath("$.routesId").isArray())
                .andExpect(jsonPath("$.id").value(transportViewTest.getId()))
                .andExpect(jsonPath("$.brandOfTransport").value(transportViewTest.getBrandOfTransport()))
                .andExpect(jsonPath("$.amountOfPassengers").value(transportViewTest.getAmountOfPassengers()))
                .andExpect(jsonPath("$.driverQualificationEnum").value(transportViewTest.getDriverQualificationEnum()))
                .andExpect(jsonPath("$.type").value(transportViewTest.getType()))
                .andExpect(jsonPath("$.amountOfDoors").value(transportViewTest.getAmountOfDoors()))
                .andExpect(jsonPath("$.driversId[0]").value(transportViewTest.getDriversId().stream().findFirst().get()))
                .andExpect(jsonPath("$.routesId[0]").value(transportViewTest.getRoutesId().stream().findFirst().get()));

        //then
        verify(transportService, times(1)).addTransport(transportDtoArgumentCaptor.capture());
        verify(viewMapperUtil, times(1)).toTransportView(transportArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Retrieving a transport by ID should return a JSON object with 200 OK status")
    void get_findTransportById_returnsObjWith200_Ok() throws Exception {
        //given
        when(transportService.findTransportById(anyLong())).thenReturn(transportTest);
        when(viewMapperUtil.toTransportView(any(Transport.class))).thenReturn(transportViewTest);

        //when
        mockMvc.perform(
                        get("/api/transports/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.brandOfTransport").isString())
                .andExpect(jsonPath("$.amountOfPassengers").isNumber())
                .andExpect(jsonPath("$.driverQualificationEnum").isString())
                .andExpect(jsonPath("$.type").isString())
                .andExpect(jsonPath("$.amountOfDoors").isNumber())
                .andExpect(jsonPath("$.driversId").isArray())
                .andExpect(jsonPath("$.routesId").isArray())
                .andExpect(jsonPath("$.id").value(transportViewTest.getId()))
                .andExpect(jsonPath("$.brandOfTransport").value(transportViewTest.getBrandOfTransport()))
                .andExpect(jsonPath("$.amountOfPassengers").value(transportViewTest.getAmountOfPassengers()))
                .andExpect(jsonPath("$.driverQualificationEnum").value(transportViewTest.getDriverQualificationEnum()))
                .andExpect(jsonPath("$.type").value(transportViewTest.getType()))
                .andExpect(jsonPath("$.amountOfDoors").value(transportViewTest.getAmountOfDoors()))
                .andExpect(jsonPath("$.driversId[0]").value(transportViewTest.getDriversId().stream().findFirst().get()))
                .andExpect(jsonPath("$.routesId[0]").value(transportViewTest.getRoutesId().stream().findFirst().get()));

        //then
        verify(transportService, times(1)).findTransportById(longArgumentCaptor.capture());
        verify(viewMapperUtil, times(1)).toTransportView(transportArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Updating a transport should return a JSON object with 200 OK status")
    void put_updateTransport_returnsObjWith200_Ok() throws Exception {
        //given
        when(transportService.updateTransport(anyLong(), any(TransportDto.class))).thenReturn(transportTest);
        when(viewMapperUtil.toTransportView(any(Transport.class))).thenReturn(transportViewTest);

        //when
        mockMvc.perform(
                        put("/api/transports")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(transportDtoTest))
                                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.brandOfTransport").isString())
                .andExpect(jsonPath("$.amountOfPassengers").isNumber())
                .andExpect(jsonPath("$.driverQualificationEnum").isString())
                .andExpect(jsonPath("$.type").isString())
                .andExpect(jsonPath("$.amountOfDoors").isNumber())
                .andExpect(jsonPath("$.driversId").isArray())
                .andExpect(jsonPath("$.routesId").isArray())
                .andExpect(jsonPath("$.id").value(transportViewTest.getId()))
                .andExpect(jsonPath("$.brandOfTransport").value(transportViewTest.getBrandOfTransport()))
                .andExpect(jsonPath("$.amountOfPassengers").value(transportViewTest.getAmountOfPassengers()))
                .andExpect(jsonPath("$.driverQualificationEnum").value(transportViewTest.getDriverQualificationEnum()))
                .andExpect(jsonPath("$.type").value(transportViewTest.getType()))
                .andExpect(jsonPath("$.amountOfDoors").value(transportViewTest.getAmountOfDoors()))
                .andExpect(jsonPath("$.driversId[0]").value(transportViewTest.getDriversId().stream().findFirst().get()))
                .andExpect(jsonPath("$.routesId[0]").value(transportViewTest.getRoutesId().stream().findFirst().get()));

        //then
        verify(viewMapperUtil, times(1)).toTransportView(transportArgumentCaptor.capture());
        verify(transportService, times(1)).updateTransport(longArgumentCaptor.capture(),transportDtoArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Deleting a transport by ID should return 200 OK status")
    void delete_deleteTransportById_returnsNothingWith200_Ok() throws Exception {
        //given
        when(transportService.deleteTransportById(anyLong())).thenReturn(true);

        //when
        mockMvc.perform(
                        delete("/api/transports/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(status().isOk());

        //then
        verify(transportService, times(1)).deleteTransportById(longArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Retrieving all transports should return a list with 200 OK status")
    void get_findAllTransports_returnsListWith200_Ok() throws Exception {
        //given
        when(transportService.findAllTransports()).thenReturn(List.of(transportTest));
        when(viewMapperUtil.toTransportView(any(Transport.class))).thenReturn(transportViewTest);

        //when
        mockMvc.perform(
                        get("/api/transports")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].brandOfTransport").isString())
                .andExpect(jsonPath("$[0].amountOfPassengers").isNumber())
                .andExpect(jsonPath("$[0].driverQualificationEnum").isString())
                .andExpect(jsonPath("$[0].type").isString())
                .andExpect(jsonPath("$[0].amountOfDoors").isNumber())
                .andExpect(jsonPath("$[0].driversId").isArray())
                .andExpect(jsonPath("$[0].routesId").isArray())
                .andExpect(jsonPath("$[0].id").value(transportViewTest.getId()))
                .andExpect(jsonPath("$[0].brandOfTransport").value(transportViewTest.getBrandOfTransport()))
                .andExpect(jsonPath("$[0].amountOfPassengers").value(transportViewTest.getAmountOfPassengers()))
                .andExpect(jsonPath("$[0].driverQualificationEnum").value(transportViewTest.getDriverQualificationEnum()))
                .andExpect(jsonPath("$[0].type").value(transportViewTest.getType()))
                .andExpect(jsonPath("$[0].amountOfDoors").value(transportViewTest.getAmountOfDoors()))
                .andExpect(jsonPath("$[0].driversId[0]").value(transportViewTest.getDriversId().stream().findFirst().get()))
                .andExpect(jsonPath("$[0].routesId[0]").value(transportViewTest.getRoutesId().stream().findFirst().get()));

        //then
        verify(transportService, times(1)).findAllTransports();
        verify(viewMapperUtil, times(1)).toTransportView(transportArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Retrieving transports by brand should return a list with 200 OK status")
    void get_findTransportByBrand_returnsListWith200_Ok() throws Exception {
        //given
        when(transportService.findTransportByBrand(anyString())).thenReturn(List.of(transportTest));
        when(viewMapperUtil.toTransportView(any(Transport.class))).thenReturn(transportViewTest);

        //when
        mockMvc.perform(
                        get("/api/transports/by_brand/brand")
                                .param("brand", "testBrand")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].brandOfTransport").isString())
                .andExpect(jsonPath("$[0].amountOfPassengers").isNumber())
                .andExpect(jsonPath("$[0].driverQualificationEnum").isString())
                .andExpect(jsonPath("$[0].type").isString())
                .andExpect(jsonPath("$[0].amountOfDoors").isNumber())
                .andExpect(jsonPath("$[0].driversId").isArray())
                .andExpect(jsonPath("$[0].routesId").isArray())
                .andExpect(jsonPath("$[0].id").value(transportViewTest.getId()))
                .andExpect(jsonPath("$[0].brandOfTransport").value(transportViewTest.getBrandOfTransport()))
                .andExpect(jsonPath("$[0].amountOfPassengers").value(transportViewTest.getAmountOfPassengers()))
                .andExpect(jsonPath("$[0].driverQualificationEnum").value(transportViewTest.getDriverQualificationEnum()))
                .andExpect(jsonPath("$[0].type").value(transportViewTest.getType()))
                .andExpect(jsonPath("$[0].amountOfDoors").value(transportViewTest.getAmountOfDoors()))
                .andExpect(jsonPath("$[0].driversId[0]").value(transportViewTest.getDriversId().stream().findFirst().get()))
                .andExpect(jsonPath("$[0].routesId[0]").value(transportViewTest.getRoutesId().stream().findFirst().get()));

        //then
        verify(transportService, times(1)).findTransportByBrand(brandArgumentCaptor.capture());
        verify(viewMapperUtil, times(1)).toTransportView(transportArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Retrieving transports without drivers should return a list with 200 OK status")
    void get_findTransportWithoutDriver_returnsListWith200_Ok() throws Exception {
        //given
        when(viewMapperUtil.toTransportView(any(Transport.class))).thenReturn(transportViewTest);
        when(transportService.findTransportWithoutDriver()).thenReturn(List.of(transportTest));

        //when
        mockMvc.perform(
                        get("/api/transports/without_driver")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].brandOfTransport").isString())
                .andExpect(jsonPath("$[0].amountOfPassengers").isNumber())
                .andExpect(jsonPath("$[0].driverQualificationEnum").isString())
                .andExpect(jsonPath("$[0].type").isString())
                .andExpect(jsonPath("$[0].amountOfDoors").isNumber())
                .andExpect(jsonPath("$[0].driversId").isArray())
                .andExpect(jsonPath("$[0].routesId").isArray())
                .andExpect(jsonPath("$[0].id").value(transportViewTest.getId()))
                .andExpect(jsonPath("$[0].brandOfTransport").value(transportViewTest.getBrandOfTransport()))
                .andExpect(jsonPath("$[0].amountOfPassengers").value(transportViewTest.getAmountOfPassengers()))
                .andExpect(jsonPath("$[0].driverQualificationEnum").value(transportViewTest.getDriverQualificationEnum()))
                .andExpect(jsonPath("$[0].type").value(transportViewTest.getType()))
                .andExpect(jsonPath("$[0].amountOfDoors").value(transportViewTest.getAmountOfDoors()))
                .andExpect(jsonPath("$[0].driversId[0]").value(transportViewTest.getDriversId().stream().findFirst().get()))
                .andExpect(jsonPath("$[0].routesId[0]").value(transportViewTest.getRoutesId().stream().findFirst().get()));

        //then
        verify(viewMapperUtil, times(1)).toTransportView(transportArgumentCaptor.capture());
        verify(transportService, times(1)).findTransportWithoutDriver();
    }

    @Test
    @DisplayName("Adding a transport to a route should return nothing with 200 OK status")
    void put_addTransportToRoute_returnsNothingWith200_Ok() throws Exception {
        //given
        when(transportService.addTransportToRoute(anyLong(), anyLong())).thenReturn(true);

        //when
        mockMvc.perform(
                        put("/api/transports/transport_to_route/1/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(status().isOk());

        //then
        verify(transportService, times(1)).addTransportToRoute(longArgumentCaptor.capture(), longArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Removing a transport from a route should return nothing with 200 OK status")
    void delete_removeTransportFromRoute_returnsNothingWith200_Ok() throws Exception {
        //given
        when(transportService.removeTransportFromRoute(anyLong(), anyLong())).thenReturn(true);

        //when
        mockMvc.perform(
                        delete("/api/transports/transport_from_route/1/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(status().isOk());

        //then
        verify(transportService, times(1)).removeTransportFromRoute(longArgumentCaptor.capture(), longArgumentCaptor.capture());
    }
}