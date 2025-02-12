package com.bank.example.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OperatorRatingDto {

    private Long id;

    private String firstName;

    private String lastName;

    private Double averageRating;

}
