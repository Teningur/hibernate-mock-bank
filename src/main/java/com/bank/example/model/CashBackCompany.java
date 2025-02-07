package com.bank.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class CashBackCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cashBackCompany_generator")
    @SequenceGenerator(sequenceName = "cashBackCompany_sequence", name = "cashBackCompany_generator", allocationSize = 10)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Manager uploader;

    @ManyToOne(fetch = FetchType.LAZY)
    private Manager editor;


    @ManyToMany(mappedBy = "cashBackCompanies")
    private Set<Account> accounts = new HashSet<>();


    @ManyToMany
    private Set<CashBackCategory> cashBackCategories = new HashSet<>();

    public void addAccount(Account account) {
        accounts.add(account);
        account.getCashBackCompanies().add(this);
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
        account.getCashBackCompanies().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CashBackCompany that = (CashBackCompany) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
