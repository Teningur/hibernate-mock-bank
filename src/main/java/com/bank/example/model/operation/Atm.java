package com.bank.example.model.operation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Atm {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "atm_generator")
    @SequenceGenerator(sequenceName = "atm_sequence", name = "atm_generator", allocationSize = 10)
    private Long id;

    private Double latitude;

    private Double longitude;

    private Boolean isAvailable;

    public Atm(Double latitude, Double longitude, Boolean isAvailable) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.isAvailable = isAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Atm atm = (Atm) o;
        return Objects.equals(id, atm.id) &&
                Objects.equals(latitude, atm.latitude) &&
                Objects.equals(longitude, atm.longitude) &&
                Objects.equals(isAvailable, atm.isAvailable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, latitude, longitude, isAvailable);
    }
}
