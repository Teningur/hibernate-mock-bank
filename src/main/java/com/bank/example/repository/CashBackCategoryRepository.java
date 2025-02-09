package com.bank.example.repository;

import com.bank.example.model.CashBackCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CashBackCategoryRepository extends JpaRepository<CashBackCategory, Long> {


    @Query("SELECT DISTINCT c FROM CashBackCategory c " +
            "JOIN FETCH c.uploader u " +
            "JOIN FETCH u.department d " +
            "JOIN FETCH c.cashBackCompanies co " +
            "JOIN FETCH co.uploader cu")
    List<CashBackCategory> findAllDetailed();
}
