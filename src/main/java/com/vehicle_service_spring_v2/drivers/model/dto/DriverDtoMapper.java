package com.vehicle_service_spring_v2.drivers.model.dto;

import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.drivers.model.DriverQualificationEnum;
import org.mapstruct.*;

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

    @Mapping(target = "id", ignore = true)
    Driver updateDriver(@MappingTarget Driver driverById, DriverDto driverDto);

    @BeforeMapping
    default Driver updating (@MappingTarget Driver driverById, DriverDto driverDto) {
        if (driverDto.getNameOfDriver() != null) {
            driverById.setNameOfDriver(driverDto.getNameOfDriver());
        }

        if (driverDto.getSurnameOfDriver() != null){
            driverById.setSurnameOfDriver(driverDto.getSurnameOfDriver());
        }

        if (driverDto.getPhoneNumber() != null) {
            driverById.setPhoneNumber(driverDto.getPhoneNumber());
        }

        if (driverDto.getQualificationEnum() != null) {
            driverById.setQualificationEnum(mapEnumFromString(driverDto.getQualificationEnum()));
        }

        return driverById;
    }
}
