package com.vehicle_service_spring_v2.transports.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
public class Tram extends Transport {
    @Column(name = "railcar_amount")
    private Integer amountOfRailcar;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tram tram = (Tram) o;
        return Objects.equals(amountOfRailcar, tram.amountOfRailcar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), amountOfRailcar);
    }
}
