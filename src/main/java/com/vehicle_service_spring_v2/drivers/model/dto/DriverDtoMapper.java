package com.vehicle_service_spring_v2.drivers.model.dto;

import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.drivers.model.DriverQualificationEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface DriverDtoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "qualificationEnum", target = "qualificationEnum", qualifiedByName = "mapEnumToString")
    Driver toDriver(DriverDto driverDto);

    @Named("mapEnumToString")
    default DriverQualificationEnum mapEnumToString(String qualificationEnum) {
        return DriverQualificationEnum.valueOf(qualificationEnum.toUpperCase() + "_DRIVER");
    }
}
