package com.vehicle_service_spring_v2.drivers;

import com.vehicle_service_spring_v2.UnitTestBase;
import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.drivers.model.dto.DriverDto;
import com.vehicle_service_spring_v2.transports.TransportServiceImpl;
import com.vehicle_service_spring_v2.transports.model.Bus;
import com.vehicle_service_spring_v2.transports.model.Transport;
import com.vehicle_service_spring_v2.transports.model.dto.TransportDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DriverServiceImplTest extends UnitTestBase {
    DriverServiceImpl driverService;
    @Mock
    TransportServiceImpl transportService;
    @Captor
    ArgumentCaptor<Driver> driverArgumentCaptor;
    @Captor
    ArgumentCaptor<DriverDto> driverDtoArgumentCaptor;
    @Captor
    ArgumentCaptor<TransportDto> transportDtoArgumentCaptor;

    @BeforeEach
    void setUp() {
        super.configure();
        driverService = new DriverServiceImpl(mockedDriverRepo, mockedTransportRepo, mockedRouteRepo, transportService, mockedDriverDtoMapper, mockedTransportDtoMapper);
    }

    @AfterEach
    void turnDown() {
        super.destroy();
        driverService = null;
    }

    @Test
    @DisplayName("Should add driver with input driver dto and return driver")
    void addDriver_inputDriverDtoReturnDriver() {
        //given
        when(mockedDriverRepo.save(any(Driver.class))).thenReturn(testDriver);
        when(mockedDriverDtoMapper.toDriver(any(DriverDto.class))).thenReturn(testDriver);

        //when
        Driver actualDriver = driverService.addDriver(testDriverDto);

        //then
        verify(mockedDriverRepo, times(1)).save(driverArgumentCaptor.capture());
        verify(mockedDriverDtoMapper, times(1)).toDriver(driverDtoArgumentCaptor.capture());

        assertEquals(testDriver, actualDriver);
    }

    @Test
    @DisplayName("Should find driver by id with input long and return driver")
    void findDriverById_inputLongReturnOptionalOfDriver() {
        //given
        when(mockedDriverRepo.findById(anyLong())).thenReturn(Optional.of(testDriver));

        //when
        Driver actualDriver = driverService.findDriverById(1L);

        //then
        verify(mockedDriverRepo, times(1)).findById(longArgumentCaptor.capture());

        assertEquals(testDriver, actualDriver);
    }

    @Test
    @DisplayName("Should throw exception when finding driver by id with input long and return Optional empty")
    void findDriverById_inputLongReturnOptionalEmpty() {
        //given
        when(mockedDriverRepo.findById(anyLong())).thenReturn(Optional.empty());

        //when
        assertThrows(
                RuntimeException.class,
                () -> driverService.findDriverById(1L),
                "Driver with id=" + 1 + " not found !"
        );

        //then
        verify(mockedDriverRepo, times(1)).findById(longArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Should update driver with input driver dto and return driver")
    void updateDriver_inputDriverDtoReturnDriver() {
        //given
        when(mockedDriverRepo.save(any(Driver.class))).thenReturn(testDriver);
        when(mockedDriverDtoMapper.toDriver(any(DriverDto.class))).thenReturn(testDriver);

        //when
        Driver actualDriver = driverService.updateDriver(1L, testDriverDto);

        //then
        verify(mockedDriverRepo, times(1)).save(driverArgumentCaptor.capture());
        verify(mockedDriverDtoMapper, times(1)).toDriver(driverDtoArgumentCaptor.capture());

        assertEquals(testDriver, actualDriver);
    }

    @Test
    @DisplayName("Should delete driver by id with input long and return true")
    void deleteDriverById_inputLongReturnTrue() {
        //given
        testDriver.getTransport().clear();
        when(mockedDriverRepo.findById(anyLong())).thenReturn(Optional.of(testDriver));

        //when
        boolean actualResult = driverService.deleteDriverById(1L);

        //then
        verify(mockedDriverRepo, times(1)).findById(longArgumentCaptor.capture());
        verify(mockedDriverRepo, times(1)).deleteById(longArgumentCaptor.capture());

        assertTrue(actualResult);

    }

    @Test
    @DisplayName("Should throw Exception when deleting driver by id with input long")
    void deleteDriverById_inputEmptyThrowException() {
        //given
        when(mockedDriverRepo.findById(anyLong())).thenReturn(Optional.empty());

        //when
        assertThrows(
                RuntimeException.class,
                () -> driverService.deleteDriverById(1L),
                "Error, driver with id = " + 1 + " not found"
        );

        //then
        verify(mockedDriverRepo, times(1)).findById(longArgumentCaptor.capture());
        verify(mockedDriverRepo, never()).deleteById(longArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Should not delete driver by id with input driver with non-empty transports and return False")
    void deleteDriverById_inputDriverWithNotEmptyDrivers_returnFalse() {
        //given
        when(mockedDriverRepo.findById(anyLong())).thenReturn(Optional.of(testDriver));

        //when
        boolean actualResult = driverService.deleteDriverById(1L);

        //then
        verify(mockedDriverRepo, times(1)).findById(longArgumentCaptor.capture());
        verify(mockedDriverRepo, never()).deleteById(longArgumentCaptor.capture());

        assertFalse(actualResult);
    }

    @Test
    @DisplayName("Should add driver on transport with input two long and return true")
    void addDriverOnTransport_inputTwoLongAndReturnTrue() {
        //given
        when(mockedDriverRepo.findById(anyLong())).thenReturn(Optional.of(testDriver));
        when(mockedTransportRepo.findById(anyLong())).thenReturn(Optional.of(testBus));
        when(transportService.updateTransport(anyLong(),any(TransportDto.class))).thenReturn(testBus);

        //when
        boolean actualResult = driverService.addDriverOnTransport(1L, 1L);

        //then
        verify(mockedDriverRepo, times(1)).findById(longArgumentCaptor.capture());
        verify(mockedTransportRepo, times(1)).findById(longArgumentCaptor.capture());
        verify(transportService, times(1)).updateTransport(longArgumentCaptor.capture(), transportDtoArgumentCaptor.capture());

        assertTrue(actualResult);
    }

    @Test
    @DisplayName("Should throw Exception when add driver on transport with input empty driver")
    void addDriverOnTransport_inputEmptyDriverAndReturnFalse() {
        //given
        when(mockedDriverRepo.findById(anyLong())).thenReturn(Optional.empty());
        when(mockedTransportRepo.findById(anyLong())).thenReturn(Optional.of(testBus));
        when(transportService.updateTransport(anyLong(), any(TransportDto.class))).thenReturn(new Bus());

        //when
        assertThrows(
                RuntimeException.class,
                () -> driverService.addDriverOnTransport(1L, 1L),
                "Driver with id=" + 1 + " not found !"
        );

        //then
        verify(mockedDriverRepo, times(1)).findById(longArgumentCaptor.capture());
        verify(mockedTransportRepo, never()).findById(longArgumentCaptor.capture());
        verify(transportService, never()).updateTransport(longArgumentCaptor.capture(),transportDtoArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Should throw Exception when add driver on transport with input empty transport")
    void addDriverOnTransport_inputEmptyTransportAndReturnFalse() {
        //given
        when(mockedDriverRepo.findById(anyLong())).thenReturn(Optional.of(testDriver));
        when(mockedTransportRepo.findById(anyLong())).thenReturn(Optional.empty());
        when(transportService.updateTransport(anyLong(), any(TransportDto.class))).thenReturn(new Bus());

        //when
        assertThrows(
                RuntimeException.class,
                () -> driverService.addDriverOnTransport(1L, 1L),
                "Transport with id=" + 1 + " not found !"
        );

        //then
        verify(mockedDriverRepo, times(1)).findById(longArgumentCaptor.capture());
        verify(mockedTransportRepo, times(1)).findById(longArgumentCaptor.capture());
        verify(transportService, never()).updateTransport(longArgumentCaptor.capture(), transportDtoArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Should find all drivers by surname with input nothing and return list of drivers by surname")
    void findAllDriverBySurname_inputNothingAndReturnListOfDriversBySurname() {
        //given
        when(mockedDriverRepo.findDriversBySurname(anyString())).thenReturn(List.of(testDriver));

        //when
        List<Driver> actualList = driverService.findAllDriverBySurname("testSurname");

        //then
        verify(mockedDriverRepo, times(1)).findDriversBySurname(stringArgumentCaptor.capture());

        assertEquals(List.of(testDriver), actualList);
    }

    @Test
    @DisplayName("Should find all drivers on route with input number of route and return list of drivers")
    void findAllDriverOnRoute_inputNumberOfRouteAndReturnListOfDrivers() {
        //given
        testDriver.getRoute().clear();
        testDriver.getTransport().clear();
        when(mockedRouteRepo.findById(anyLong())).thenReturn(Optional.of(testRoute));

        //when
        Set<Driver> actualList = driverService.findAllDriverOnRoute(1L);

        //then
        verify(mockedRouteRepo, times(1)).findById(longArgumentCaptor.capture());

        assertEquals(Set.of(testDriver).size(), actualList.size());
    }

    @Test
    @DisplayName("Should find all drivers on route with input empty route and return empty set")
    void findAllDriverOnRoute_inputEmptyRouteAndReturnEmptySet() {
        //given
        when(mockedRouteRepo.findById(anyLong())).thenReturn(Optional.empty());

        //when
        Set<Driver> actualList = driverService.findAllDriverOnRoute(1L);

        //then
        verify(mockedRouteRepo, times(1)).findById(longArgumentCaptor.capture());

        assertEquals(Collections.emptySet(), actualList);
    }

    @Test
    @DisplayName("Should find all transports without driver with input nothing and return list of transports without driver")
    void findAllTransportsWithoutDriver() {
        //given
        when(mockedTransportRepo.findTransportWithoutDriver()).thenReturn(List.of(testBus));

        //when
        List<Transport> actualList = driverService.findAllTransportsWithoutDriver();

        //then
        verify(mockedTransportRepo, times(1)).findTransportWithoutDriver();

        assertEquals(List.of(testBus).size(), actualList.size());
    }

    @Test
    @DisplayName("Should find all drivers with input nothing and return list of drivers")
    void findAllDrivers_inputNothingAndReturnListOfDrivers() {
        //given
        when(mockedDriverRepo.findAll()).thenReturn(List.of(testDriver));

        //when
        List<Driver> actualList = driverService.findAllDrivers();

        //then
        verify(mockedDriverRepo, times(1)).findAll();

        assertEquals(List.of(testDriver), actualList);
    }
}