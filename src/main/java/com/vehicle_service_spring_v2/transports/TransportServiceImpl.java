package com.vehicle_service_spring_v2.transports;

import com.vehicle_service_spring_v2.routes.RouteRepoI;
import com.vehicle_service_spring_v2.routes.model.Route;
import com.vehicle_service_spring_v2.transports.model.Bus;
import com.vehicle_service_spring_v2.transports.model.Tram;
import com.vehicle_service_spring_v2.transports.model.Transport;
import com.vehicle_service_spring_v2.transports.model.dto.TransportDto;
import com.vehicle_service_spring_v2.transports.model.dto.TransportDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransportServiceImpl implements TransportServiceI {
    private final TransportRepoI transportRepo;
    private final RouteRepoI routeRepo;
    private final TransportDtoMapper transportDtoMapper;

    @Override
    public Transport addTransport(TransportDto transportDto) {
        switch (transportDto.getDriverQualificationEnum().toLowerCase()) {
            case "bus" -> {
                Bus bus = (Bus) transportDtoMapper.toTransport(transportDto);
                log.info("Bus was added to db " + bus);
                return transportRepo.save(bus);
            }
            case "tram" -> {
                Tram tram = (Tram) transportDtoMapper.toTransport(transportDto);
                log.info("Tram was added to db " + tram);
                return transportRepo.save(tram);
            }
            default -> throw new RuntimeException("Unsupported transport type: " + transportDto.getDriverQualificationEnum());
        }
    }

    @Override
    public Transport findTransportById(Long id) {
        return transportRepo.findById(id)
                .filter(x -> transportRepo.existsById(id))
                .orElseThrow(
                        () -> new RuntimeException("Transport with id=" + id + " not found !")
                );
    }

    @Override
    public Transport updateTransport(Long id, TransportDto transportDto) {
        Transport transportById = findTransportById(id);

        switch (transportDto.getDriverQualificationEnum().toLowerCase()) {
            case "bus" -> {
                Bus bus = (Bus) transportDtoMapper.updateVehicle(transportById, transportDto);
                return transportRepo.save(bus);
            }
            case "tram" -> {
                Tram tram = (Tram) transportDtoMapper.updateVehicle(transportById, transportDto);
                return transportRepo.save(tram);
            }
            default -> throw new RuntimeException("Unsupported transport type");
        }
    }

    @Override
    public boolean deleteTransportById(Long id) {
        Transport foundTransport = transportRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Error, transport with id = " + id + " not found")
        );

        boolean isEmpty = foundTransport.getDrivers().isEmpty();

        if (!isEmpty) {
            log.warn("This transport can`t be deleted, transport has the driver = " + foundTransport);
            return false;
        }

        log.info("Transport with id= " + id + " was deleted");
        transportRepo.deleteById(id);
        return true;
    }

    @Override
    public List<Transport> findAllTransports() {
        return transportRepo.findAll();
    }

    @Override
    public List<Transport> findTransportByBrand(String brand) {
        return transportRepo.findTransportByBrand(brand);
    }

    @Override
    public List<Transport> findTransportWithoutDriver() {
        return transportRepo.findTransportWithoutDriver();
    }

    @Override
    public boolean addTransportToRoute(long transportId, long routeId) {
        Route route = routeRepo.findById(routeId).orElseThrow(
                () -> new RuntimeException("Route with id=" + routeId + " not found !")
        );

        Transport transport = transportRepo.findById(transportId).orElseThrow(
                () -> new RuntimeException("Transport with id=" + transportId + " not found !")
        );

        transport.getRoute().add(route);
        updateTransport(transport);

        log.info("Route was update " + route);
        return true;
    }

    private void updateTransport(Transport transport) {
        switch (transport.getDriverQualificationEnum()) {
            case BUS_DRIVER -> {
                Bus bus = (Bus) transport;
                TransportDto transportDto = transportDtoMapper.toDto(bus);
                updateTransport(transport.getId(), transportDto);
            }
            case TRAM_DRIVER -> {
                Tram tram = (Tram) transport;
                TransportDto transportDtoTram = transportDtoMapper.toDto(tram);
                updateTransport(transport.getId(), transportDtoTram);
            }
            default -> throw new RuntimeException("Something went wrong !");
        }
    }

    @Override
    public boolean removeTransportFromRoute(long transportId, long routeId) {
        Route route = routeRepo.findById(routeId).orElseThrow(
                () -> new RuntimeException("Route with id=" + routeId + " not found !")
        );

        Transport transport = transportRepo.findById(transportId).orElseThrow(
                () -> new RuntimeException("Transport with id=" + transportId + " not found !")
        );

        transport.getRoute().remove(route);
        updateTransport(transport);

        log.info("Transport was removed from route");
        return true;
    }
}
