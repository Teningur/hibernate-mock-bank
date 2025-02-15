package com.bank.example.repository;

import com.bank.example.model.CashBackCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashBackCompanyRepository extends JpaRepository<CashBackCompany, Long> {
}
