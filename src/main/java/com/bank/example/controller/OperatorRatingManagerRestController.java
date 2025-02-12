package com.bank.example.controller;

import com.bank.example.dto.OperatorRatingDto;
import com.bank.example.repository.OperatorRatingRepository;
import com.bank.example.service.OperatorRatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/manager/operator/rating")
public class OperatorRatingManagerRestController {

    private final OperatorRatingService operatorRatingService;
    private final OperatorRatingRepository operatorRatingRepository;

    public OperatorRatingManagerRestController(OperatorRatingService operatorRatingService, OperatorRatingRepository operatorRatingRepository) {
        this.operatorRatingService = operatorRatingService;
        this.operatorRatingRepository = operatorRatingRepository;
    }

    @GetMapping("/top10")
    public ResponseEntity<List<OperatorRatingDto>> getTop10Operators() {
        return ResponseEntity.ok(operatorRatingService.findTop10Operators());
    }
}
