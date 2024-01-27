package com.vehicle_service_spring_v2.transports.model;


import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.drivers.model.DriverQualificationEnum;
import com.vehicle_service_spring_v2.routes.model.Route;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "transports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@SuperBuilder
@DynamicUpdate
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
}
