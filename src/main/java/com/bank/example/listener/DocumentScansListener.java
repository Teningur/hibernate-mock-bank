package com.bank.example.listener;


import com.bank.example.model.DocumentScans;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.ValidationException;

public class DocumentScansListener {

    @PrePersist
    @PreUpdate
    public void prePersist(DocumentScans documentScans) {
        if (documentScans.getInsuranceNumber() == null && documentScans.getITN() == null) {
            throw new ValidationException("Хотя бы одно из полей ITN или insuranceNumber должно быть заполнено");
        }

        if (documentScans.getPassport() == null){
            throw new ValidationException("Поле passport не может быть null");
        }
    }
}
