package com.bank.example.dao;

import com.bank.example.model.Deposit;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositDao extends GenericDao<Long, Deposit> {
}
