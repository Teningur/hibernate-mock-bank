package com.bank.example.repository;

import com.bank.example.model.operation.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT tx FROM Transaction tx JOIN tx.account acc JOIN acc.cashBackCategories cbc WHERE acc.id = :accountId AND cbc.id = :categoryId")
    List<Transaction> findByAccountIdAndCategoryId(@Param("accountId") Long accountId, @Param("categoryId") Long categoryId);
}
