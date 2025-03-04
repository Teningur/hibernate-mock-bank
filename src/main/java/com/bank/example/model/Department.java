package com.bank.example.model;

import com.bank.example.listener.DepartmentListener;
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
@EntityListeners(DepartmentListener.class)
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_generator")
    @SequenceGenerator(sequenceName = "department_sequence", name = "department_generator", allocationSize = 10)
    private Long id;

    private String name;

    @OneToMany(
            mappedBy = "department",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH}
    )
    private List<Operator> operators = new ArrayList<>();

    @OneToMany(
            mappedBy = "department",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH}
    )
    private List<Manager> managers = new ArrayList<>();

    public Department(String name) {
        this.name = name;
    }

    public void addManager(Manager manager) {
        this.managers.add(manager);
        manager.setDepartment(this);
    }

    public void removeManager(Manager manager) {
        this.managers.remove(manager);
        manager.setDepartment(null);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
