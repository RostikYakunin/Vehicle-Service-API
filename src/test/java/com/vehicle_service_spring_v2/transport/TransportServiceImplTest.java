package com.vehicle_service_spring_v2.transport;

import com.vehicle_service_spring_v2.UnitTestBase;
import com.vehicle_service_spring_v2.transports.TransportServiceImpl;
import com.vehicle_service_spring_v2.transports.model.Bus;
import com.vehicle_service_spring_v2.transports.model.Tram;
import com.vehicle_service_spring_v2.transports.model.Transport;
import com.vehicle_service_spring_v2.transports.model.dto.TransportDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransportServiceImplTest extends UnitTestBase {
    TransportServiceImpl transportService;
    @Captor
    ArgumentCaptor<Transport> transportArgumentCaptor;
    @Captor
    ArgumentCaptor<TransportDto> transportDtoArgumentCaptor;

    @BeforeEach
    void setUp() {
        super.configure();
        transportService = new TransportServiceImpl(mockedTransportRepo, mockedRouteRepo, mockedTransportDtoMapper);
    }

    @AfterEach
    void turnDown() {
        super.destroy();
        transportService = null;
    }

    @Test
    @DisplayName("Should add transportDto with bus and return bus")
    void addTransport_inputBusDtoReturnBus() {
        //given
        when(mockedTransportRepo.save(any(Transport.class))).thenReturn(testBus);
        when(mockedTransportDtoMapper.toTransport(any(TransportDto.class))).thenReturn(testBus);

        //when
        Bus actualBus = (Bus) transportService.addTransport(testBusDto);

        //then
        verify(mockedTransportRepo, times(1)).save(transportArgumentCaptor.capture());
        verify(mockedTransportDtoMapper, times(1)).toTransport(transportDtoArgumentCaptor.capture());

        assertEquals(testBus, actualBus);
    }

    @Test
    @DisplayName("Should add transportDto with tram and return tram")
    void addTransport_inputTramDtoReturnTram() {
        //given
        when(mockedTransportRepo.save(any(Transport.class))).thenReturn(testTram);
        when(mockedTransportDtoMapper.toTransport(any(TransportDto.class))).thenReturn(testTram);

        //when
        Tram actualTram = (Tram) transportService.addTransport(testTramDto);

        //then
        verify(mockedTransportRepo, times(1)).save(transportArgumentCaptor.capture());
        verify(mockedTransportDtoMapper, times(1)).toTransport(transportDtoArgumentCaptor.capture());

        assertEquals(testTram, actualTram);
    }

    @Test
    @DisplayName("Should find transport by id and return Optional of bus")
    void findTransportById_inputLongReturnOptionalOfBus() {
        //given
        when(mockedTransportRepo.findById(anyLong())).thenReturn(Optional.of(testBus));
        when(mockedTransportRepo.existsById(anyLong())).thenReturn(true);

        //when
        Transport actualTransport = transportService.findTransportById(1L);

        //then
        verify(mockedTransportRepo, times(1)).findById(longArgumentCaptor.capture());
        verify(mockedTransportRepo, times(1)).existsById(longArgumentCaptor.capture());

        assertEquals(testBus, actualTransport);
    }

    @Test
    @DisplayName("Should find transport by id and return Optional of tram")
    void findTransportById_inputLongReturnOptionalOfTram() {
        //given
        when(mockedTransportRepo.findById(anyLong())).thenReturn(Optional.of(testTram));
        when(mockedTransportRepo.existsById(anyLong())).thenReturn(true);

        //when
        Transport actualTransport = transportService.findTransportById(1L);

        //then
        verify(mockedTransportRepo, times(1)).findById(longArgumentCaptor.capture());
        verify(mockedTransportRepo, times(1)).existsById(longArgumentCaptor.capture());

        assertEquals(testTram, actualTransport);
    }

    @Test
    @DisplayName("Should throw RuntimeException when finding transport by id with empty result")
    void findTransportById_inputEmptyReturnOptionalEmpty() {
        //given
        when(mockedTransportRepo.findById(anyLong())).thenReturn(Optional.empty());

        //when
        assertThrows(
                RuntimeException.class,
                () -> transportService.findTransportById(1L),
                "Transport with id=" + 1 + " not found !"
        );

        //then
        verify(mockedTransportRepo, times(1)).findById(longArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Should update transport with busDto and return updated bus")
    void updateTransport_inputBusDtoReturnBus() {
        //given
        when(mockedTransportRepo.save(any(Transport.class))).thenReturn(testBus);
        when(mockedTransportDtoMapper.toTransport(any(TransportDto.class))).thenReturn(testBus);

        //when
        Bus actualBus = (Bus) transportService.updateTransport(1L, testBusDto);

        //then
        verify(mockedTransportRepo, times(1)).save(transportArgumentCaptor.capture());
        verify(mockedTransportDtoMapper, times(1)).toTransport(transportDtoArgumentCaptor.capture());

        assertEquals(testBus, actualBus);
    }

    @Test
    @DisplayName("Should update transport with tramDto and return updated tram")
    void updateTransport_inputTramDtoReturnTram() {
        //given
        when(mockedTransportRepo.save(any(Transport.class))).thenReturn(testTram);
        when(mockedTransportDtoMapper.toTransport(any(TransportDto.class))).thenReturn(testTram);

        //when
        Tram actualTram = (Tram) transportService.updateTransport(1L, testTramDto);

        //then
        verify(mockedTransportRepo, times(1)).save(transportArgumentCaptor.capture());
        verify(mockedTransportDtoMapper, times(1)).toTransport(transportDtoArgumentCaptor.capture());

        assertEquals(testTram, actualTram);
    }

    @Test
    @DisplayName("Should delete transport by id with input long and return true")
    void deleteTransportById_inputLongAndReturnTrue() {
        //given
        when(mockedTransportRepo.findById(anyLong())).thenReturn(Optional.of(testBus));
        doNothing().when(mockedTransportRepo).deleteById(anyLong());
        testBus.getDrivers().clear();

        //when
        boolean actualResult = transportService.deleteTransportById(1L);

        //then
        verify(mockedTransportRepo, times(1)).findById(longArgumentCaptor.capture());
        verify(mockedTransportRepo, times(1)).deleteById(longArgumentCaptor.capture());

        assertTrue(actualResult);
    }

    @Test
    @DisplayName("Should throw exception when deleting empty transport by id")
    void deleteTransportById_inputEmptyTransport() {
        //given
        when(mockedTransportRepo.findById(anyLong())).thenReturn(Optional.empty());

        //when
        assertThrows(
                RuntimeException.class,
                () -> transportService.deleteTransportById(1L),
                "Error, transport with id = " + 1 + " not found"
        );

        //then
        verify(mockedTransportRepo, times(1)).findById(longArgumentCaptor.capture());
        verify(mockedTransportRepo, never()).deleteById(longArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Should not delete transport by id with non-empty drivers and return false")
    void deleteTransportById_inputTransportWithNotEmptyDriversAndReturnFalse() {
        //given
        when(mockedTransportRepo.findById(anyLong())).thenReturn(Optional.of(testBus));

        //when
        boolean actualResult = transportService.deleteTransportById(1L);

        //then
        verify(mockedTransportRepo, times(1)).findById(longArgumentCaptor.capture());
        verify(mockedTransportRepo, never()).deleteById(longArgumentCaptor.capture());

        assertFalse(actualResult);
    }

    @Test
    @DisplayName("Should find all exist transports")
    void findAllTransports() {
        //given
        when(mockedTransportRepo.findAll()).thenReturn(List.of(testBus));

        //when
        List<Transport> actualList = transportService.findAllTransports();

        //then
        verify(mockedTransportRepo, times(1)).findAll();

        assertEquals(List.of(testBus), actualList);
    }

    @Test
    @DisplayName("Should find transports by brand and return list of transports")
    void findTransportByBrand_ReturnListOfTransportsByBrand() {
        //given
        when(mockedTransportRepo.findTransportByBrand(anyString())).thenReturn(List.of(testBus));

        //when
        List<Transport> actualList = transportService.findTransportByBrand("testBus");

        //then
        verify(mockedTransportRepo, times(1)).findTransportByBrand(stringArgumentCaptor.capture());

        assertEquals(List.of(testBus).size(), actualList.size());
    }

    @Test
    @DisplayName("Should find transports without drivers and return list of transports")
    void findTransportWithoutDriver_ReturnListOfTransportsWithoutDriver() {
        //given
        testBus.getDrivers().clear();
        when(mockedTransportRepo.findTransportWithoutDriver()).thenReturn(List.of(testBus));

        //when
        List<Transport> actualList = transportService.findTransportWithoutDriver();

        //then
        verify(mockedTransportRepo, times(1)).findTransportWithoutDriver();

        assertEquals(List.of(testBus).size(), actualList.size());
        assertEquals(Collections.emptySet(), actualList.get(0).getDrivers());
    }

    @Test
    @DisplayName("Should add transport to route and return true")
    void addTransportToRoute_inputTransportAndRouteReturnTrue() {
        //given
        when(mockedTransportRepo.findById(anyLong())).thenReturn(Optional.of(testBus));
        when(mockedRouteRepo.findById(anyLong())).thenReturn(Optional.of(testRoute));
        when(mockedTransportRepo.save(any(Transport.class))).thenReturn(testBus);
        when(mockedTransportDtoMapper.toDto(any(Transport.class))).thenReturn(testBusDto);

        //when
        boolean actualResult = transportService.addTransportToRoute(1L, 1L);

        //then
        verify(mockedTransportRepo, times(1)).findById(longArgumentCaptor.capture());
        verify(mockedRouteRepo, times(1)).findById(longArgumentCaptor.capture());
        verify(mockedTransportRepo, times(1)).save(transportArgumentCaptor.capture());
        verify(mockedTransportDtoMapper, times(1)).toDto(transportArgumentCaptor.capture());

        assertTrue(actualResult);
    }

    @Test
    @DisplayName("Should not add empty transport to route and return false")
    void addTransportToRoute_inputEmptyTransportReturnFalse() {
        //given
        when(mockedTransportRepo.findById(anyLong())).thenReturn(Optional.empty());
        when(mockedRouteRepo.findById(anyLong())).thenReturn(Optional.of(testRoute));
        when(mockedTransportRepo.save(any(Transport.class))).thenReturn(testBus);

        //when
        assertThrows(
                RuntimeException.class,
                () -> transportService.addTransportToRoute(1L, 1L),
                "Transport with id=" + 1 + " not found !"
        );

        //then
        verify(mockedTransportRepo, times(1)).findById(longArgumentCaptor.capture());
        verify(mockedRouteRepo, times(1)).findById(longArgumentCaptor.capture());
        verify(mockedTransportRepo, never()).save(transportArgumentCaptor.capture());
    }

    @Test
    @DisplayName("Should remove transport from route and return true")
    void removeTransportFromRoute_inputTransportAndRouteReturnTrue() {
        //given
        when(mockedTransportRepo.findById(anyLong())).thenReturn(Optional.of(testBus));
        when(mockedRouteRepo.findById(anyLong())).thenReturn(Optional.of(testRoute));
        when(mockedTransportRepo.save(any(Transport.class))).thenReturn(testBus);
        when(mockedTransportDtoMapper.toDto(any(Transport.class))).thenReturn(testBusDto);

        //when
        boolean actualResult = transportService.removeTransportFromRoute(1L, 1L);

        //then
        verify(mockedTransportRepo, times(1)).findById(longArgumentCaptor.capture());
        verify(mockedRouteRepo, times(1)).findById(longArgumentCaptor.capture());
        verify(mockedTransportRepo, times(1)).save(transportArgumentCaptor.capture());
        verify(mockedTransportDtoMapper, times(1)).toDto(transportArgumentCaptor.capture());

        assertTrue(actualResult);
    }

    @Test
    @DisplayName("Should not remove empty transport from route and return false")
    void removeTransportFromRoute_inputEmptyTransportReturnFalse() {
        //given
        when(mockedTransportRepo.findById(anyLong())).thenReturn(Optional.empty());
        when(mockedRouteRepo.findById(anyLong())).thenReturn(Optional.of(testRoute));
        when(mockedTransportRepo.save(any(Transport.class))).thenReturn(testBus);

        //when
        assertThrows(
                RuntimeException.class,
                () -> transportService.removeTransportFromRoute(1L, 1L),
                "Transport with id=" + 1 + " not found !"
        );

        //then
        verify(mockedTransportRepo, times(1)).findById(longArgumentCaptor.capture());
        verify(mockedRouteRepo, times(1)).findById(longArgumentCaptor.capture());
        verify(mockedTransportRepo, never()).save(transportArgumentCaptor.capture());
    }
}