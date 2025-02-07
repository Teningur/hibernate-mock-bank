package com.bank.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Tariff {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tariff_generator")
    @SequenceGenerator(sequenceName = "tariff_sequence", name = "tariff_generator", allocationSize = 10)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "tariff")
    private List<Account> accounts = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tariff tariff = (Tariff) o;
        return Objects.equals(id, tariff.id) &&
                Objects.equals(name, tariff.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
