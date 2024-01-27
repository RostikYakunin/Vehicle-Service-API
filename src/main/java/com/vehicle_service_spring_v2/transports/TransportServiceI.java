package com.vehicle_service_spring_v2.transports;

import com.vehicle_service_spring_v2.transports.model.Transport;
import com.vehicle_service_spring_v2.transports.model.dto.TransportDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransportServiceI {

    Transport addTransport(TransportDto transportDto);

    Transport findTransportById(Long id);

    Transport updateTransport(TransportDto transportDto);

    boolean deleteTransportById(Long id);

    List<Transport> findAllTransports();

    List<Transport> findTransportByBrand(String brand);

    List<Transport> findTransportWithoutDriver();

    boolean addTransportToRoute(long transportId, long routeId);

    boolean removeTransportFromRoute(long transportId, long routeId);
}
