package com.bank.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@NoArgsConstructor
public class OperatorRating {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operatorRating_generator")
    @SequenceGenerator(sequenceName = "operatorRating_sequence", name = "operatorRating_generator", allocationSize = 10)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Operator operator;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    private Integer rating;

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperatorRating that = (OperatorRating) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(rating, that.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rating);
    }
}
