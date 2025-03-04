package com.bank.example.service;


import com.bank.example.dao.OperatorDao;
import com.bank.example.model.Operator;
import com.bank.example.repository.OperatorRatingRepository;
import org.springframework.stereotype.Service;



@Service
public class OperatorServiceImpl extends AbstractService<Long, Operator> implements OperatorService {

    private final OperatorDao operatorDao;

    public OperatorServiceImpl(OperatorDao operatorDao) {
        super(operatorDao);
        this.operatorDao = operatorDao;
    }

}
