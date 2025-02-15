package com.bank.example.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CashBackTransactionDto {

    private Long transactionId;
    private Long companyId;
    private String companyName;
    private Double amount;
    private LocalDate transactionDate;
}
