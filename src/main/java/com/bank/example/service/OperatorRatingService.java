package com.bank.example.service;

import com.bank.example.dto.OperatorRatingDto;
import com.bank.example.model.OperatorRating;

import java.util.List;

public interface OperatorRatingService extends GenericService<Long, OperatorRating> {

    List<OperatorRatingDto> findTop10Operators();
}
