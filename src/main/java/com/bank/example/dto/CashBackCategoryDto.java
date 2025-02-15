package com.bank.example.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CashBackCategoryDto {

    private Long categoryId;
    private String categoryName;
    private List<CashBackTransactionDto> cashBacks;
}
