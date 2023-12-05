package com.vehicle_service_spring_v2.transports.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Builder
public class Tram extends Transport {
    @Column(name = "railcar_amount")
    private Integer amountOfRailcar;

    public Tram() {
    }

    @Override
    public String toString() {
        return super.toString() + ", railcar`s numbers: " + amountOfRailcar;
    }
}
