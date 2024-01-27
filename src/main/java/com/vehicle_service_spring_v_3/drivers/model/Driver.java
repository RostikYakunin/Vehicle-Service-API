package com.vehicle_service_spring_v_3.drivers.model;


import com.vehicle_service_spring_v_3.routes.model.Route;
import com.vehicle_service_spring_v_3.transports.model.Transport;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "drivers")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Driver {
    @Id
    @Column(name = "driver_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Error, driver`s name cannot be empty")
    @Column(name = "name_of_driver")
    private String nameOfDriver;

    @NotBlank(message = "Error, driver`s surname cannot be empty")
    @Column(name = "surname_of_driver")
    private String surnameOfDriver;

    @NotBlank(message = "Error, phone number cannot be empty")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "qualification")
    private DriverQualificationEnum qualificationEnum;

    @ManyToMany(mappedBy = "drivers")
    private Set<Transport> transport = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "drivers_routes",
            joinColumns = @JoinColumn(name = "driver_id"),
            inverseJoinColumns = @JoinColumn(name = "route_id")
    )
    private Set<Route> route = new HashSet<>();

    public Driver(Long id) {
        this.id = id;
    }
}
