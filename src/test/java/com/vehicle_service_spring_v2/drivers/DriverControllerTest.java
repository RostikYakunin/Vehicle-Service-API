package com.vehicle_service_spring_v2.drivers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicle_service_spring_v2.auth.services.AuthenticationService;
import com.vehicle_service_spring_v2.auth.services.JwtService;
import com.vehicle_service_spring_v2.auth.users.UserRepository;
import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.drivers.model.DriverQualificationEnum;
import com.vehicle_service_spring_v2.drivers.model.dto.DriverDto;
import com.vehicle_service_spring_v2.drivers.model.dto.DriverView;
import com.vehicle_service_spring_v2.drivers.resouce.DriverResource;
import com.vehicle_service_spring_v2.routes.model.Route;
import com.vehicle_service_spring_v2.transports.model.Bus;
import com.vehicle_service_spring_v2.transports.model.Transport;
import com.vehicle_service_spring_v2.transports.model.dto.TransportView;
import com.vehicle_service_spring_v2.utils.ViewMapperUtil;
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

@WebMvcTest(DriverResource.class)
@ActiveProfiles("test")
@WithMockUser(
        username = "admin",
        roles = "ADMIN",
        value = "admin"
)
class DriverControllerTest {
    // beans
    @MockBean
    DriverServiceI driverService;
    @Autowired
    MockMvc mockMvc;
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
    ArgumentCaptor<DriverDto> driverDtoArgumentCaptor;
    @Captor
    ArgumentCaptor<String> surnameArgumentCaptor;
    @Captor
    ArgumentCaptor<Long> id;
    @Captor
    ArgumentCaptor<Driver> driverArgumentCaptor;
    @Captor
    ArgumentCaptor<Transport> transportArgumentCaptor;

    // tested objects
    DriverDto driverDtoTest;
    Driver driverBusTest;
    DriverView driverViewTest;
    Bus bus;
    TransportView busViewTest;

    @BeforeEach
    void setUp() {
        //given
        driverDtoTest = DriverDto.builder()
                .id(1L)
                .nameOfDriver("testName")
                .surnameOfDriver("testSurname")
                .phoneNumber("testPhoneNumber")
                .qualificationEnum("testQualificationEnum")
                .build();

        driverBusTest = Driver.builder()
                .id(1L)
                .nameOfDriver("testName")
                .surnameOfDriver("testSurname")
                .phoneNumber("testPhoneNumber")
                .qualificationEnum(DriverQualificationEnum.BUS_DRIVER)
                .route(new HashSet<>(Set.of(Route.builder()
                        .id(1L)
                        .build())))
                .transport(new HashSet<>(Set.of(Bus.builder()
                        .id(1L)
                        .build())))
                .build();

        driverViewTest = DriverView.builder()
                .id(1L)
                .nameOfDriver("testName")
                .surnameOfDriver("testSurname")
                .phoneNumber("testPhoneNumber")
                .qualificationEnum(DriverQualificationEnum.BUS_DRIVER)
                .transportId(new HashSet<>(Set.of(1L)))
                .routeId(new HashSet<>(Set.of(1L)))
                .build();

        bus = Bus.builder()
                .id(1L)
                .brandOfTransport("testBrand")
                .driverQualificationEnum(DriverQualificationEnum.BUS_DRIVER)
                .type("testType")
                .amountOfDoors(3)
                .drivers(new HashSet<>(Set.of(Driver.builder()
                        .id(1L)
                        .build())))
                .route(new HashSet<>(Set.of(Route.builder()
                        .id(1L)
                        .build())))
                .build();

        busViewTest = TransportView.builder()
                .id(1L)
                .brandOfTransport("testBrand")
                .driverQualificationEnum(DriverQualificationEnum.BUS_DRIVER.name())
                .type("testType")
                .amountOfDoors(3)
                .driversId(new HashSet<>(Set.of(1L)))
                .routesId(new HashSet<>(Set.of(1L)))
                .build();
    }

    @Test
    @DisplayName("Creating a new driver should return a JSON object with 201 Created status")
    void post_createNewDriver_returnsObjWithStatus201_Created() throws Exception {
        //given
        when(driverService.addDriver(any(DriverDto.class))).thenReturn(driverBusTest);
        when(viewMapperUtil.toDriverView(driverBusTest)).thenReturn(driverViewTest);

        //when
        mockMvc.perform(
                        post("/api/drivers")
                                .content(mapper.writeValueAsString(driverDtoTest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nameOfDriver").isString())
                .andExpect(jsonPath("$.surnameOfDriver").isString())
                .andExpect(jsonPath("$.phoneNumber").isString())
                .andExpect(jsonPath("$.qualificationEnum").isString())
                .andExpect(jsonPath("$.transportId").isArray())
                .andExpect(jsonPath("$.routeId").isArray())
                .andExpect(jsonPath("$.id").value(driverViewTest.getId()))
                .andExpect(jsonPath("$.nameOfDriver").value(driverViewTest.getNameOfDriver()))
                .andExpect(jsonPath("$.surnameOfDriver").value(driverViewTest.getSurnameOfDriver()))
                .andExpect(jsonPath("$.phoneNumber").value(driverViewTest.getPhoneNumber()))
                .andExpect(jsonPath("$.qualificationEnum").value(driverViewTest.getQualificationEnum().name()))
                .andExpect(jsonPath("$.transportId[0]").value(driverViewTest.getTransportId().stream().findFirst().get()))
                .andExpect(jsonPath("$.routeId[0]").value(driverViewTest.getRouteId().stream().findFirst().get()));

        //then
        verify(driverService, times(1)).addDriver(driverDtoArgumentCaptor.capture());
        verify(viewMapperUtil, times(1)).toDriverView(driverArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Retrieving a driver by ID should return a JSON object with 200 OK status")
    void get_findDriverById_returnsObjWithStatus200_Ok() throws Exception {
        //given
        when(viewMapperUtil.toDriverView(driverBusTest)).thenReturn(driverViewTest);
        when(driverService.findDriverById(anyLong())).thenReturn(driverBusTest);

        //when
        mockMvc.perform(
                        get("/api/drivers/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nameOfDriver").isString())
                .andExpect(jsonPath("$.surnameOfDriver").isString())
                .andExpect(jsonPath("$.phoneNumber").isString())
                .andExpect(jsonPath("$.qualificationEnum").isString())
                .andExpect(jsonPath("$.transportId").isArray())
                .andExpect(jsonPath("$.routeId").isArray())
                .andExpect(jsonPath("$.id").value(driverViewTest.getId()))
                .andExpect(jsonPath("$.nameOfDriver").value(driverViewTest.getNameOfDriver()))
                .andExpect(jsonPath("$.surnameOfDriver").value(driverViewTest.getSurnameOfDriver()))
                .andExpect(jsonPath("$.phoneNumber").value(driverViewTest.getPhoneNumber()))
                .andExpect(jsonPath("$.qualificationEnum").value(driverViewTest.getQualificationEnum().name()))
                .andExpect(jsonPath("$.transportId[0]").value(driverViewTest.getTransportId().stream().findFirst().get()))
                .andExpect(jsonPath("$.routeId[0]").value(driverViewTest.getRouteId().stream().findFirst().get()));

        //then
        verify(viewMapperUtil, times(1)).toDriverView(driverArgumentCaptor.capture());
        verify(driverService, times(1)).findDriverById(id.capture());
    }

    @Test
    @DisplayName("Updating a driver should return a JSON object with 200 OK status")
    void put_updateDriver_returnsObjWith200_Ok() throws Exception {
        //given
        when(driverService.updateDriver(any(DriverDto.class))).thenReturn(driverBusTest);
        when(viewMapperUtil.toDriverView(driverBusTest)).thenReturn(driverViewTest);

        //when
        mockMvc.perform(
                        put("/api/drivers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(driverDtoTest))
                                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nameOfDriver").isString())
                .andExpect(jsonPath("$.surnameOfDriver").isString())
                .andExpect(jsonPath("$.phoneNumber").isString())
                .andExpect(jsonPath("$.qualificationEnum").isString())
                .andExpect(jsonPath("$.transportId").isArray())
                .andExpect(jsonPath("$.routeId").isArray())
                .andExpect(jsonPath("$.id").value(driverViewTest.getId()))
                .andExpect(jsonPath("$.nameOfDriver").value(driverViewTest.getNameOfDriver()))
                .andExpect(jsonPath("$.surnameOfDriver").value(driverViewTest.getSurnameOfDriver()))
                .andExpect(jsonPath("$.phoneNumber").value(driverViewTest.getPhoneNumber()))
                .andExpect(jsonPath("$.qualificationEnum").value(driverViewTest.getQualificationEnum().name()))
                .andExpect(jsonPath("$.transportId[0]").value(driverViewTest.getTransportId().stream().findFirst().get()))
                .andExpect(jsonPath("$.routeId[0]").value(driverViewTest.getRouteId().stream().findFirst().get()));

        //then
        verify(driverService, times(1)).updateDriver(driverDtoArgumentCaptor.capture());
        verify(viewMapperUtil, times(1)).toDriverView(driverArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Deleting a driver by ID should return 200 OK status")
    void delete_deleteDriverById_returnsNothingWith200_Ok() throws Exception {
        //given
        when(driverService.deleteDriverById(anyLong())).thenReturn(true);

        //when
        mockMvc.perform(
                        delete("/api/drivers/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(status().isOk());

        //then
        verify(driverService, times(1)).deleteDriverById(id.capture());
    }

    @Test
    @DisplayName("Adding a driver to a transport should return nothing with 200 OK status")
    void put_addDriverOnTransport_returnNothingWith200_Ok() throws Exception {
        //given
        when(driverService.addDriverOnTransport(anyLong(), anyLong())).thenReturn(true);

        //when
        mockMvc.perform(
                        put("/api/drivers/driver_to_transport/1/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(status().isOk());

        //then
        verify(driverService, times(1)).addDriverOnTransport(id.capture(), id.capture());
    }

    @Test
    @DisplayName("Retrieving all drivers with a specific surname should return a list with 200 OK status")
    void get_findAllDriverBySurname_returnsListWith200_Ok() throws Exception {
        //given
        when(driverService.findAllDriverBySurname(anyString())).thenReturn(List.of(driverBusTest));
        when(viewMapperUtil.toDriverView(any(Driver.class))).thenReturn(driverViewTest);

        //when
        mockMvc.perform(
                        get("/api/drivers/surname/surname")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("surname", "testSurname"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].nameOfDriver").isString())
                .andExpect(jsonPath("$[0].surnameOfDriver").isString())
                .andExpect(jsonPath("$[0].phoneNumber").isString())
                .andExpect(jsonPath("$[0].qualificationEnum").isString())
                .andExpect(jsonPath("$[0].transportId").isArray())
                .andExpect(jsonPath("$[0].routeId").isArray())
                .andExpect(jsonPath("$[0].id").value(driverViewTest.getId()))
                .andExpect(jsonPath("$[0].nameOfDriver").value(driverViewTest.getNameOfDriver()))
                .andExpect(jsonPath("$[0].surnameOfDriver").value(driverViewTest.getSurnameOfDriver()))
                .andExpect(jsonPath("$[0].phoneNumber").value(driverViewTest.getPhoneNumber()))
                .andExpect(jsonPath("$[0].qualificationEnum").value(driverViewTest.getQualificationEnum().name()))
                .andExpect(jsonPath("$[0].transportId[0]").value(driverViewTest.getTransportId().stream().findFirst().get()))
                .andExpect(jsonPath("$[0].routeId[0]").value(driverViewTest.getRouteId().stream().findFirst().get()));

        //then
        verify(driverService, times(1)).findAllDriverBySurname(surnameArgumentCaptor.capture());
        verify(viewMapperUtil, times(1)).toDriverView(driverArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Retrieving all drivers on a route should return a set with 200 OK status")
    void get_findAllDriverOnRoute_returnsSetWith200_Ok() throws Exception {
        //given
        when(viewMapperUtil.toDriverView(any(Driver.class))).thenReturn(driverViewTest);
        when(driverService.findAllDriverOnRoute(anyLong())).thenReturn(Set.of(driverBusTest));

        //when
        mockMvc.perform(
                        get("/api/drivers/drivers_on_route/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].nameOfDriver").isString())
                .andExpect(jsonPath("$[0].surnameOfDriver").isString())
                .andExpect(jsonPath("$[0].phoneNumber").isString())
                .andExpect(jsonPath("$[0].qualificationEnum").isString())
                .andExpect(jsonPath("$[0].transportId").isArray())
                .andExpect(jsonPath("$[0].routeId").isArray())
                .andExpect(jsonPath("$[0].id").value(driverViewTest.getId()))
                .andExpect(jsonPath("$[0].nameOfDriver").value(driverViewTest.getNameOfDriver()))
                .andExpect(jsonPath("$[0].surnameOfDriver").value(driverViewTest.getSurnameOfDriver()))
                .andExpect(jsonPath("$[0].phoneNumber").value(driverViewTest.getPhoneNumber()))
                .andExpect(jsonPath("$[0].qualificationEnum").value(driverViewTest.getQualificationEnum().name()))
                .andExpect(jsonPath("$[0].transportId[0]").value(driverViewTest.getTransportId().stream().findFirst().get()))
                .andExpect(jsonPath("$[0].routeId[0]").value(driverViewTest.getRouteId().stream().findFirst().get()));

        //then
        verify(viewMapperUtil, times(1)).toDriverView(driverArgumentCaptor.capture());
        verify(driverService, times(1)).findAllDriverOnRoute(id.capture());
    }

    @Test
    @DisplayName("Retrieving all transports without drivers should return a list with 200 OK status")
    void get_findAllTransportsWithoutDriver_returnsListWith200_Ok() throws Exception {
        //given
        when(viewMapperUtil.toTransportView(any(Transport.class))).thenReturn(busViewTest);
        when(driverService.findAllTransportsWithoutDriver()).thenReturn(List.of(bus));

        //when
        mockMvc.perform(
                        get("/api/drivers/transport_without_driver")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$").isNotEmpty());
        //then
        verify(driverService, times(1)).findAllTransportsWithoutDriver();
        verify(viewMapperUtil, times(1)).toTransportView(transportArgumentCaptor.capture());

    }

    @Test
    @DisplayName("Retrieving all drivers should return a list with 200 OK status")
    void get_findAllDrivers_returnsListWith200_Ok() throws Exception {
        //given
        when(viewMapperUtil.toDriverView(any(Driver.class))).thenReturn(driverViewTest);
        when(driverService.findAllDrivers()).thenReturn(List.of(driverBusTest));

        //when
        mockMvc.perform(
                        get("/api/drivers")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].nameOfDriver").isString())
                .andExpect(jsonPath("$[0].surnameOfDriver").isString())
                .andExpect(jsonPath("$[0].phoneNumber").isString())
                .andExpect(jsonPath("$[0].qualificationEnum").isString())
                .andExpect(jsonPath("$[0].id").value(driverViewTest.getId()))
                .andExpect(jsonPath("$[0].nameOfDriver").value(driverViewTest.getNameOfDriver()))
                .andExpect(jsonPath("$[0].surnameOfDriver").value(driverViewTest.getSurnameOfDriver()))
                .andExpect(jsonPath("$[0].phoneNumber").value(driverViewTest.getPhoneNumber()))
                .andExpect(jsonPath("$[0].qualificationEnum").value(driverViewTest.getQualificationEnum().name()));

        //then
        verify(viewMapperUtil, times(1)).toDriverView(driverArgumentCaptor.capture());
        verify(driverService, times(1)).findAllDrivers();
    }
}