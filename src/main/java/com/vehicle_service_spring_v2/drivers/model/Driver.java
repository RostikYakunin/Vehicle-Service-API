package com.vehicle_service_spring_v2.drivers.model;


import com.vehicle_service_spring_v2.routes.model.Route;
import com.vehicle_service_spring_v2.transports.model.Transport;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "drivers")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DynamicUpdate
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return Objects.equals(id, driver.id) && Objects.equals(nameOfDriver, driver.nameOfDriver) && Objects.equals(surnameOfDriver, driver.surnameOfDriver) && Objects.equals(phoneNumber, driver.phoneNumber) && qualificationEnum == driver.qualificationEnum && Objects.equals(transport, driver.transport) && Objects.equals(route, driver.route);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameOfDriver, surnameOfDriver, phoneNumber, qualificationEnum, transport, route);
    }
}
