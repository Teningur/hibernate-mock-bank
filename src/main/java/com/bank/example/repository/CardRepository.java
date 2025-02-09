package com.bank.example.repository;

import com.bank.example.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


// TODO: почитать про текст блоки
@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    @Query("SELECT c FROM Card c WHERE c.account.id IN :accountIds AND c.isDefaultCard = true")
    List<Card> findDefaultCardsByAccountIds(@Param("accountIds") List<Long> accountIds);

    @Query("select c from Card c left join fetch c.account")
    public List<Card> findCardByCardNumber(String cardNumber);
}
