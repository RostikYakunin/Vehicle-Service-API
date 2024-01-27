package com.vehicle_service_spring_v3.routes.model;

import com.vehicle_service_spring_v3.drivers.model.Driver;
import com.vehicle_service_spring_v3.transports.model.Transport;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "routes")
@Getter
@Setter
@Builder
@AllArgsConstructor
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

    public Route() {
    }

    public Route(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "\nRoute ID  " + id + ", start: " + startOfWay + ", end: " + endOfWay;
    }
}
