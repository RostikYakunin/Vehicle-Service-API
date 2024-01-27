package com.vehicle_service_spring_v3.transports;

import com.vehicle_service_spring_v3.routes.model.Route;
import com.vehicle_service_spring_v3.routes.RouteRepoI;
import com.vehicle_service_spring_v3.transports.model.Bus;
import com.vehicle_service_spring_v3.transports.model.Tram;
import com.vehicle_service_spring_v3.transports.model.Transport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TransportServiceImpl implements TransportServiceI {
    private final TransportRepoI transportRepo;
    private final RouteRepoI routeRepo;

    @Autowired
    public TransportServiceImpl(TransportRepoI transportRepo, RouteRepoI routeRepo) {
        this.transportRepo = transportRepo;
        this.routeRepo = routeRepo;
    }

    @Override
    public Transport addTransport(TransportDto transportDto) {
        if (transportDto.getDriverQualificationEnum().equalsIgnoreCase("bus")) {
            Bus bus = (Bus) TransportDtoHandler.mappingDtoToTransportMethodAdd(transportDto);
            log.info("Bus was added to db " + bus);

            return transportRepo.save(bus);
        } else {
            Tram tram = (Tram) TransportDtoHandler.mappingDtoToTransportMethodAdd(transportDto);
            log.info("Tram was added to db " + tram);

            return transportRepo.save(tram);
        }
    }

    @Override
    public Optional<Transport> findTransportById(Long id) {
        Optional<Transport> foundTransport = transportRepo.findById(id);

        if (foundTransport.isEmpty()) {
            log.warn("Error, transport with id = " + id + " not found");
            return Optional.empty();
        }

        return foundTransport;
    }

    @Override
    public Transport updateTransport(TransportDto transportDto) {
        Optional<Transport> foundTransport = transportRepo.findById(transportDto.getId());

        Bus bus;
        Tram tram;

        if (transportDto.getDriverQualificationEnum().equalsIgnoreCase("bus")) {
            bus = (Bus) TransportDtoHandler.mappingDtoToTransportMethodUpdate(transportDto, foundTransport);
            return transportRepo.save(bus);
        } else {
            tram = (Tram) TransportDtoHandler.mappingDtoToTransportMethodUpdate(transportDto, foundTransport);
            return transportRepo.save(tram);
        }
    }

    @Override
    public boolean deleteTransportById(Long id) {
        Optional<Transport> foundTransport = transportRepo.findById(id);

        if (foundTransport.isEmpty()) {
            log.warn("Error, transport with id = " + id + " not found");
            return false;
        }

        if (!foundTransport.get().getDrivers().isEmpty()) {
            log.warn("This transport can`t be deleted, transport has the driver = " + foundTransport.get());
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
        Optional<Route> route = routeRepo.findById(routeId);
        Optional<Transport> transport = transportRepo.findById(transportId);

        if (transport.isEmpty() || route.isEmpty()) {
            log.warn("Transport or route cannot be null");
            return false;
        }

        transport.get().getRoute().add(route.get());

        TransportDto transportDto = TransportDtoHandler.createTransportDto(transport.get());
        updateTransport(transportDto);

        log.info("Route was update " + route);
        return true;
    }

    @Override
    public boolean removeTransportFromRoute(long transportId, long routeId) {
        Optional<Route> route = routeRepo.findById(routeId);
        Optional<Transport> transport = transportRepo.findById(transportId);

        if (transport.isEmpty() || route.isEmpty()) {
            log.warn("Transport or route cannot be null");
            return false;
        }

        transport.get().getRoute().remove(route.get());
        TransportDto transportDto = TransportDtoHandler.createTransportDto(transport.get());
        updateTransport(transportDto);

        log.info("Transport was removed from route");
        return true;
    }
}
