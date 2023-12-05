package com.vehicle_service_spring_v2.transports;

import com.vehicle_service_spring_v2.transports.model.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportRepoI extends JpaRepository<Transport, Long> {

    @Query("SELECT t FROM Transport t WHERE t.brandOfTransport = :brand")
    List<Transport> findTransportByBrand(@Param("brand") String brand);

    @Query("SELECT t FROM Transport t WHERE t.drivers is empty")
    List<Transport> findTransportWithoutDriver();
}
