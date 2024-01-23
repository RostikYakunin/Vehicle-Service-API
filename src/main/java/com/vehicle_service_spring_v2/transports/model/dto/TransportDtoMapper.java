package com.vehicle_service_spring_v2.transports.model.dto;

import com.vehicle_service_spring_v2.drivers.model.DriverQualificationEnum;
import com.vehicle_service_spring_v2.transports.model.Bus;
import com.vehicle_service_spring_v2.transports.model.Tram;
import com.vehicle_service_spring_v2.transports.model.Transport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ObjectFactory;

@Mapper(componentModel = "spring")
public interface TransportDtoMapper {
    @Mapping(source = "driverQualificationEnum", target = "driverQualificationEnum", qualifiedByName = "mapQualification")
    Transport toTransport(TransportDto transportDto);

    @Named("mapQualification")
    default DriverQualificationEnum mapQualification(String driverQualificationEnum) {
        return DriverQualificationEnum.valueOf(driverQualificationEnum.toUpperCase() + "_DRIVER");
    }

    @ObjectFactory
    default Transport createTransport(TransportDto transportDto) {
        DriverQualificationEnum driverQualificationEnum = DriverQualificationEnum.valueOf(transportDto.getDriverQualificationEnum().toUpperCase() + "_DRIVER");

        switch (driverQualificationEnum) {
            case BUS_DRIVER -> {
                Bus bus = new Bus();
                bus.setType(transportDto.getType());
                bus.setAmountOfDoors(transportDto.getAmountOfDoors());
                return bus;
            }
            case TRAM_DRIVER -> {
                Tram tram = new Tram();
                tram.setAmountOfRailcar(transportDto.getAmountOfRailcar());
                return tram;
            }
            default -> throw new RuntimeException("Type not found");
        }
    }

    default TransportDto toDto(Transport transport) {
        DriverQualificationEnum driverQualificationEnum = transport.getDriverQualificationEnum();

        switch (driverQualificationEnum) {
            case BUS_DRIVER -> {
                Bus bus = (Bus) transport;
                return toDtoFromBus(bus);
            }
            case TRAM_DRIVER -> {
                Tram tram = (Tram) transport;
                return toDtoFromTram(tram);
            }
            default -> throw new RuntimeException("Type not found");
        }
    }

    TransportDto toDtoFromBus(Bus bus);

    TransportDto toDtoFromTram(Tram tram);

}
