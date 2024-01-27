package com.vehicle_service_spring_v_3.transports.model;


import com.vehicle_service_spring_v_3.drivers.model.Driver;
import com.vehicle_service_spring_v_3.drivers.model.DriverQualificationEnum;
import com.vehicle_service_spring_v_3.routes.model.Route;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "transports")
@Getter
@Setter
public abstract class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Error, transport`s brand cannot be empty")
    @Column(name = "brand_of_transport")
    private String brandOfTransport;

    @Column(name = "amount_of_passengers")
    private Integer amountOfPassengers;
    @Enumerated(EnumType.STRING)
    private DriverQualificationEnum driverQualificationEnum;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "transports_drivers",
            joinColumns = @JoinColumn(name = "transport_id"),
            inverseJoinColumns = @JoinColumn(name = "driver_id")
    )
    private Set<Driver> drivers = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "transports_routes",
            joinColumns = @JoinColumn(name = "transport_id"),
            inverseJoinColumns = @JoinColumn(name = "route_id")
    )
    private Set<Route> route = new HashSet<>();

    public Transport() {
    }

    @Override
    public String toString() {
        return "\nTransport ID " + id + ", model: " + brandOfTransport + ", numbers of passengers: " + amountOfPassengers +
                ", driver qualification: " + driverQualificationEnum.name();
    }
}
