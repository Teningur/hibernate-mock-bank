package com.bank.example.model;

import com.bank.example.listener.CashBackCategoryListener;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(CashBackCategoryListener.class)
public class CashBackCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cashBackCategory_generator")
    @SequenceGenerator(sequenceName = "cashBackCategory_sequence", name = "cashBackCategory_generator", allocationSize = 10)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Manager uploader;

    @ManyToOne(fetch = FetchType.LAZY)
    private Manager editor;

    @ManyToMany(mappedBy = "cashBackCategories")
    private Set<Account> accounts = new HashSet<>();

    @ManyToMany(mappedBy = "cashBackCategories")
    private Set<CashBackCompany> cashBackCompanies = new HashSet<>();

    public void addAccount(Account account) {
        accounts.add(account);
        account.getCashBackCategories().add(this);
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
        account.getCashBackCategories().remove(this);
    }

    public void addCashBackCompany(CashBackCompany cashBackCompany) {
        cashBackCompanies.add(cashBackCompany);
        cashBackCompany.getCashBackCategories().add(this);
    }

    public void removeCashBackCompany(CashBackCompany cashBackCompany) {
        cashBackCompanies.remove(cashBackCompany);
        cashBackCompany.getCashBackCategories().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CashBackCategory that = (CashBackCategory) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
