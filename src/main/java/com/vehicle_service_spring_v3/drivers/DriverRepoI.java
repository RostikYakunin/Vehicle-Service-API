package com.vehicle_service_spring_v3.drivers;

import com.vehicle_service_spring_v3.drivers.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepoI extends JpaRepository<Driver, Long> {

    @Query("SELECT d FROM Driver d WHERE d.surnameOfDriver = :surname")
    List<Driver> findDriversBySurname(@Param("surname") String surname);
}
