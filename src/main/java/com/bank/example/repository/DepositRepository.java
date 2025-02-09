package com.bank.example.repository;

import com.bank.example.model.Deposit;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {

    @Query("SELECT DISTINCT d FROM Deposit d " +
            "LEFT JOIN FETCH d.account a " +
            "LEFT JOIN FETCH a.closeRequest " +
            "LEFT JOIN FETCH d.interests " +
            "WHERE d.account.id IN :accountIds")
    List<Deposit> findDepositsByAccountIds(@Param("accountIds") List<Long> accountIds);


//    @EntityGraph(attributePaths = {"account", "interests"})
//    @Query("SELECT DISTINCT d FROM Deposit d WHERE d.account.id IN :accountIds")
//    List<Deposit> findDepositsByAccountIds(@Param("accountIds") List<Long> accountIds);

}
