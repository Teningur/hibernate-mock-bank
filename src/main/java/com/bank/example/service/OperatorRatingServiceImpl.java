package com.bank.example.service;

import com.bank.example.dao.GenericDao;
import com.bank.example.dao.OperatorRatingDao;
import com.bank.example.dto.OperatorRatingDto;
import com.bank.example.model.Operator;
import com.bank.example.model.OperatorRating;
import com.bank.example.repository.OperatorRatingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OperatorRatingServiceImpl extends AbstractService<Long, OperatorRating> implements OperatorRatingService {

    private final OperatorRatingDao operatorRatingDao;
    private final OperatorRatingRepository operatorRatingRepository;

    @Autowired
    private final ModelMapper modelMapper;

    public OperatorRatingServiceImpl(OperatorRatingDao operatorRatingDao, OperatorRatingRepository operatorRatingRepository, ModelMapper modelMapper) {
        super(operatorRatingDao);
        this.operatorRatingDao = operatorRatingDao;
        this.operatorRatingRepository = operatorRatingRepository;
        this.modelMapper = modelMapper;
    }

//    public List<OperatorRatingDto> findTop10Operators() {
//        return operatorRatingRepository.findTopRatedOperators()
//                .stream()
//                .limit(10)
//                .map(result -> {
//                    Operator operator = (Operator) result[0];
//                    Double avgRating = (Double) result[1];
//
//                    return new OperatorRatingDto(
//                            operator.getId(),
//                            operator.getFirstName(),
//                            operator.getLastName(),
//                            avgRating
//                    );
//                })
//                .collect(Collectors.toList());
//    }

    public List<OperatorRatingDto> findTop10Operators() {
        return operatorRatingRepository.findTopRatedOperators()
                .stream()
                .limit(10)
                .map(result -> {
                    Operator operator = (Operator) result[0];
                    Double avgRating = (Double) result[1];

                    OperatorRatingDto operatorRatingDto = modelMapper.map(operator, OperatorRatingDto.class);
                    operatorRatingDto.setAverageRating(avgRating);
                    return operatorRatingDto;
                })
                .collect(Collectors.toList());
    }
}
