package com.bank.example.dao;

import com.bank.example.model.CashBackCompany;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CashBackCompanyDaoImpl extends AbstractDao<Long, CashBackCompany> implements CashBackCompanyDao {
}
