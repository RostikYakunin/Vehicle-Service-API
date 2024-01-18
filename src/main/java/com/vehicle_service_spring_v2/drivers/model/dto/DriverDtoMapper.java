package com.vehicle_service_spring_v2.drivers.model.dto;

import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.drivers.model.DriverQualificationEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface DriverDtoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "qualificationEnum", target = "qualificationEnum", qualifiedByName = "mapEnumFromString")
    Driver toDriver(DriverDto driverDto);

    @Mapping(source = "qualificationEnum", target = "qualificationEnum", qualifiedByName = "mapEnumToString")
    DriverDto toDto(Driver driver);

    @Named("mapEnumFromString")
    default DriverQualificationEnum mapEnumFromString(String qualificationEnum) {
        return DriverQualificationEnum.valueOf(qualificationEnum.toUpperCase() + "_DRIVER");
    }

    @Named("mapEnumToString")
    default String mapEnumToString(DriverQualificationEnum qualificationEnum) {
        return qualificationEnum.toString();
    }
}
