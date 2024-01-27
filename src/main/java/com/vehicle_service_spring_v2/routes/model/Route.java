package com.vehicle_service_spring_v2.routes.model;

import com.vehicle_service_spring_v2.drivers.model.Driver;
import com.vehicle_service_spring_v2.transports.model.Transport;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "routes")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Error, start way cannot be empty")
    @Column(name = "start_way")
    private String startOfWay;

    @NotBlank(message = "Error, end way cannot be empty")
    @Column(name = "end_way")
    private String endOfWay;

    @ManyToMany(mappedBy = "route", fetch = FetchType.EAGER)
    private Set<Transport> transports = new HashSet<>();

    @ManyToMany(mappedBy = "route", fetch = FetchType.EAGER)
    private Set<Driver> drivers = new HashSet<>();

    public Route(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return id == route.id && Objects.equals(startOfWay, route.startOfWay) && Objects.equals(endOfWay, route.endOfWay) && Objects.equals(transports, route.transports) && Objects.equals(drivers, route.drivers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startOfWay, endOfWay, transports, drivers);
    }
}
