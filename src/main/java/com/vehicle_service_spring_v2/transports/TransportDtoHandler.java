package com.vehicle_service_spring_v2.transports;

import com.vehicle_service_spring_v2.drivers.model.DriverQualificationEnum;
import com.vehicle_service_spring_v2.transports.model.Bus;
import com.vehicle_service_spring_v2.transports.model.Tram;
import com.vehicle_service_spring_v2.transports.model.Transport;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class TransportDtoHandler {

    public static TransportDto createTransportDto(Transport transport) {
        TransportDto transportDto;

        if (transport.getDriverQualificationEnum().equals(DriverQualificationEnum.BUS_DRIVER)) {
            Bus bus = (Bus) transport;
            transportDto = busDtoCreator(bus);
        } else {
            Tram tram = (Tram) transport;
            transportDto = tramDtoCreator(tram);
        }

        return transportDto;
    }

    private static TransportDto tramDtoCreator(Tram tram) {
        TransportDto transportDto = new TransportDto();

        transportDto.setId(tram.getId());
        transportDto.setBrandOfTransport(tram.getBrandOfTransport());
        transportDto.setAmountOfPassengers(tram.getAmountOfPassengers());
        transportDto.setDriverQualificationEnum("TRAM");
        transportDto.setAmountOfRailcar(tram.getAmountOfRailcar());

        return transportDto;
    }

    private static TransportDto busDtoCreator(Bus bus) {
        TransportDto transportDto = new TransportDto();

        transportDto.setId(bus.getId());
        transportDto.setBrandOfTransport(bus.getBrandOfTransport());
        transportDto.setAmountOfPassengers(bus.getAmountOfPassengers());
        transportDto.setType(bus.getType());
        transportDto.setAmountOfDoors(bus.getAmountOfDoors());
        transportDto.setDriverQualificationEnum("BUS");

        return transportDto;
    }

    public static Transport mappingDtoToTransportMethodAdd(TransportDto transportDto) {

        if (transportDto.getAmountOfDoors() != null) {
            return busMapper(transportDto);
        } else if (transportDto.getAmountOfRailcar() != null) {
            return tramMapper(transportDto);
        } else {
            throw new RuntimeException("Transport: " + transportDto + " cannot mapped to transport");
        }
    }

    private static Bus busMapper(TransportDto transportDto) {
        Bus bus = new Bus();
        bus.setBrandOfTransport(transportDto.getBrandOfTransport());
        bus.setAmountOfPassengers(transportDto.getAmountOfPassengers());
        bus.setDriverQualificationEnum(DriverQualificationEnum.BUS_DRIVER);
        bus.setType(transportDto.getType());
        bus.setAmountOfDoors(transportDto.getAmountOfDoors());

        log.info("New bus was mapped: " + bus);
        return bus;
    }

    private static Tram tramMapper(TransportDto transportDto) {
        Tram tram = new Tram();
        tram.setBrandOfTransport(transportDto.getBrandOfTransport());
        tram.setAmountOfPassengers(transportDto.getAmountOfPassengers());
        tram.setDriverQualificationEnum(DriverQualificationEnum.TRAM_DRIVER);
        tram.setAmountOfRailcar(transportDto.getAmountOfRailcar());

        log.info("New tram was mapped: " + tram);
        return tram;
    }

    public static Transport mappingDtoToTransportMethodUpdate(TransportDto transportDto, Optional<Transport> transport) {
        Bus bus = null;
        Tram tram = null;

        if (transport.get().getDriverQualificationEnum() == DriverQualificationEnum.BUS_DRIVER) {
            bus = (Bus) transport.get();
        } else {
            tram = (Tram) transport.get();
        }

        if (transportDto.getBrandOfTransport() != null) {
            log.info("Transport`s brand was updated from " + transport.get().getBrandOfTransport() + " to " + transportDto.getBrandOfTransport());
            transport.get().setBrandOfTransport(transportDto.getBrandOfTransport());
        }

        if (transportDto.getAmountOfPassengers() != null) {
            log.info("Transport`s passenger amount was updated from " + transport.get().getAmountOfPassengers() + " to " + transportDto.getAmountOfPassengers());
            transport.get().setAmountOfPassengers(transportDto.getAmountOfPassengers());
        }

        if (transportDto.getDriverQualificationEnum() != null) {
            log.info("Transport`s driver qualifications was updated from " + transport.get().getDriverQualificationEnum() + " to " + transportDto.getDriverQualificationEnum());
            transport.get().setDriverQualificationEnum(DriverQualificationEnum.valueOf(transportDto.getDriverQualificationEnum().toUpperCase() + "_DRIVER"));
        }

        if (!transportDto.getDrivers().isEmpty()) {
            log.info("New drivers were added to transport");
            transport.get().getDrivers().addAll(transportDto.getDrivers());
        }

        if (!transportDto.getRoutes().isEmpty()) {
            log.info("New routes were added to transport");
            transport.get().getRoute().addAll(transportDto.getRoutes());
        }

        if (transportDto.getType() != null && bus != null) {
            log.info("Transport`s type was updated form " + bus.getType() + " to " + transportDto.getType());
            bus.setType(transportDto.getType());
        }

        if (transportDto.getAmountOfDoors() != null && bus != null) {
            log.info("Transport`s door amount was updated form " + bus.getAmountOfDoors() + " to " + transportDto.getAmountOfDoors());
            bus.setAmountOfDoors(transportDto.getAmountOfDoors());
        }

        if (transportDto.getAmountOfRailcar() != null && tram != null) {
            log.info("Transport`s railcar amount was updated form " + tram.getAmountOfRailcar() + " to " + transportDto.getAmountOfRailcar());
            tram.setAmountOfRailcar(transportDto.getAmountOfRailcar());
        }

        if (bus != null) {
            return bus;
        } else {
            return tram;
        }
    }
}
