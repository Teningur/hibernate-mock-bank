package com.bank.example.model;

import com.bank.example.listener.DocumentScansListener;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@EntityListeners(DocumentScansListener.class)
public class DocumentScans {

    @Id
    private Long id;

    private String passport;

    private String ITN;

    private String insuranceNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    @MapsId
    private Account account;

    public DocumentScans() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentScans that = (DocumentScans) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(passport, that.passport) &&
                Objects.equals(ITN, that.ITN) &&
                Objects.equals(insuranceNumber, that.insuranceNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, passport, ITN, insuranceNumber);
    }
}
