package com.vehicle_service_spring_v_3.transport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicle_service_spring_v_3.drivers.model.DriverQualificationEnum;
import com.vehicle_service_spring_v_3.drivers.model.dto.ReturnedConverter;
import com.vehicle_service_spring_v_3.transports.ReturnedTransport;
import com.vehicle_service_spring_v_3.transports.TransportDto;
import com.vehicle_service_spring_v_3.transports.TransportServiceI;
import com.vehicle_service_spring_v_3.transports.model.Bus;
import com.vehicle_service_spring_v_3.transports.resource.TransportController;
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

@WebMvcTest(TransportController.class)
class TransportControllerTest {
    // beans
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    TransportServiceI transportService;

    // captors
    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;
    @Captor
    ArgumentCaptor<TransportDto> transportDtoArgumentCaptor;
    @Captor
    ArgumentCaptor<String> brandArgumentCaptor;

    // objects
    Bus transportTest;
    TransportDto transportDtoTest;
    ReturnedTransport returnedTransportTest;

    @BeforeEach
    void setUp() {
        transportTest = Bus.builder()
                .type("testType")
                .amountOfDoors(3)
                .build();
        transportTest.setId(1L);
        transportTest.setBrandOfTransport("testBrand");
        transportTest.setDriverQualificationEnum(DriverQualificationEnum.BUS_DRIVER);
        transportTest.setAmountOfPassengers(30);

        transportDtoTest = TransportDto.builder()
                .id(1L)
                .amountOfDoors(3)
                .amountOfPassengers(30)
                .brandOfTransport("testBrand")
                .type("testType")
                .driverQualificationEnum(DriverQualificationEnum.BUS_DRIVER.name())
                .build();

        returnedTransportTest = ReturnedConverter.convertToReturnedTransport(transportTest);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void post_createTransport_returnsObjWith201_Created() throws Exception {
        //given

        //when
        when(transportService.addTransport(any(TransportDto.class))).thenReturn(transportTest);

        //then
        mockMvc.perform(
                        post("/api/transports")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(transportDtoTest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.brandOfTransport").isString())
                .andExpect(jsonPath("$.amountOfPassengers").isNumber())
                .andExpect(jsonPath("$.driverQualificationEnum").isString())
                .andExpect(jsonPath("$.type").isString())
                .andExpect(jsonPath("$.amountOfDoors").isNumber())
                .andExpect(jsonPath("$.id").value(returnedTransportTest.getId()))
                .andExpect(jsonPath("$.brandOfTransport").value(returnedTransportTest.getBrandOfTransport()))
                .andExpect(jsonPath("$.amountOfPassengers").value(returnedTransportTest.getAmountOfPassengers()))
                .andExpect(jsonPath("$.driverQualificationEnum").value(returnedTransportTest.getDriverQualificationEnum()))
                .andExpect(jsonPath("$.type").value(returnedTransportTest.getType()))
                .andExpect(jsonPath("$.amountOfDoors").value(returnedTransportTest.getAmountOfDoors()));

        verify(transportService, times(1)).addTransport(transportDtoArgumentCaptor.capture());
    }

    @Test
    void get_findTransportById_returnsObjWith200_Ok() throws Exception {
        //given
        //when
        when(transportService.findTransportById(anyLong())).thenReturn(Optional.of(transportTest));

        //then
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
                .andExpect(jsonPath("$.id").value(returnedTransportTest.getId()))
                .andExpect(jsonPath("$.brandOfTransport").value(returnedTransportTest.getBrandOfTransport()))
                .andExpect(jsonPath("$.amountOfPassengers").value(returnedTransportTest.getAmountOfPassengers()))
                .andExpect(jsonPath("$.driverQualificationEnum").value(returnedTransportTest.getDriverQualificationEnum()))
                .andExpect(jsonPath("$.type").value(returnedTransportTest.getType()))
                .andExpect(jsonPath("$.amountOfDoors").value(returnedTransportTest.getAmountOfDoors()));

        verify(transportService, times(1)).findTransportById(longArgumentCaptor.capture());
    }

    @Test
    void put_updateTransport_returnsObjWith200_Ok() throws Exception {
        //given
        //when
        when(transportService.updateTransport(any(TransportDto.class))).thenReturn(transportTest);

        //then
        mockMvc.perform(
                        put("/api/transports")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(transportDtoTest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.brandOfTransport").isString())
                .andExpect(jsonPath("$.amountOfPassengers").isNumber())
                .andExpect(jsonPath("$.driverQualificationEnum").isString())
                .andExpect(jsonPath("$.type").isString())
                .andExpect(jsonPath("$.amountOfDoors").isNumber())
                .andExpect(jsonPath("$.id").value(returnedTransportTest.getId()))
                .andExpect(jsonPath("$.brandOfTransport").value(returnedTransportTest.getBrandOfTransport()))
                .andExpect(jsonPath("$.amountOfPassengers").value(returnedTransportTest.getAmountOfPassengers()))
                .andExpect(jsonPath("$.driverQualificationEnum").value(returnedTransportTest.getDriverQualificationEnum()))
                .andExpect(jsonPath("$.type").value(returnedTransportTest.getType()))
                .andExpect(jsonPath("$.amountOfDoors").value(returnedTransportTest.getAmountOfDoors()));

        verify(transportService, times(1)).updateTransport(transportDtoArgumentCaptor.capture());
    }

    @Test
    void delete_deleteTransportById_returnsNothingWith200_Ok() throws Exception {
        //given
        //when
        when(transportService.deleteTransportById(anyLong())).thenReturn(true);

        //then
        mockMvc.perform(
                        delete("/api/transports/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(transportService, times(1)).deleteTransportById(longArgumentCaptor.capture());
    }

    @Test
    void get_findAllTransports_returnsListWith200_Ok() throws Exception {
        //given
        //when
        when(transportService.findAllTransports()).thenReturn(List.of(transportTest));

        //then
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
                .andExpect(jsonPath("$[0].id").value(returnedTransportTest.getId()))
                .andExpect(jsonPath("$[0].brandOfTransport").value(returnedTransportTest.getBrandOfTransport()))
                .andExpect(jsonPath("$[0].amountOfPassengers").value(returnedTransportTest.getAmountOfPassengers()))
                .andExpect(jsonPath("$[0].driverQualificationEnum").value(returnedTransportTest.getDriverQualificationEnum()))
                .andExpect(jsonPath("$[0].type").value(returnedTransportTest.getType()))
                .andExpect(jsonPath("$[0].amountOfDoors").value(returnedTransportTest.getAmountOfDoors()));

        verify(transportService, times(1)).findAllTransports();
    }

    @Test
    void get_findTransportByBrand_returnsListWith200_Ok() throws Exception {
        //given
        //when
        when(transportService.findTransportByBrand(anyString())).thenReturn(List.of(transportTest));

        //then
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
                .andExpect(jsonPath("$[0].id").value(returnedTransportTest.getId()))
                .andExpect(jsonPath("$[0].brandOfTransport").value(returnedTransportTest.getBrandOfTransport()))
                .andExpect(jsonPath("$[0].amountOfPassengers").value(returnedTransportTest.getAmountOfPassengers()))
                .andExpect(jsonPath("$[0].driverQualificationEnum").value(returnedTransportTest.getDriverQualificationEnum()))
                .andExpect(jsonPath("$[0].type").value(returnedTransportTest.getType()))
                .andExpect(jsonPath("$[0].amountOfDoors").value(returnedTransportTest.getAmountOfDoors()));

        verify(transportService, times(1)).findTransportByBrand(brandArgumentCaptor.capture());
    }

    @Test
    void get_findTransportWithoutDriver_returnsListWith200_Ok() throws Exception {
        //given
        //when
        when(transportService.findTransportWithoutDriver()).thenReturn(List.of(transportTest));

        //then
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
                .andExpect(jsonPath("$[0].id").value(returnedTransportTest.getId()))
                .andExpect(jsonPath("$[0].brandOfTransport").value(returnedTransportTest.getBrandOfTransport()))
                .andExpect(jsonPath("$[0].amountOfPassengers").value(returnedTransportTest.getAmountOfPassengers()))
                .andExpect(jsonPath("$[0].driverQualificationEnum").value(returnedTransportTest.getDriverQualificationEnum()))
                .andExpect(jsonPath("$[0].type").value(returnedTransportTest.getType()))
                .andExpect(jsonPath("$[0].amountOfDoors").value(returnedTransportTest.getAmountOfDoors()))
                .andExpect(jsonPath("$[0].driversId").isEmpty());

        verify(transportService, times(1)).findTransportWithoutDriver();
    }

    @Test
    void put_addTransportToRoute_returnsNothingWith200_Ok() throws Exception {
        //given
        //when
        when(transportService.addTransportToRoute(anyLong(), anyLong())).thenReturn(true);

        //then
        mockMvc.perform(
                        put("/api/transports/transport_to_route/1/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(transportService, times(1)).addTransportToRoute(longArgumentCaptor.capture(), longArgumentCaptor.capture());
    }

    @Test
    void delete_removeTransportFromRoute_returnsNothingWith200_Ok() throws Exception {
        //given
        //when
        when(transportService.removeTransportFromRoute(anyLong(), anyLong())).thenReturn(true);

        //then
        mockMvc.perform(
                        delete("/api/transports/transport_from_route/1/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(transportService, times(1)).removeTransportFromRoute(longArgumentCaptor.capture(), longArgumentCaptor.capture());
    }
}