package com.bank.example.repository;

import com.bank.example.dto.OperatorRatingDto;
import com.bank.example.model.Operator;
import com.bank.example.model.OperatorRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OperatorRatingRepository extends JpaRepository<Operator, Long> {

    @Query(
            value = "SELECT o, COALESCE(CAST(AVG(r.rating) AS double), 0.0) " +
                    "FROM Operator o " +
                    "LEFT JOIN o.operatorRatings r " +
                    "GROUP BY o.id, o.firstName, o.lastName " +
                    "ORDER BY COALESCE(AVG(r.rating), 0.0) DESC, o.id ASC"
    )
    List<Object[]> findTopRatedOperators();
}
